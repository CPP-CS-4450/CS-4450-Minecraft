/***************************************************************
 * file: TextureLoader.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Loads textures for program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public final class TextureLoader {
    private static final InternalTextureLoader loader = InternalTextureLoader.get();

    static {
        loader.setHoldTextureData(true);
        loader.setDeferredLoading(true);
    }

    private TextureLoader(){
        throw new UnsupportedOperationException();
    }

    public static Texture getTexture(final String path) throws IOException {
        return getTexture(new File(path));
    }

    public static Texture getTexture(final File file) throws IOException {
        return getTexture(new FileInputStream(file));
    }

    public static Texture getTexture(final InputStream is) throws IOException {
        return loader.getTexture(is, is.toString(), false, GL11.GL_LINEAR);
    }

    public static Texture getTexture(final ImageData image) throws IOException {
        return loader.getTexture(image, GL11.GL_LINEAR);
    }

    private static InputStream readFileToStream(final File file){
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream readFileToStream(final String path){
        return readFileToStream(new File(path));
    }

    static Texture computeTexture(final String path){
        return computeTexture(new File(path));
    }

    static Texture computeTexture(final File file){
        return computeTexture(readFileToStream(file));
    }

    static Texture computeTexture(final InputStream is){
        try {
            return getTexture(is);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
