package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.BoundedContainer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Chunk implements Renderable, Textured3D, BoundedContainer, InvertibleContainer {
    private final Cube[][][] cubes;
    private final List<Cube> blocks;
    private final List<Bound> bounds;


    public Chunk(final Cube[][][] cubes, final List<Cube> blocks) {
        this.cubes = cubes;
        this.blocks = blocks;
        this.bounds = filterBounded(this.blocks);
    }

    @Override
    public void render() {
        renderOptimized();
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
        return List.copyOf(bounds);
    }

    private static List<Bound> filterBounded(final List<?> objects){
        return objects.stream().filter(Objects::nonNull)
                .filter(o -> o instanceof Bounded)
                .map(o -> (Bounded) o)
                .map(Bounded::getBounds)
                .collect(Collectors.toList());
    }

}
