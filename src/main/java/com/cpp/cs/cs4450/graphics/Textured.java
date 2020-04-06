/***************************************************************
 * file: Textured.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose:
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

import org.newdawn.slick.opengl.Texture;

import java.util.Map;

public interface Textured {

    void setTextures(Map<?, ? extends Texture> textures);

    Map<?, ? extends Texture> getTextures();

    Map<?, String> getPaths();

}
