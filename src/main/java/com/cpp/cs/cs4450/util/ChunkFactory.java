package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.Chunk;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class ChunkFactory {
    private static final int LARGEST_FEATURE = 32;
    private static final List<BlockType> CUBE_BOX_TYPES = List.copyOf(Arrays.asList(BlockType.values()));

    private static final Random random = new Random();


    public static Chunk createChunk(final int size, final float scale, final double persistence){
        final SimplexNoise noise = new SimplexNoise(LARGEST_FEATURE, persistence, (int) System.currentTimeMillis());

        final List<Cube> blocks = new ArrayList<>();

        final Cube[][][] cubes = new Cube[size][size][size];
        for(int i = 0; i < size; ++i){
            for(int k = 0; k < size; ++k){
                final float x = (i * scale);
                final float z = (k * scale);

                final double h = ((((noise.getNoise(i,k) + 1) >= size) ? (size - 1) : (noise.getNoise(i,k) + 1)) / scale);
                for(int j = 0; j < h; ++j){
                    final float y = (j * scale);

                    cubes[i][j][k] = CubeFactory.createTexturedCube(CUBE_BOX_TYPES.get(ChunkFactory.random.nextInt(CUBE_BOX_TYPES.size())), x, y, z, scale);

                    if(hasViewableRender(size, i, j, k)) {
                        blocks.add(cubes[i][j][k]);
                    }
                }
            }
        }

        return new Chunk(cubes, blocks);
    }

    private static boolean hasViewableRender(final int n, final int l, final int h, final int d){
        return (l == 0) || (h == 0) || (d == 0) || (l == n - 1)  || (h > 6) || (d == (n - 1));
    }

}
