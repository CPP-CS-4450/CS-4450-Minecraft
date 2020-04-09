package com.cpp.cs.cs4450.model.cube;


import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import org.lwjgl.util.vector.ReadableVector2f;

import java.io.File;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public enum BlockType {

    GRASS(TexturesConfiguration.GRASS_TEXTURES, TexturesConfiguration.GRASS_TEXTURE_VERTICES,true),
    SAND(TexturesConfiguration.SAND_TEXTURES, TexturesConfiguration.SAND_TEXTURE_VERTICES,true),
    WATER(TexturesConfiguration.WATER_TEXTURES, TexturesConfiguration.WATER_TEXTURE_VERTICES, false),
    DIRT(TexturesConfiguration.DIRT_TEXTURES, TexturesConfiguration.DIRT_TEXTURE_VERTICES,true),
    STONE(TexturesConfiguration.STONE_TEXTURES, TexturesConfiguration.STONE_TEXTURE_VERTICES, true),
    BEDROCK(TexturesConfiguration.BEDROCK_TEXTURES, TexturesConfiguration.BEDROCK_TEXTURE_VERTICES,true);


    private final Map<BlockSideType, String> paths;
    private final Map<BlockSideType, List<ReadableVector2f>> textureVertices;
    private final boolean solid;
    private final boolean multiTextured;

    BlockType(final Map<BlockSideType, String> paths, final Map<BlockSideType, List<ReadableVector2f>> textureVertices, final boolean solid) {
        this.paths = paths;
        this.textureVertices = textureVertices;
        this.solid = solid;
        this.multiTextured = paths.values().stream().distinct().count() > 1;
    }

    public final Map<BlockSideType, String> getPaths() {
        return paths;
    }

    public final boolean isSolid() {
        return solid;
    }

    public final Map<BlockSideType, List<ReadableVector2f>> getTextureVertices() {
        return textureVertices;
    }

    public final boolean isMultiTextured(){ return multiTextured; }

}
