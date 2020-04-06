/***************************************************************
 * file: BlockTypes.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Enums that represent the different constant types of blocks in the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.cube;


import com.cpp.cs.cs4450.config.TexturesConfiguration;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;

import java.util.Map;

public enum BlockType {

    GRASS(TexturesConfiguration.GRASS_TEXTURES, true),
    SAND(TexturesConfiguration.SAND_TEXTURES, true),
    WATER(TexturesConfiguration.WATER_TEXTURES, false),
    DIRT(TexturesConfiguration.DIRT_TEXTURES, true),
    STONE(TexturesConfiguration.STONE_TEXTURES, true),
    BEDROCK(TexturesConfiguration.BEDROCK_TEXTURES, true);


    private final Map<CubeSideType, String> paths;
    private final boolean solid;

    BlockType(final Map<CubeSideType, String> paths, final boolean solid) {
        this.paths = paths;
        this.solid = solid;
    }

    public Map<CubeSideType, String> getPaths() {
        return paths;
    }

    public boolean solid() {
        return solid;
    }

}
