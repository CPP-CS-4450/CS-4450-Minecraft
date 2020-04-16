/***************************************************************
 * file: BlockTextureLoader.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Loads Textures for Blocks
 *
 ****************************************************************/
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

/**
 * Utility class for loading block textures
 */
public final class BlockTextureLoader {

    /**
     * Constructor
     */
    private BlockTextureLoader(){}

    /**
     * Creates block texture
     *
     * @param type block type
     * @return block texture
     */
    public static BlockTexture getBlockTexture(final BlockType type){
        return type.isMultiTextured() ? getBlockMultipleTexture(type) : getBlockSingleTexture(type);
    }

    /**
     * Creates block single texture
     *
     * @param type block type
     * @return block texture
     */
    private static BlockTexture getBlockSingleTexture(final BlockType type){
        return new BlockTexture(getTextures(Collections.singletonList(type.getPaths().entrySet().iterator().next())), type);
    }

    /**
     * Creates block multi-texture
     *
     * @param type block type
     * @return block texture
     */
    private static BlockTexture getBlockMultipleTexture(final BlockType type){
        return new BlockTexture(getTextures(type.getPaths()), type);
    }

    /**
     * Reads textures from paths
     *
     * @param paths paths to texture files
     * @return Map of textures
     */
    private static Map<BlockSideType, Texture> getTextures(final Map<BlockSideType, String> paths){
        return getTextures(paths.entrySet());
    }

    /**
     * Reads textures from paths
     *
     * @param paths paths to texture files
     * @return Map of textures
     */
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

    /**
     * Inverts texture
     *
     * @param texture texture to invert
     * @return inverted texture
     */
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

    /**
     * Wrapper class for block texture
     */
    public static class BlockTexture {
        /**
         * Textures
         */
        private final Map<BlockSideType, Texture> textures;
        /**
         * Types
         */
        private final BlockType type;

        /**
         * Constructor
         *
         * @param textures textures
         * @param type texture type
         */
        public BlockTexture(final Map<BlockSideType, Texture> textures, final BlockType type) {
            this.textures = textures;
            this.type = type;
        }

        /**
         * Getter for textures
         *
         * @return map of textures
         */
        public Map<BlockSideType, Texture> getTextures() {
            return textures;
        }

        /**
         * Getter for type
         *
         * @return texture type
         */
        public BlockType getType() {
            return type;
        }

        /**
         * Getter for texture
         *
         * @param side block side
         * @return texture for side
         */
        public Texture getTexture(final BlockSide side){
            return getTexture(side.getType());
        }

        /**
         * Getter for texture
         *
         * @param side block side
         * @return texture for side
         */
        public Texture getTexture(final BlockSideType side){
            if(type.isMultiTextured()){
                return textures.get(side);
            }

            return getTexture();
        }

        /**
         * Getter for texture
         *
         * @return texture
         */
        public Texture getTexture(){
            return textures.values().iterator().next();
        }

        /**
         * Checks if equal
         *
         * @param obj object to check
         * @return True if equal, false otherwise
         */
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
