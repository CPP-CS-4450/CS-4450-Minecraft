/***************************************************************
 * file: TextureLoader.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Util class that loaders textures into the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.graphics.Textured;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class TextureLoader {
    private static final String PNG_TYPE = "PNG";

    /*
    Loads the textures of the textured items in list
     */
    public static void load(final List<Textured> textured) throws IOException {

        final Map<String, InputStream> cache = new HashMap<>();
        for(Textured texd : textured){
            final Map<?, String> paths = texd.getPaths();

            final Map<Object, Texture> textures = new HashMap<>();
            for(final Object key : paths.keySet()){
                final String path = paths.get(key);
                final InputStream is = cache.computeIfAbsent(path, p -> readFile(path));
                textures.put(key, org.newdawn.slick.opengl.TextureLoader.getTexture(PNG_TYPE, is));
            }
            texd.setTextures(textures);
        }
    }

    /*
    Reads the file for the given path
     */
    private static InputStream readFile(final String path){
        try {
            return Files.newInputStream(Paths.get(path), StandardOpenOption.READ);
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
