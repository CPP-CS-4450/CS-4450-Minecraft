package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSide;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import org.newdawn.slick.opengl.Texture;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BlockTextureLoader {

    private BlockTextureLoader(){}


    public static BlockTexture getBlockTexture(final BlockType type){
        return type.isMultiTextured() ? getBlockMultipleTexture(type) : getBlockSingleTexture(type);
    }

    private static BlockTexture getBlockSingleTexture(final BlockType type){
        return new BlockTexture(getTextures(Collections.singletonList(type.getPaths().entrySet().iterator().next())), type);
    }

    private static BlockTexture getBlockMultipleTexture(final BlockType type){
        return new BlockTexture(getTextures(type.getPaths()), type);
    }

    private static Map<BlockSideType, Texture> getTextures(final Map<BlockSideType, String> paths){
        return getTextures(paths.entrySet());
    }

    private static Map<BlockSideType, Texture> getTextures(final Collection<Entry<BlockSideType, String>> paths){
        return paths.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toMap(
                                Entry::getKey,
                                e -> TextureLoader.computeTexture(e.getValue()),
                                (k0, k1) -> k0,
                                () -> new EnumMap<>(BlockSideType.class)
                        ),
                        Collections::unmodifiableMap
                )
        );
    }

    public static BlockTexture invertBlockTexture(final BlockTexture texture){
        return new BlockTexture(
                texture.getTextures().entrySet().stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Entry::getKey,
                                        e -> TextureInverter.invert(e.getValue()),
                                        (k0, k1) -> k0,
                                        () -> new EnumMap<>(BlockSideType.class)
                                ),
                                Collections::unmodifiableMap
                        )),
                texture.type
        );
    }

    public static class BlockTexture {
        private final Map<BlockSideType, Texture> textures;
        private final BlockType type;

        public BlockTexture(final Map<BlockSideType, Texture> textures, final BlockType type) {
            this.textures = textures;
            this.type = type;
        }


        public Map<BlockSideType, Texture> getTextures() {
            return textures;
        }

        public BlockType getType() {
            return type;
        }

        public Texture getTexture(final BlockSide side){
            return getTexture(side.getType());
        }

        public Texture getTexture(final BlockSideType side){
            if(type.isMultiTextured()){
                return textures.get(side);
            }

            return getTexture();
        }

        public Texture getTexture(){
            return textures.values().iterator().next();
        }

        @Override
        public boolean equals(final Object obj){
            if(obj == null) return false;
            if(obj == this) return true;
            if(obj.getClass() != getClass()){
                return false;
            }

            final BlockTexture other = (BlockTexture) obj;

            return Objects.deepEquals(this.textures, other.textures)
                    && Objects.deepEquals(this.type, other.type);
        }

    }

}
