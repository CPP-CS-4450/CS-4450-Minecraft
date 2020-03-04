package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.block.Block;
import com.cpp.cs.cs4450.noise.SimplexNoise;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Chunk implements Renderable {
    private static final int SIZE = 30;
    private static final double PERSISTENCE = .25;
    private static final float CUBE_SIZE = 0.1f;

    private final Texture terrainTexture;
    private final Block[][][] blocks;
    private final SimplexNoise noise;
    private final int size;

    public Chunk(Block[][][] blocks, SimplexNoise noise, int size){
        try {
            this.blocks = blocks;
            this.noise = noise;
            this.size = size;
            this.terrainTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("./assets/terrain-test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    @Override
    public void render() {
        for(final Block[][] matrix : blocks){
            for(final Block[] array : matrix){
                for(Block block : array){
                    if(block != null){
                        block.render();
                    }
                }
            }
        }
    }
}
