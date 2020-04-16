/***************************************************************
 * file: Chunk.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Acts as the container that holds all the games terrain blocks
 *
 ****************************************************************/
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

/**
 * This class is meant to represent a Chunk of blocks, in this case, act as the
 * terrain for the program. Here is where all the games terrain blocks will be stored.
 */
public class Chunk implements Renderable, BoundedContainer, InvertibleContainer, TexturedContainer {
    /**
     * 3D array of all the Blocks in the chunk
     */
    protected final Block[][][] cubes;
    /**
     * Bounds of all the blocks
     */
    protected final List<Bound> bounds;

    /**
     * Constructor
     *
     * @param cubes Cubes in Chunk
     * @param bounds Bounds of cubes in chunk
     */
    public Chunk(final Block[][][] cubes, final List<Bound> bounds) {
        this.cubes = cubes;
        this.bounds = bounds;
    }

    /**
     * Renders the Chunk's blocks
     */
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

    /**
     * Getter for Chunk's blocks
     * @return 3D Matrix of chunk's blocks
     */
    public Block[][][] getCubes(){ return cubes; }

    /**
     * Inverts the blocks in the Chunk
     */
    @Override
    public void invert() {
        getInvertibles().parallelStream().forEach(Invertible::invert);
    }

    /**
     * Getter for container object's invertibles
     *
     * @return List of Invertibles
     */
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

    /**
     * Getter for Chunk's bounds
     *
     * @return Bounds of all blocks in Chunk
     */
    @Override
    public List<Bound> getBounds() {
        return Collections.unmodifiableList(bounds);
    }

    /**
     * Getter for container's textured objects
     *
     * @return List of Textured objects
     */
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
