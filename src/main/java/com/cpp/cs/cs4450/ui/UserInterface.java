/***************************************************************
 * file: UserInterface.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Interface to interact with the user
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

public interface UserInterface {

    boolean up();

    boolean down();

    boolean left();

    boolean right();

    boolean forward();

    boolean backward();

    boolean quit();

    float getMouseHorizontalChange();

    float getMouseVerticalChange();

    void shutdown();

}
