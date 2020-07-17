package com.cpp.cs.cs4450.input;

import com.cpp.cs.cs4450.input.controller.WirelessController;

public class WirelessControllerUserInput implements UserInput {

    private final WirelessController controller;
    private float x;
    private float y;
    private float dx;
    private float dy;


    public WirelessControllerUserInput(final WirelessController controller) {
        this.controller = controller;
    }


    @Override
    public boolean up() {
        return controller.isUpButtonPressed();
    }

    @Override
    public boolean down() {
        return controller.isDownButtonPressed();
    }

    @Override
    public boolean left() {
        return controller.getLeftStickXValue() < -0.5;
    }

    @Override
    public boolean right() {
        return controller.getLeftStickXValue() > 0.5;
    }

    @Override
    public boolean forward() {
        return controller.getLeftStickYValue() < -0.5;
    }

    @Override
    public boolean backward() {
        return controller.getLeftStickYValue() > 0.5;
    }

    @Override
    public float horizontalDelta() {
        float x0 = x;
        float x1 = controller.getRightStickXValue();

        dx = x1-x0;

        float result = dx;
        dx = 0;

        return result * 25;
    }

    @Override
    public float verticalDelta() {
        float y0 = y;
        float y1 = controller.getRightStickYValue();

        dy = y1-y0;

        float result = dy;
        dy = 0;

        return -(result * 25);
    }

    @Override
    public boolean quit() {
        return controller.isQuitButtonPressed();
    }

    @Override
    public boolean invert() {
        return controller.isInvertButtonPressed();
    }


}
