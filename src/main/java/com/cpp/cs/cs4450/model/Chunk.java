package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.BoundedContainer;
import com.cpp.cs.cs4450.util.CubeFactory;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import com.cpp.cs.cs4450.util.TextureLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.ReadableVector2f;
import org.newdawn.slick.opengl.Texture;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Chunk implements Renderable, Textured3D, BoundedContainer, InvertibleContainer {
    private final Block[][][] cubes;
    private final List<Block> blocks;
    private final List<Bound> bounds;
    private int vboVertexHandle;
    private int vboColorHandle;
    private int vboTextureHandle;
    private Texture texture;


    private static final Random random = new Random();

    public Chunk(final Block[][][] cubes, final List<Block> blocks) {
        this.cubes = cubes;
        this.blocks = blocks;
        this.bounds = filterBounded(this.blocks);
    }

    @Override
    public void init(){
        texture = TextureLoader.getTexture(TexturesConfiguration.TERRAIN_TEXTURE_PATH);

        vboVertexHandle = GL15.glGenBuffers();
        vboColorHandle = GL15.glGenBuffers();
        vboTextureHandle = GL15.glGenBuffers();

        int n = cubes.length, m = cubes[0].length, o = cubes[0][0].length;


        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((n * m * o) * 6 * 12);
        FloatBuffer colorPositionData = BufferUtils.createFloatBuffer((n * m * o) * 6 * 12);
        FloatBuffer texturePositionData = BufferUtils.createFloatBuffer((n * m * o) * 6 * 12);

        float w = texture.getTextureWidth(), h = texture.getTextureHeight();

        for(int x = 0; x < n; ++x){
            for(int y = 0; y < m; ++y){
                for(int z = 0; z < o; ++z){
                    Block block = cubes[x][y][z];
                    if(Objects.nonNull(block)){
                        Map<CubeSideType, List<ReadableVector2f>> texVertex = block.getType().getTextureVertices();
                        //System.out.println(block.getType());
                        for(CubeSide side : block.getSides()){
                            for(float vertex : side.getVerticesFloatArray()){
                                vertexPositionData.put(vertex);
                            }
                            for(ReadableVector2f vertex : texVertex.get(side.getType())){
                                float tx = ((vertex.getX() * 64.0f) / w) ;
                                float ty = ((vertex.getY() * 64.0f) / h);
                                //System.out.println(tx + " " + ty);
                                texturePositionData.put(tx);
                                texturePositionData.put(ty);
                            }
                        }
                        colorPositionData.put(colorArray());
                    }
                }
            }
        }

        vertexPositionData.flip();
        colorPositionData.flip();
        texturePositionData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texturePositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private float[] colorArray(){
        List<Color> cols = CubeFactory.COLORS;

        Color col = cols.get(random.nextInt(cols.size()));

        //float[] c = new float[]{col.getRed(), col.getGreen(), col.getBlue()};
        float[] c = new float[]{1f,1f,1f};

        float[] colors = new float[c.length * 4 * 6];
        for(int i = 0; i < colors.length; ++i){
            colors[i] = c[i % c.length];
        }



        return colors;
    }

    @Override
    public void render() {
        renderArray();
    }

    private void renderArray(){
        //texture.bind();
        GL11.glPushMatrix();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, cubes.length * cubes[0].length * cubes[0][0].length * 24);
        GL11.glPopMatrix();
    }

    private void renderOptimized(){
        for(Cube block : blocks){
            if(block != null){
                block.render();
            }
        }
    }

    private void renderAll(){
        for(final Cube[][] matrix : cubes){
            for(final Cube[] array : matrix){
                for(Cube cube : array){
                    if(cube != null){
                        cube.render();
                    }
                }
            }
        }
    }

    public Cube[][][] getCubes(){ return cubes; }


    @Override
    public Textured[][][] getTensor() {
        final int a = cubes.length;
        final int b = cubes[0].length;
        final int c = cubes[0][0].length;

        final Textured[][][] texs = new Textured[a][b][c];
        for(int i = 0; i < a; ++i){
            for(int j = 0; j < b; ++j){
                for(int k = 0; k < c; ++k){
                    texs[i][j][k] = (Textured)  cubes[i][j][k];
                }
            }
        }
        
        return texs;
    }

    @Override
    public void invert() {
        blocks.parallelStream().filter(c -> c instanceof Invertible).map(c -> (Invertible) c).forEach(Invertible::invert);
    }

    @Override
    public List<Invertible> getInvertibles() {
        return blocks.stream().filter(b -> b instanceof Invertible).map(b -> (Invertible) b).collect(Collectors.toList());
    }

    @Override
    public List<Bound> getBounds() {
        return Collections.unmodifiableList(bounds);
    }

    private static List<Bound> filterBounded(final List<?> objects){
        return objects.stream().filter(Objects::nonNull)
                .filter(o -> o instanceof Bounded)
                .map(o -> (Bounded) o)
                .map(Bounded::getBounds)
                .collect(Collectors.toList());
    }

}
