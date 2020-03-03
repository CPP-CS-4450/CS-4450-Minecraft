package com.cpp.cs.cs4450.model;

import com.cpp.cs.cs4450.model.block.Block;
import com.cpp.cs.cs4450.noise.SimplexNoise;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Chunk {
    private static final int SIZE = 30;
    private static final double PERSISTENCE = .25;
    private static final float CUBE_SIZE = 0.1f;

    private Texture terrainTexture;
    private Block[][][] blocks;
    private SimplexNoise noise;

    public Chunk(){
        try {
            this.noise = new SimplexNoise(32, PERSISTENCE, (int) System.currentTimeMillis());
            this.terrainTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("./assets/terrain-test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private void init(){
        this.blocks = new Block[SIZE][SIZE][SIZE];



    }



}
