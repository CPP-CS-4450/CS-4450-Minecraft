package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptimizedChunk extends Chunk implements Renderable, Textured3D, BoundedContainer, InvertibleContainer {
    private final List<Block> blocks;

    public OptimizedChunk(final Block[][][] cubes, final List<Bound> bounds, final List<Block> blocks) {
        super(cubes, bounds);
        this.blocks = blocks;
    }


    @Override
    public void render(){
        blocks.forEach(Renderable::render);
    }

    @Override
    public List<Invertible> getInvertibles(){
        final List<Invertible> invertibles = new ArrayList<>();
        for(final Block block : blocks){
            if(block instanceof Invertible){
                invertibles.add((Invertible) block);
            }
        }

        return invertibles;
    }

    public List<Block> getBlocks(){ return Collections.unmodifiableList(blocks); }

}
