package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.graphics.Invertible;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TextureInverter {

    private static final InternalTextureLoader loader = InternalTextureLoader.get();


    public static void invert(List<Invertible> invertibles){

        Map<Object, InvertTextureWrapper> cache = new HashMap<>();

        for(Invertible invertible : invertibles){
            Object key = invertible.getInvertKey();
            Map<?, ? extends Texture> textures = invertible.getTextures();

            Map<?, ? extends Texture> inverts = cache.computeIfAbsent(key, k -> wrap(textures)).getInvertsMap();
            invertible.setInverts(inverts);
        }

    }

    public static Texture invertOpt(Texture texture){
        try {
            List<Color> colors = bytesToColors(texture.getTextureData());

            Map<Color, Color> cache = new HashMap<>();

            List<Color> inverted = new ArrayList<>(colors.size());
            for (Color color : colors) {
                inverted.add(cache.computeIfAbsent(color, TextureInverter::invert));
            }


            int w = texture.getImageWidth(), h = texture.getImageHeight();

            ImageBuffer buffer = new ImageBuffer(w, h);

            for (int x = 0; x < w; ++x) {
                for (int y = 0; y < h; ++y) {
                    Color invert = inverted.get(x + (y * w));
                    buffer.setRGBA(x, y, invert.getRed(), invert.getGreen(), invert.getBlue(), invert.getAlpha());
                }
            }

            return loader.getTexture(buffer, GL11.GL_LINEAR);
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static Texture invert(Texture texture){
        try {
            List<Color> invertedColors = bytesToColors(texture.getTextureData()).stream()
                    .map(TextureInverter::invert)
                    .collect(Collectors.toList());

            int w = texture.getImageWidth(), h = texture.getImageHeight();

            ImageBuffer buffer = new ImageBuffer(w, h);

            for(int x = 0; x < w; ++x){
                for(int y = 0; y < h; ++y){
                    Color inverted = invertedColors.get(x + (y * w));
                    buffer.setRGBA(x, y, inverted.getRed(), inverted.getGreen(), inverted.getBlue(), inverted.getAlpha());
                }
            }

            return loader.getTexture(buffer, GL11.GL_LINEAR);
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static Color invert(Color color){
        return new Color(1 - color.getRed(), 1 - color.getGreen(), 1 - color.getBlue(), color.getAlpha());
    }

    public static byte[] invert(byte[] bytes){
        List<Color> invertedColors = bytesToColors(bytes).parallelStream()
                .map(TextureInverter::invert)
                .collect(Collectors.toList());

        List<Byte> invertedBytes = new ArrayList<>(invertedColors.size() * 4);
        for(Color invertedColor : invertedColors){
            invertedBytes.addAll(colorToByte(invertedColor));
        }

        return listToArray(invertedBytes);
    }


    private static List<Color> bytesToColors(byte[] bytes){
        List<Color> colors = new ArrayList<>(bytes.length / 4);

        int n = bytes.length;
        for(int i = 0; i < n; i += 4){
            colors.add(new Color(bytes[i], bytes[i + 1], bytes[i + 2], bytes[i + 3]));
        }


        return colors;
    }

    private static List<Byte> colorToByte(Color color){
        return List.of(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
    }

    private static byte[] listToArray(List<Byte> list){
        int n = list.size();

        byte[] array = new byte[n];
        for(int i = 0; i < n; ++i){
            array[i] = list.get(i);
        }

        return array;
    }

    public static Map<?, ? extends Texture> invert(Map<?, ? extends Texture> textures){
        return textures.entrySet().stream().collect(Collectors.toMap(
                Entry::getKey,
                e -> invert(e.getValue()),
                (k0, k1) -> k0
        ));
    }

    private static InvertTextureWrapper wrap(Map<?, ? extends Texture> textures){
        return new InvertTextureWrapper(invert(textures));
    }

    private static final class InvertTextureWrapper {
        private final Map<?, ? extends Texture> inverts;

        private InvertTextureWrapper(Map<?, ? extends Texture> inverts){
            this.inverts = inverts;
        }

        public Map<?, ? extends Texture> getInvertsMap() {
            return inverts;
        }
    }

}