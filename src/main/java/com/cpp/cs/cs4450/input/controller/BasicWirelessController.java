package com.cpp.cs.cs4450.input.controller;

import org.lwjgl.input.Controller;

public class BasicWirelessController implements WirelessController {
    private final Controller controller;


    public BasicWirelessController(final Controller controller) {
        this.controller = controller;
    }


    @Override
    public int getUpButton() {
        return 1;
    }

    @Override
    public int getDownButton() {
        return 2;
    }

    @Override
    public int getInvertButton() {
        return 0;
    }

    @Override
    public int getQuitButton() {
        return 9;
    }

    @Override
    public float getLeftStickXValue() {
        controller.poll();
        return controller.getAxisValue(0);
    }

    @Override
    public float getLeftStickYValue() {
        controller.poll();
        return controller.getAxisValue(1);
    }

    @Override
    public float getRightStickXValue() {
        controller.poll();
        return controller.getAxisValue(2);
    }

    @Override
    public float getRightStickYValue() {
        controller.poll();
        return controller.getAxisValue(3);
    }

    @Override
    public boolean isUpButtonPressed() {
        controller.poll();
        return controller.isButtonPressed(getUpButton());
    }

    @Override
    public boolean isDownButtonPressed() {
        controller.poll();
        return controller.isButtonPressed(getDownButton());
    }

    @Override
    public boolean isInvertButtonPressed() {
        controller.poll();
        return controller.isButtonPressed(getInvertButton());
    }

    @Override
    public boolean isQuitButtonPressed() {
        controller.poll();
        return controller.isButtonPressed(getQuitButton());
    }

}
