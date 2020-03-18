package com.cpp.cs.cs4450.graphics;

import org.newdawn.slick.opengl.Texture;

import java.util.Map;

public interface Invertible {

    void invert();

    boolean isInverted();

    Map<?, ? extends Texture> getTextures();

    void setInverts(Map<?, ? extends Texture> inverts);

    Map<?, ? extends Texture> getInverts();

    Object getInvertKey();

}
