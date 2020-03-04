package com.cpp.cs.cs4450.model.block;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public final class BlockTypes{

    public static final float TEXTURE_FILE_SIZE = 1024f;

    public static final int TEXTURE_FILE_DIMENSIONS = 16;

    public static final float TEXTURE_FILE_OFFSET = (TEXTURE_FILE_SIZE / TEXTURE_FILE_DIMENSIONS) / TEXTURE_FILE_SIZE;


    public enum BlockSide{
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    private static final Map<BlockSide, Entry<Integer, Integer>> GRASS_COORDS = toBlockSideMap(
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );
    private static final Map<BlockSide, Entry<Integer, Integer>> SAND_COORDS = toBlockSideMap(
            toPair(0, 1),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );
    private static final Map<BlockSide, Entry<Integer, Integer>> WATER_COORDS = toBlockSideMap(
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );
    private static final Map<BlockSide, Entry<Integer, Integer>> DIRT_COORDS = toBlockSideMap(
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );
    private static final Map<BlockSide, Entry<Integer, Integer>> STONE_COORDS = toBlockSideMap(
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );
    private static final Map<BlockSide, Entry<Integer, Integer>> BEDROCK_COORDS = toBlockSideMap(
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0),
            toPair(3, 0)
    );



    public enum BlockType {
        GRASS(toPair(4, 0)),
        SAND(toPair(2,1)),
        WATER(toPair(14,12)),
        DIRT(toPair(2,1)),
        STONE(toPair(1,0)),
        BEDROCK(toPair(1,1));

       //private final Map<BlockSide, Entry<Integer, Integer>> texCords;

        //BlockType(Map<BlockSide, Entry<Integer, Integer>> texCords){
          //  this.texCords = texCords;
        //}

        private final Entry<Integer, Integer> texCord;

        BlockType(Entry<Integer, Integer> texCord){
            this.texCord = texCord;
        }

        public Entry<Integer, Integer> getTexCord() {
            return texCord;
        }
    }


    private static Map<BlockSide, Entry<Integer, Integer>> toBlockSideMap(
            Entry<Integer, Integer> top,
            Entry<Integer, Integer> bottom,
            Entry<Integer, Integer> left,
            Entry<Integer, Integer> right,
            Entry<Integer, Integer> front,
            Entry<Integer, Integer> back
    ){
        Map<BlockSide, Entry<Integer, Integer>> map = new EnumMap<>(BlockSide.class);
        map.put(BlockSide.TOP, top);
        map.put(BlockSide.BOTTOM, bottom);
        map.put(BlockSide.LEFT, left);
        map.put(BlockSide.RIGHT, right);
        map.put(BlockSide.FRONT, front);
        map.put(BlockSide.BACK, back);

        return Collections.unmodifiableMap(map);
    }

    private static Entry<Integer, Integer> toPair(int x, int y){
        return new SimpleImmutableEntry<>(x,y);
    }

}
