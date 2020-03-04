package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.block.Block;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.noise.SimplexNoise;

public class CubeChunk implements Renderable {

    private final Cube[][][] cubes;
    private final SimplexNoise noise;

    public CubeChunk(Cube[][][] cubes, SimplexNoise noise) {
        this.cubes = cubes;
        this.noise = noise;
    }

    @Override
    public void render() {
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
}
