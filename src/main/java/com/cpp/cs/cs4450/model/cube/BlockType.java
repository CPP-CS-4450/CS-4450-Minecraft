/***************************************************************
 * file: BlockType.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Predefined values for types of blocks in the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.cube;


import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import org.lwjgl.util.vector.ReadableVector2f;

import java.util.List;
import java.util.Map;

/**
 * Enum of Block Types
 */
public enum BlockType {

    GRASS(TexturesConfiguration.GRASS_TEXTURES, TexturesConfiguration.GRASS_TEXTURE_VERTICES,true),
    SAND(TexturesConfiguration.SAND_TEXTURES, TexturesConfiguration.SAND_TEXTURE_VERTICES,true),
    WATER(TexturesConfiguration.WATER_TEXTURES, TexturesConfiguration.WATER_TEXTURE_VERTICES, false),
    DIRT(TexturesConfiguration.DIRT_TEXTURES, TexturesConfiguration.DIRT_TEXTURE_VERTICES,true),
    STONE(TexturesConfiguration.STONE_TEXTURES, TexturesConfiguration.STONE_TEXTURE_VERTICES, true),
    BEDROCK(TexturesConfiguration.BEDROCK_TEXTURES, TexturesConfiguration.BEDROCK_TEXTURE_VERTICES,true);

    /**
     * Map of Sides to Texture file paths
     */
    private final Map<BlockSideType, String> paths;
    /**
     * Map of sides to texture vertices
     */
    private final Map<BlockSideType, List<ReadableVector2f>> textureVertices;
    /**
     * Solid flag
     */
    private final boolean solid;
    /**
     * Multi-textured flag
     */
    private final boolean multiTextured;

    /**
     * Constructor
     *
     * @param paths Map of paths
     * @param textureVertices Texture vertices
     * @param solid True if block is solid, false otherwise.
     */
    BlockType(final Map<BlockSideType, String> paths, final Map<BlockSideType, List<ReadableVector2f>> textureVertices, final boolean solid) {
        this.paths = paths;
        this.textureVertices = textureVertices;
        this.solid = solid;
        this.multiTextured = paths.values().stream().distinct().count() > 1;
    }

    /**
     * Getter for paths
     *
     * @return map of paths
     */
    public final Map<BlockSideType, String> getPaths() {
        return paths;
    }

    /**
     * Getter for solid flag
     *
     * @return True if solid, false otherwise
     */
    public final boolean isSolid() {
        return solid;
    }

    /**
     * Getter for texture vertices
     *
     * @return map of texture vertices
     */
    public final Map<BlockSideType, List<ReadableVector2f>> getTextureVertices() {
        return textureVertices;
    }

    /**
     * Returns if type is multi-textured
     *
     * @return True if multi-textured, false otherwise
     */
    public final boolean isMultiTextured(){ return multiTextured; }

}
