/***************************************************************
 * file: ChunkFactory.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Utility factory class to create chunks
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.model.chunk.Chunk;
import com.cpp.cs.cs4450.model.chunk.OptimizedChunk;
import com.cpp.cs.cs4450.model.chunk.VBOArrayChunk;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.noise.SimplexNoise;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSide;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import com.cpp.cs.cs4450.util.BlockTextureLoader.BlockTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.ReadableVector2f;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class ChunkFactory {
    private static final int BASE_NUMBER = 4;
    private static final int DIMENSIONS = 3;
    private static final int LARGEST_FEATURE = 32;
    private static final int MINIMUM_HEIGHT = 9;
    private static final int PLANES = BlockSideType.values().length;
    private static final String DEFAULT_TEXTURE_PATH = TexturesConfiguration.TERRAIN_TEXTURE_PATH;
    private static final List<BlockType> CUBE_BOX_TYPES = Collections.unmodifiableList(Arrays.asList(BlockType.values()));

    private static final Random rand = new Random();

    private ChunkFactory(){
        throw new UnsupportedOperationException();
    }

    public static Chunk createChunk(final int size, final float scale, final double persistence){
        return createChunk(size, scale, persistence, false);
    }

    public static Chunk createChunk(final int size, final float scale, final double persistence, final boolean random){
        return createChunk(size, scale, persistence, true, false, random, DEFAULT_TEXTURE_PATH);
    }

    public static Chunk createChunk(final ChunkOptions options){
        return createChunk(options.size, options.scale, options.persistence, options.optimized, options.bufferRendering, options.random, options.path);
    }

    public static Chunk createChunk(
            final int size,
            final float scale,
            final double persistence,
            final boolean optimized,
            final boolean bufferRendering,
            final boolean random,
            final String path
    ){
        final SimplexNoise noise = new SimplexNoise(LARGEST_FEATURE, persistence, (int) System.currentTimeMillis());

        final Map<BlockType, BlockTexture> textures = new EnumMap<>(BlockType.class);
        final Map<BlockType, BlockTexture> inverts = new EnumMap<>(BlockType.class);
        final Function<BlockType, BlockTexture> computeBlockTexture = t -> textures.computeIfAbsent(t, BlockTextureLoader::getBlockTexture);
        final Function<BlockType, BlockTexture> computeInvert = t -> inverts.computeIfAbsent(t, k -> BlockTextureLoader.invertBlockTexture(computeBlockTexture.apply(k)));


        final List<Block> blocks = new ArrayList<>();

        final Block[][][] cubes = new Block[size][size][size];
        for(int i = 0; i < size; ++i){
            for(int k = 0; k < size; ++k){
                final float x = (i * scale);
                final float z = (k * scale);

                final double h = calculateHeight(noise.getNoise(i,k), size, scale);
                for(int j = 0; j < h; ++j){
                    final float y = (j * scale);

                    final BlockType type = calculateCubeBoxType(random, size, i, j, k);

                    cubes[i][j][k] = BlockFactory.createTexturedCube(type, x, y, z, scale, computeBlockTexture.apply(type), computeInvert.apply(type));

                    if(hasViewableRender(size, i, j, k)) {
                        blocks.add(cubes[i][j][k]);
                    }
                }
            }
        }

        final List<Bound> bounds = optimized ? getBounds(blocks) : getBounds(cubes);

        if(bufferRendering){
            final List<Block> data = optimized ? blocks : convertTensorToList(cubes);
            final ChunkTextureProxy proxy = initChunkTexture(data, path);

            return createVBOArrayChunk(cubes, bounds, data.size(), proxy);
        }

        return optimized ? new OptimizedChunk(cubes, bounds, blocks) : new Chunk(cubes, bounds);
    }

    private static ChunkTextureProxy initChunkTexture(final List<Block> blocks, final String path){
        final Texture texture = TextureLoader.computeTexture(path);
        final Texture invert = TextureInverter.invert(texture);

        final float w = texture.getTextureWidth(), h = texture.getTextureHeight();

        final int size = blocks.size() * BASE_NUMBER * DIMENSIONS * PLANES;

        final FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer(size);
        final FloatBuffer colorData = BufferUtils.createFloatBuffer(size);
        final FloatBuffer textureData = BufferUtils.createFloatBuffer(size);

        final float d = (float) Math.pow(BASE_NUMBER, DIMENSIONS);
        for(final Block block : blocks){
            if(Objects.nonNull(block)){
                final Map<BlockSideType, List<ReadableVector2f>> texVertex = block.getType().getTextureVertices();
                for(final BlockSide side : block.getSides()){
                    for(final float vertex : side.getVerticesArray()){
                        vertexPositionData.put(vertex);
                    }
                    for(final ReadableVector2f vertex : texVertex.get(side.getType())){
                        float tx = ((vertex.getX() * d) / w);
                        float ty = ((vertex.getY() * d) / h);

                        textureData.put(tx);
                        textureData.put(ty);
                    }
                }
            }
        }

        colorData.put(generateColorArray(size));

        vertexPositionData.flip();
        colorData.flip();
        textureData.flip();

        final int vboVertexHandle = GL15.glGenBuffers();
        final int vboColorHandle = GL15.glGenBuffers();
        final int vboTextureHandle = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return ChunkTextureProxy.wrap(vboVertexHandle, vboColorHandle, vboTextureHandle, texture, invert);
    }

    private static boolean hasViewableRender(final int n, final int l, final int h, final int d){
        return (l == 0) || (h == 0) || (d == 0) || (l == n - 1)  || (h > 6) || (d == (n - 1));
    }

    private static BlockType calculateCubeBoxType(final boolean random, final int size, final int length, final int height, final int depth){
        if (random) return CUBE_BOX_TYPES.get(rand.nextInt(CUBE_BOX_TYPES.size()));


        final boolean isBorderBlock = ((length == 0) || (length == (size - 1)) || (depth == 0) || (depth == (size - 1)));

        if (height <= 2) {
            return BlockType.BEDROCK;
        } else if (height <= 4) {
            return BlockType.STONE;
        } else if (height <= 6) {
            return BlockType.DIRT;
        } else if(height == 7){
            return isBorderBlock ? BlockType.DIRT : BlockType.GRASS;
        } else if (height == 8) {
            return isBorderBlock ? BlockType.GRASS : BlockType.WATER;
        } else if (height == 10) {
            return BlockType.SAND;
        } else {
            return BlockType.GRASS;
        }
    }

    private static VBOArrayChunk createVBOArrayChunk(final Block[][][] cubes, final List<Bound> bounds, final int count, final ChunkTextureProxy proxy){
        return new VBOArrayChunk(cubes, bounds, count, proxy.vboVertexHandle, proxy.vboColorHandle, proxy.vboTextureHandle, proxy.texture, proxy.invert);
    }

    private static final Function<Double, Double> calculateHeight = h -> h >= MINIMUM_HEIGHT ? h : MINIMUM_HEIGHT;

    private static double calculateHeight(double noise, int size, float scale){
        return calculateHeight.apply(++noise >= size ? --size : noise / scale);
    }

    private static List<Bound> getBounds(final List<?> objects){
        final List<Bound> bounds = new ArrayList<>();
        for(final Object obj : objects){
            if(obj instanceof Bounded){
                bounds.add(((Bounded) obj).getBounds());
            }
        }

        return bounds;
    }

    private static List<Bound> getBounds(final Block[][][] tensor) {
        final int n = tensor.length, m = tensor[0].length, l = tensor[0][0].length;
        final List<Bound> bounds = new ArrayList<>(n * m * l);
        for(final Block[][] matrix : tensor){
            for(final Block[] array : matrix){
                for(final Block block : array){
                    if(Objects.nonNull(block)){
                        bounds.add(block.getBounds());
                    }
                }
            }
        }

        return bounds;
    }

    private static List<Block> convertTensorToList(final Block[][][] tensor){
        return Arrays.stream(tensor).flatMap(Arrays::stream).flatMap(Arrays::stream).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static float[] generateColorArray(final int size){
        final float[] a = new float[size];
        Arrays.fill(a, 1f);
        return a;
    }

    public static final class ChunkOptions {
        public static final String SIZE_FLAG = "--n";
        public static final String SCALE_FLAG = "--s";
        public static final String PERSISTENCE_FLAG = "--p";
        public static final String ENABLE_OPTIMIZED_FLAG = "--o";
        public static final String DISABLE_OPTIMIZED_FLAG = "--do";
        public static final String ENABLE_BUFFER_RENDERING_FLAG = "--br";
        public static final String DISABLE_BUFFER_RENDERING_FLAG = "--dbr";
        public static final String RANDOM_FLAG = "--r";
        public static final String TEXTURE_PATH_FLAG = "--t";

        private final int size;
        private final float scale;
        private final double persistence;
        private final boolean optimized;
        private final boolean bufferRendering;
        private final boolean random;
        private final String path;

        private ChunkOptions(int size, float scale, double persistence, boolean optimized, boolean bufferRendering, boolean random, String path) {
            this.size = size;
            this.scale = scale;
            this.persistence = persistence;
            this.optimized = optimized;
            this.bufferRendering = bufferRendering;
            this.random = random;
            this.path = path;
        }

        public int getSize() {
            return size;
        }

        public float getScale() {
            return scale;
        }

        public double getPersistence() {
            return persistence;
        }

        public boolean isOptimized() {
            return optimized;
        }

        public boolean isBufferRendering() {
            return bufferRendering;
        }

        public boolean isRandom() {
            return random;
        }

        public String getPath() {
            return path;
        }

        @Override
        public String toString(){
            return "\nChunk Options:" +
                    "\n\tSize:\t" + size +
                    "\n\tScale:\t" + scale +
                    "\n\tPersistence:\t" + persistence +
                    "\n\tOptimize:\t" + optimized +
                    "\n\tBuffer Render:\t" + bufferRendering +
                    "\n\tRandom:\t" + random +
                    "\n\tTexture Path:\t" + path + "\n";
        }

        public static Builder builder(){ return new Builder(); }

        public static final class Builder {
            private int size = 30;
            private float scale = 0.1f;
            private double persistence = 0.25;
            private boolean optimized = true;
            private boolean bufferRendering = false;
            private boolean random = false;
            private String path = DEFAULT_TEXTURE_PATH;

            private Builder(){}

            public ChunkOptions build(){
                return new ChunkOptions(size, scale, persistence, optimized, bufferRendering, random, path);
            }

            public int getSize() {
                return size;
            }

            public Builder withSize(int size) {
                if(size > -1) setSize(size);
                return this;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public float getScale() {
                return scale;
            }

            public Builder withScale(float scale) {
                if(scale > -1) setScale(scale);
                return this;
            }

            public void setScale(float scale) {
                this.scale = scale;
            }

            public double getPersistence() {
                return persistence;
            }

            public Builder withPersistence(double persistence) {
                if(persistence > -1) setPersistence(persistence);
                return this;
            }

            public void setPersistence(double persistence) {
                this.persistence = persistence;
            }

            public boolean isOptimized() {
                return optimized;
            }

            public Builder withOptimized(boolean optimized) {
                setOptimized(optimized);
                return this;
            }

            public void setOptimized(boolean optimized) {
                this.optimized = optimized;
            }

            public boolean isBufferRendering() {
                return bufferRendering;
            }

            public Builder withBufferRendering(boolean bufferRendering) {
                setBufferRendering(bufferRendering);
                return this;
            }
            public void setBufferRendering(boolean bufferRendering) {
                this.bufferRendering = bufferRendering;
            }

            public boolean isRandom() {
                return random;
            }

            public Builder withRandom(boolean random) {
                setRandom(random);
                return this;
            }

            public void setRandom(boolean random) {
                this.random = random;
            }

            public String getPath() {
                return path;
            }

            public Builder withPath(String path) {
                if(Objects.nonNull(path) && !path.isEmpty()) {
                    setPath(path);
                }
                return this;
            }

            public void setPath(String path) {
                this.path = path;
            }

        }

    }

    private static final class ChunkTextureProxy {
        private final int vboVertexHandle;
        private final int vboColorHandle;
        private final int vboTextureHandle;
        private final Texture texture;
        private final Texture invert;


        private ChunkTextureProxy(final int vboVertexHandle, final int vboColorHandle, final int vboTextureHandle, final Texture texture, final Texture invert) {
            this.vboVertexHandle = vboVertexHandle;
            this.vboColorHandle = vboColorHandle;
            this.vboTextureHandle = vboTextureHandle;
            this.texture = texture;
            this.invert = invert;
        }

        private static ChunkTextureProxy wrap(final int vboVertexHandle, final int vboColorHandle, final int vboTextureHandle, final Texture texture, final Texture invert){
            return new ChunkTextureProxy(vboVertexHandle, vboColorHandle, vboTextureHandle, texture, invert);
        }

    }

}
