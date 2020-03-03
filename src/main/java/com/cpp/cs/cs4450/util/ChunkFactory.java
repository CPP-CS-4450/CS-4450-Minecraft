package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.Chunk;
import com.cpp.cs.cs4450.model.block.Block;
import com.cpp.cs.cs4450.noise.SimplexNoise;

public class ChunkFactory {

    public static Chunk create(int size, int blockSize, double persistence){
        SimplexNoise noise = new SimplexNoise(32, persistence, (int) System.currentTimeMillis());

        Block[][][] blocks = new Block[size][size][size];
        for(int i = 0; i < size; ++i){
            for(int k = 0; k < size; ++k){
                final float x = (i * blockSize);
                final float z = (k * blockSize);

                final int max = (int) (((noise.getNoise(i,k) + 1) >= size) ? (size - 1) : (noise.getNoise(i,k) + 1));
                for(int j = 0; j < max; ++j){
                    final float y = (j * blockSize);


                }
            }
        }

        return null;
    }

    public static Chunk createChuck(int chunkSize, float blockSize, double persistence){
        SimplexNoise noise = new SimplexNoise(32, persistence, (int) System.currentTimeMillis());

        Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];
        for(int i = 0; i < chunkSize; ++i){
            for(int j = 0; j < chunkSize; ++j){
                fillColumn(blocks, chunkSize, blockSize, i, j, (noise.getNoise(i,j) + 1));
            }
        }


        return null;
    }

    private static void fillColumn(Block[][][] blocks, int size, float cubeSize, int n, int m, double h){
        final int max = (int) (((h >= size) ? size - 1 : h) / cubeSize);
        for(int i = 0; i < max; ++i){
            blocks[n][i][m] = BlockFactory.createRandom((cubeSize * n), (cubeSize * i), (cubeSize * m), cubeSize);
        }
    }

}
