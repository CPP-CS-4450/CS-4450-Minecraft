/***************************************************************
 * file: UserInterfaceException.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Custom exception
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

public class UserInterfaceException extends RuntimeException {

    public UserInterfaceException(final String e){
        super(e);
    }

    public UserInterfaceException(final Exception e){
        super(e);
    }

}
