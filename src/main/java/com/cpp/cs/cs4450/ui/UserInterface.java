/***************************************************************
 * file: UserInterface.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Definition of functionality for a class that is repsonsible for interacting with the user
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

public interface UserInterface {

    /*
    Returns true if user presses the up signal
     */
    boolean up();

    /*
    Returns true if user presses the down signal
     */
    boolean down();

    /*
    Returns true if user presses the left signal
     */
    boolean left();

    /*
    Returns true if user presses the right signal
     */
    boolean right();

    /*
    Returns true if user presses the forward signal
     */
    boolean forward();

    /*
    Returns true if user presses the up signal
     */
    boolean backward();

    /*
    Returns true if user presses the up signal
     */
    boolean quit();

    /*
    Returns the horizontal change when mouse is moved
     */
    float getMouseHorizontalChange();

    /*
    Returns the vertical change when mouse is moved
     */
    float getMouseVerticalChange();

    /*
    Shuts down the UserInterface
     */
    void shutdown();

}
