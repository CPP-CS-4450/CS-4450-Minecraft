/***************************************************************
 * file: Color.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Class responsible for representing a color in the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

public class Color {
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;


    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /*
    Getter for colors red atribute
     */
    public float getRed() {
        return red;
    }

    /*
    Getter for colors green atribute
     */
    public float getGreen() {
        return green;
    }

    /*
    Getter for colors blue atribute
     */
    public float getBlue() {
        return blue;
    }

    /*
    Getter for colors alpha atribute
     */
    public float getAlpha() {
        return alpha;
    }

}
