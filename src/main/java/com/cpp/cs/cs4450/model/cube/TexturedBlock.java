package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import com.cpp.cs.cs4450.util.TextureInverter;
import javafx.geometry.Bounds;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public abstract class TexturedBlock extends Block implements Renderable, Textured, Bounded, Invertible {
    protected static final List<ReadableVector2f> TEX_COORDS = List.of(
            new Vector2f(1,1),
            new Vector2f(0,1),
            new Vector2f(0,0),
            new Vector2f(1,0)
    );

    protected final BlockType type;
    protected final Bounds bounds;
    protected Map<CubeSideType, String> paths;
    protected Map<CubeSideType, Texture> textures;
    protected Map<CubeSideType, Texture> inverts;
    protected boolean inverted;


    public TexturedBlock(
            final float x,
            final float y,
            final float z,
            final BlockType type,
            final Bounds bounds,
            final List<CubeSide> sides,
            final Map<CubeSideType, String> paths
    ) {
        super(x, y, z, sides);
        this.type = type;
        this.bounds = bounds;
        this.paths = paths;
        this.inverted = false;
    }

    @Override
    public void setTextures(final Map<?, ? extends Texture> textures) {
        this.textures = castTexturesMapKeys(textures);
    }

    @Override
    public void setInverts(Map<?, ? extends Texture> inverts){
        this.inverts = castTexturesMapKeys(inverts);
    }

    @Override
    public Map<?, ? extends Texture> getInverts(){
        return inverts;
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
        return bounds;
    }

    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    @Override
    public double getHeight() {
        return bounds.getHeight();
    }

    @Override
    public double getDepth() {
        return bounds.getDepth();
    }

    @Override
    public void invert(){ inverted = !inverted; }

    @Override
    public boolean isInverted(){ return inverted; }

    @Override
    public Object getInvertKey(){
        return getType();
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

    protected static Map<CubeSideType, Texture> castTexturesMapKeys(final Map<?, ? extends Texture> textures){
        Map<CubeSideType, Texture> casts = Collections.checkedMap(new HashMap<>(), CubeSideType.class, Texture.class);
        for(Object key : textures.keySet()){
            casts.put((CubeSideType) key, textures.get(key));
        }

        return casts;
    }

    protected static Map<CubeSideType, Texture> invertTextures(final Map<CubeSideType, Texture> textures){
        return textures.entrySet().stream().collect(Collectors.toMap(
                Entry::getKey,
                e -> TextureInverter.invert(e.getValue()),
                (k0, k1) -> k0
        ));
    }

}
