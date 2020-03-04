package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.block.Block;
import com.cpp.cs.cs4450.model.block.BlockTypes.BlockType;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Random;

public final class BlockFactory {
    private static final Random RANDOM = new Random();

    private BlockFactory(){}

    public static Block create(BlockType type, float x, float y, float z, float size){
        return create(type, new Vector3f(x, y, z), size);
    }

    public static Block create(BlockType type, ReadableVector3f start, float size){
        return create(type, VertexUtils.calculateCubeVertices(start, size, size, size), size);
    }

    public static Block create(BlockType type, List<ReadableVector3f> vertices, float size){
        return new Block(size, type, vertices);
    }

    public static Block createRandom(float x, float y, float z, float size){
        return createRandom(new Vector3f(x, y, z), size);
    }

    public static Block createRandom(ReadableVector3f start, float size){
        return createRandom(VertexUtils.calculateCubeVertices(start, size, size, size), size);
    }

    public static Block createRandom(List<ReadableVector3f> vertices, float size){
        final BlockType[] types = BlockType.values();
        final BlockType randomType = types[RANDOM.nextInt(types.length)];

        return create(randomType, vertices, size);
    }

}
