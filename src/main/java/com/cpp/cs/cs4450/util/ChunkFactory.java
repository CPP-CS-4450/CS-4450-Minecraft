/***************************************************************
 * file: ChunkFactory.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Utility factory class that creates Chunk instances
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.Chunk;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class ChunkFactory {
    private static final int LARGEST_FEATURE = 32;
    private static final List<BlockType> CUBE_BOX_TYPES = Collections.unmodifiableList(
            Arrays.asList(BlockType.values())
    );

    private static final Random random = new Random();

    /*
    Creates a Chunk object based on parameters passed
     */
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

                    cubes[i][j][k] = CubeFactory.createTexturedCube(CUBE_BOX_TYPES.get(random.nextInt(CUBE_BOX_TYPES.size())), x, y, z, scale);

                    if(hasViewableRender(size, i, j, k)) {
                        blocks.add(cubes[i][j][k]);
                    }
                }
            }
        }

        return new Chunk(cubes, blocks);
    }

    /*
    Checks if the block is viewable
     */
    private static boolean hasViewableRender(final int n, final int l, final int h, final int d){
        return (l == 0) || (h == 0) || (d == 0) || (l == n - 1)  || (h > 6) || (d == (n - 1));
    }

}
