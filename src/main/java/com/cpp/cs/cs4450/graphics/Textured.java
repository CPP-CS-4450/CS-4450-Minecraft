package com.cpp.cs.cs4450.graphics;

import org.newdawn.slick.opengl.Texture;

public interface Textured {

    Texture getTexture();

    void setTexture(Texture texture, int size);

}
