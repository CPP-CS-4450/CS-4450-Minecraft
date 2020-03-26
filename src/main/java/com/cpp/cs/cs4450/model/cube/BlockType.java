package com.cpp.cs.cs4450.model.cube;


import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import org.lwjgl.util.vector.ReadableVector2f;

import static com.cpp.cs.cs4450.config.TexturesConfiguration.*;

import java.util.List;
import java.util.Map;

public enum BlockType {

    GRASS(TexturesConfiguration.GRASS_TEXTURES, GRASS_TEXTURE_VERTICES,true),
    SAND(TexturesConfiguration.SAND_TEXTURES, SAND_TEXTURE_VERTICES,true),
    WATER(TexturesConfiguration.WATER_TEXTURES, WATER_TEXTURE_VERTICES, false),
    DIRT(TexturesConfiguration.DIRT_TEXTURES, DIRT_TEXTURE_VERTICES,true),
    STONE(TexturesConfiguration.STONE_TEXTURES, STONE_TEXTURE_VERTICES, true),
    BEDROCK(TexturesConfiguration.BEDROCK_TEXTURES, BEDROCK_TEXTURE_VERTICES,true);


    private final Map<CubeSideType, String> paths;
    private final Map<CubeSideType, List<ReadableVector2f>> textureVertices;
    private final boolean solid;

    BlockType(final Map<CubeSideType, String> paths, final Map<CubeSideType, List<ReadableVector2f>> textureVertices, final boolean solid) {
        this.paths = paths;
        this.textureVertices = textureVertices;
        this.solid = solid;
    }

    public Map<CubeSideType, String> getPaths() {
        return paths;
    }

    public boolean solid() {
        return solid;
    }

    public Map<CubeSideType, List<ReadableVector2f>> getTextureVertices() {
        return textureVertices;
    }
}
