package com.cpp.cs.cs4450.model.cube;

public enum CubeBoxType {

    GRASS("assets/grass.png"),
    SAND("assets/sand.png"),
    WATER("assets/water.png"),
    DIRT("assets/dirt.png"),
    STONE("assets/stone.png"),
    BEDROCK("assets/bedrock.png");


    private final String path;

    CubeBoxType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
