/***************************************************************
 * file: GraphicsException.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Custome exception
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

public class GraphicsException extends RuntimeException {

    public GraphicsException(final String e){
        super(e);
    }

    public GraphicsException(final Exception e){
        super(e);
    }

}
