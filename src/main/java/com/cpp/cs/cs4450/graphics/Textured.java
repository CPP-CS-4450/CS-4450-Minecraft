package com.cpp.cs.cs4450.graphics;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import org.newdawn.slick.opengl.Texture;

import java.util.Map;

public interface Textured extends Invertible {

    void setTextures(Map<?, ? extends Texture> textures);

    Map<?, ? extends Texture> getTextures();

    Map<?, String> getPaths();

}
