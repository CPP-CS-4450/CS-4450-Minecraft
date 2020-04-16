/***************************************************************
 * file: Color.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Class to represent color
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

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public boolean equals(final Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != getClass()){
            return false;
        }

        final Color other = (Color) obj;

        return this.red == other.red
                && this.green == other.green
                && this.blue == other.blue
                && this.alpha == other.alpha;
    }

    @Override
    public int hashCode(){
        final int prime = 31;

        int hash = 1;
        hash += prime * red;
        hash += prime * green;
        hash += prime * blue;
        hash += prime * alpha;

        return hash;
    }

}
