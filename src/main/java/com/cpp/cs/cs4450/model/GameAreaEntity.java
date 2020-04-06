/***************************************************************
 * file: GameAreaEntity.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Interface that defines behavior for a entity in the programs game area
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

import org.lwjgl.util.vector.ReadableVector3f;

public interface GameAreaEntity {

    /*
    Getter for objects X plane position
     */
    float getPositionX();

    /*
    Getter for objects Y plane position
     */
    float getPositionY();

    /*
    Getter for objects Z plane position
     */
    float getPositionZ();

    /*
    Getter for objects position
     */
    ReadableVector3f getPosition3f();

}
