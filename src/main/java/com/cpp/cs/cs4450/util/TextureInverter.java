package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.graphics.Invertible;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.CompositeImageData;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.Texture;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class TextureInverter {
    private static final InternalTextureLoader loader = InternalTextureLoader.get();

    private TextureInverter(){}

    public static void invert(final List<Invertible> invertibles){
        final Map<Object, InvertTextureWrapper> cache = new HashMap<>();

        for(final Invertible invertible : invertibles){
            final Object key = invertible.getInvertKey();
            final Map<?, ? extends Texture> textures = invertible.getTextures();
            invertible.setInverts(cache.computeIfAbsent(key, k -> wrap(textures)).getInvertsMap());
        }

    }

    public static Texture invert(final Texture texture){
        try {
            final List<Color> invertedColors = bytesToColors(texture.getTextureData()).stream()
                    .map(TextureInverter::invert)
                    .collect(Collectors.toList());

            final int w = texture.getImageWidth(), h = texture.getImageHeight();

            byte[] bytes = listToArray(invertedColors.stream().map(TextureInverter::colorToByte).flatMap(Collection::stream).collect(Collectors.toList()));

            final ImageData buffer = new ImageByteBufferWrapper(bytes, w, h);


            return loader.getTexture(buffer, GL11.GL_LINEAR);
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /*
    public static Texture invert(final Texture texture){
        try {
            final List<Color> invertedColors = bytesToColors(texture.getTextureData()).stream()
                    .map(TextureInverter::invert)
                    .collect(Collectors.toList());

            final int w = texture.getImageWidth(), h = texture.getImageHeight();

            final ImageBuffer buffer = new ImageBuffer(w, h);
            for(int x = 0; x < w; ++x){
                for(int y = 0; y < h; ++y){
                    final Color inverted = invertedColors.get(x + (y * w));
                    buffer.setRGBA(x, y, inverted.getRed(), inverted.getGreen(), inverted.getBlue(), inverted.getAlpha());
                }
            }

            return loader.getTexture(buffer, GL11.GL_LINEAR);
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

     */


    public static Color invert(final Color color){
        return new Color(1 - color.getRed(), 1 - color.getGreen(), 1 - color.getBlue(), color.getAlpha());
    }

    public static byte[] invert(final byte[] bytes){
        final List<Color> invertedColors = bytesToColors(bytes).stream()
                .map(TextureInverter::invert)
                .collect(Collectors.toList());

        final List<Byte> invertedBytes = new ArrayList<>(invertedColors.size() * 4);
        for(final Color invertedColor : invertedColors){
            invertedBytes.addAll(colorToByte(invertedColor));
        }

        return listToArray(invertedBytes);
    }


    private static List<Color> bytesToColors(final byte[] bytes){
        final List<Color> colors = new ArrayList<>(bytes.length / 4);

        final int n = bytes.length;
        for(int i = 0; i < n; i += 4){
            colors.add(new Color(bytes[i], bytes[i + 1], bytes[i + 2], bytes[i + 3]));
        }

        return colors;
    }

    private static List<Byte> colorToByte(final Color color){
        return List.of(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
    }

    private static byte[] listToArray(final List<Byte> list){
        final int n = list.size();

        final byte[] array = new byte[n];
        for(int i = 0; i < n; ++i){
            array[i] = list.get(i);
        }

        return array;
    }

    public static Map<?, ? extends Texture> invert(final Map<?, ? extends Texture> textures){
        return textures.entrySet().stream().collect(Collectors.toMap(
                Entry::getKey,
                e -> invert(e.getValue()),
                (k0, k1) -> k0
        ));
    }

    private static InvertTextureWrapper wrap(final Map<?, ? extends Texture> textures){
        return new InvertTextureWrapper(invert(textures));
    }

    private static ImageData bytesToImageData(byte[] bytes) throws IOException {
        LoadableImageData image = new CompositeImageData();
        image.loadImage(new ByteArrayInputStream(bytes), false, true, null);

        return image;
    }

    private static final class InvertTextureWrapper {
        private final Map<?, ? extends Texture> inverts;

        private InvertTextureWrapper(final Map<?, ? extends Texture> inverts){
            this.inverts = inverts;
        }

        public Map<?, ? extends Texture> getInvertsMap() {
            return inverts;
        }
    }

    private static class ImageByteBufferWrapper implements ImageData {
        private final byte[] bytes;
        private final int width;
        private final int height;

        private ImageByteBufferWrapper(byte[] bytes, int width, int height) {
            this.bytes = bytes;
            this.width = width;
            this.height = height;
        }


        @Override
        public int getDepth() {
            return 32;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public int getTexWidth() {
            return TextureLoader.get2Power(width);
        }

        @Override
        public int getTexHeight() {
            return TextureLoader.get2Power(height);
        }

        @Override
        public ByteBuffer getImageBufferData() {
            return ByteBuffer.allocateDirect(bytes.length)
                    .order(ByteOrder.nativeOrder())
                    .put(bytes)
                    .flip();
        }
    }


}
