/***************************************************************
 * file: OptimizedChunk.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Chunk that only renders viewable blocks
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.TexturedContainer;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a subclass of Chunk that renders only the blocks that
 * can be seen instead of all the blocks. This optimizes performance
 */
public class OptimizedChunk extends Chunk implements Renderable, BoundedContainer, InvertibleContainer, TexturedContainer {
    /**
     * Viewable blocks
     */
    private final List<Block> blocks;

    /**
     * Constructor
     *
     * @param cubes Cubes in chunk
     * @param bounds Bounds of Chunk
     * @param blocks Viewable blocks
     */
    public OptimizedChunk(final Block[][][] cubes, final List<Bound> bounds, final List<Block> blocks) {
        super(cubes, bounds);
        this.blocks = blocks;
    }

    /**
     * Renders the Chunk's viewable blocks
     */
    @Override
    public void render(){
        blocks.forEach(Renderable::render);
    }

    /**
     * Getter for container object's invertibles
     *
     * @return List of Invertibles
     */
    @Override
    public List<Invertible> getInvertibles(){
        final List<Invertible> invertibles = Collections.checkedList(new ArrayList<>(), Invertible.class);
        for(final Block block : blocks){
            if(block instanceof Invertible){
                invertibles.add((Invertible) block);
            }
        }

        return invertibles;
    }

    /**
     * Getter for container's textured objects
     *
     * @return List of Textured objects
     */
    @Override
    public List<Textured> getTextured(){
        final List<Textured> textured = Collections.checkedList(new ArrayList<>(), Textured.class);
        for(final Block block : blocks){
            if(block instanceof Textured){
                textured.add((Textured) block);
            }
        }

        return textured;
    }

    /**
     * Getter for chunk's viewable blocks
     * @return List of viewable blocks
     */
    public List<Block> getBlocks(){ return Collections.unmodifiableList(blocks); }

}
