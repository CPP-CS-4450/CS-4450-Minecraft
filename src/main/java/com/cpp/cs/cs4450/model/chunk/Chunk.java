package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.TexturedContainer;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Chunk implements Renderable, BoundedContainer, InvertibleContainer, TexturedContainer {
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
    public void invert() {
        getInvertibles().parallelStream().forEach(Invertible::invert);
    }

    @Override
    public List<Invertible> getInvertibles() {
        final List<Invertible> invertibles = Collections.checkedList(new ArrayList<>(), Invertible.class);
        for(final Cube[][] matrix : cubes){
            for(final Cube[] array : matrix){
                for(Cube cube : array){
                    if(cube instanceof Invertible){
                        invertibles.add((Invertible) cube);
                    }
                }
            }
        }

        return Collections.unmodifiableList(invertibles);
    }

    @Override
    public List<Bound> getBounds() {
        return Collections.unmodifiableList(bounds);
    }

    @Override
    public List<Textured> getTextured(){
        final List<Textured> textured = Collections.checkedList(new ArrayList<>(), Textured.class);
        for(final Cube[][] matrix : cubes){
            for(final Cube[] array : matrix){
                for(Cube cube : array){
                    if(cube instanceof Textured){
                        textured.add((Textured) cube);
                    }
                }
            }
        }

        return Collections.unmodifiableList(textured);
    }

}
