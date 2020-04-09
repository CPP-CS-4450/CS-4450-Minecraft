package com.cpp.cs.cs4450.util;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public final class TextureLoader {
    private static final InternalTextureLoader loader = InternalTextureLoader.get();

    static {
        loader.setHoldTextureData(true);
        loader.setDeferredLoading(true);
    }

    private TextureLoader(){}

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

    private static InputStream readFileToStream(File file){
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream readFileToStream(final String path){
        return readFileToStream(new File(path));
    }

    static Texture computeTexture(String path){
        return computeTexture(new File(path));
    }

    static Texture computeTexture(File file){
        return computeTexture(readFileToStream(file));
    }

    static Texture computeTexture(InputStream is){
        try {
            return getTexture(is);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
