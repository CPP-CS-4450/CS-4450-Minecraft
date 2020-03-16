package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract class TexturedBlock extends Block implements Renderable, Textured, Bounded {
    protected static final List<ReadableVector2f> TEX_COORDS = List.of(
            new Vector2f(1,1),
            new Vector2f(0,1),
            new Vector2f(0,0),
            new Vector2f(1,0)
    );

    protected final BlockType type;
    protected final BoundingBox boundingBox;
    protected Map<CubeSideType, String> paths;
    protected Map<CubeSideType, Texture> textures;


    public TexturedBlock(
            final float x,
            final float y,
            final float z,
            final BlockType type,
            final BoundingBox boundingBox,
            final List<CubeSide> sides,
            final Map<CubeSideType, String> paths
    ) {
        super(x, y, z, sides);
        this.type = type;
        this.boundingBox = boundingBox;
        this.paths = paths;
    }

    @Override
    public void setTextures(final Map<?, ? extends Texture> textures) {
        this.textures = castTexturesMapKeys(textures);
    }

    @Override
    public Map<CubeSideType, ? extends Texture> getTextures() {
        return textures;
    }

    @Override
    public Map<?, String> getPaths() {
        return paths;
    }

    @Override
    public Bounds getBounds() {
        return boundingBox;
    }

    @Override
    public double getWidth() {
        return boundingBox.getWidth();
    }

    @Override
    public double getHeight() {
        return boundingBox.getHeight();
    }

    @Override
    public double getDepth() {
        return boundingBox.getDepth();
    }

    @Override
    public String toString(){
        return  "\nTextured Cube" +
                "\nX:\t" + getPositionX() +
                "\nY:\t" + getPositionY() +
                "\nZ:\t" + getPositionZ();
    }

    public BlockType getType() {
        return type;
    }

    private static Map<CubeSideType, Texture> castTexturesMapKeys(final Map<?, ? extends Texture> textures){
        Map<CubeSideType, Texture> casts = Collections.checkedMap(new HashMap<>(), CubeSideType.class, Texture.class);
        for(Object key : textures.keySet()){
            casts.put((CubeSideType) key, textures.get(key));
        }

        return casts;
    }

}
