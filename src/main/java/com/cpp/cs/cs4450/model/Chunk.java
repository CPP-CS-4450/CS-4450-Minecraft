/***************************************************************
 * file: Chunk.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Class that acts as a container for the programs blocks or cubes
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Cube;

import java.util.List;

public class Chunk implements Renderable, Textured3D {
    private final Cube[][][] cubes;
    private final List<Cube> blocks;


    public Chunk(final Cube[][][] cubes, final List<Cube> blocks) {
        this.cubes = cubes;
        this.blocks = blocks;
    }

    @Override
    public void render() {
        renderOptimized();
    }

    /*
    Renders list of viewable blocks
     */
    private void renderOptimized(){
        for(Cube block : blocks){
            if(block != null){
                block.render();
            }
        }
    }

    /*
    Renders all blocks in chunk
     */
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

    /*
    Getter for all the cubes in the chunk
     */
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

}
