package com.cpp.cs.cs4450.util;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class TextureInverter {
    private static final String INVALID_TEXTURE_SIZE_ERROR_MESSAGE = "Texture size dimensions must be power of 2";

    private TextureInverter(){}

    public static Texture invert(final Texture texture){
        try {
            final byte[] data = texture.getTextureData();

            final int n = data.length;
            if(!(n != 0 && (( n & (n - 1)) == 0))){
                throw new RuntimeException(INVALID_TEXTURE_SIZE_ERROR_MESSAGE);
            }

            final int m = 4;
            final int w = texture.getTextureWidth();
            final int h = texture.getTextureHeight();

            final byte[] inverted = new byte[n];
            for(int i = 0; i < n; ++i){
                for(int j = 0; j < m - 1; ++j){
                    inverted[i] = (byte) ~data[i++];
                }
                inverted[i] = data[i];
            }

            return TextureLoader.getTexture(wrap(inverted, w, h));
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

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

        return byteListToArray(invertedBytes);
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
        return Collections.unmodifiableList(Arrays.asList(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte()));
    }

    private static byte[] byteListToArray(final List<Byte> list){
        final int n = list.size();

        final byte[] array = new byte[n];
        for(int i = 0; i < n; ++i){
            array[i] = list.get(i);
        }

        return array;
    }

    public static Map<?, ? extends Texture> invert(final Map<?, ? extends Texture> textures){
        return textures.entrySet().stream().collect(
                Collectors.toMap(
                        Entry::getKey,
                        e -> invert(e.getValue()),
                        (k0, k1) -> k0
                )
        );
    }

    private static ImageData wrap(final byte[] bytes, final int width, final int height){
        return new ImageByteBufferWrapper(bytes, width, height);
    }

    private static class ImageByteBufferWrapper implements ImageData {
        private final byte[] bytes;
        private final int width;
        private final int height;

        private ImageByteBufferWrapper(final byte[] bytes, final int width, final int height) {
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
            return InternalTextureLoader.get2Fold(width);
        }

        @Override
        public int getTexHeight() {
            return InternalTextureLoader.get2Fold(height);
        }

        @Override
        public ByteBuffer getImageBufferData() {
            return (ByteBuffer) ByteBuffer.allocateDirect(bytes.length)
                    .order(ByteOrder.nativeOrder())
                    .put(bytes)
                    .flip();

        }
    }

}
