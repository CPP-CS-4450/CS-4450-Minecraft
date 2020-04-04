package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Chunk implements Renderable, Textured3D, BoundedContainer, InvertibleContainer {
    protected final Block[][][] cubes;
    protected final List<Bound> bounds;


    public Chunk(final Block[][][] cubes, final List<Bound> bounds) {
        this.cubes = cubes;
        this.bounds = bounds;
    }

    @Override
    public void render() {
        for(final Cube[][] matrix : cubes){
            for(final Cube[] array : matrix){
                for(final Cube cube : array){
                    if(Objects.nonNull(cube)){
                        cube.render();
                    }
                }
            }
        }
    }

    @Override
    public void init() { }

    public Block[][][] getCubes(){ return cubes; }


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
        getInvertibles().parallelStream().forEach(Invertible::invert);
    }

    @Override
    public List<Invertible> getInvertibles() {
        final List<Invertible> invertibles = new ArrayList<>();
        for(final Cube[][] matrix : cubes){
            for(final Cube[] array : matrix){
                for(Cube cube : array){
                    if(cube instanceof Invertible){
                        invertibles.add((Invertible) cube);
                    }
                }
            }
        }

        return invertibles;
    }

    @Override
    public List<Bound> getBounds() {
        return Collections.unmodifiableList(bounds);
    }

}
