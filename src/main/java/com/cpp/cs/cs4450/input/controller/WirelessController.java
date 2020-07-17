package com.cpp.cs.cs4450.input.controller;

public interface WirelessController {

    int getUpButton();

    int getDownButton();

    int getInvertButton();

    int getQuitButton();

    float getLeftStickXValue();

    float getLeftStickYValue();

    float getRightStickXValue();

    float getRightStickYValue();

    boolean isUpButtonPressed();

    boolean isDownButtonPressed();

    boolean isInvertButtonPressed();

    boolean isQuitButtonPressed();

}
