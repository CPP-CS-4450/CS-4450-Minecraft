package com.cpp.cs.cs4450.graphics;

import org.newdawn.slick.opengl.Texture;

import java.util.Map;

@FunctionalInterface
public interface Textured {

    Map<?, ? extends Texture> getTextures();

}
