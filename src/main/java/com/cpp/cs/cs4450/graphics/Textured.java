/***************************************************************
 * file: Textured.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines behavior for an object that is textured
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

import org.newdawn.slick.opengl.Texture;

import java.util.Map;

/**
 * Interface for textured game objects
 */
@FunctionalInterface
public interface Textured {

    /**
     * Returns map of textures
     *
     * @return map of textures
     */
    Map<?, ? extends Texture> getTextures();

}
