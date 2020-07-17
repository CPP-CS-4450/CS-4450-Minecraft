package com.cpp.cs.cs4450.input.controller;

import org.lwjgl.input.Controller;

public class XboxWirelessController extends BasicWirelessController implements WirelessController {

    public XboxWirelessController(final Controller controller) {
        super(controller);
    }


    @Override
    public int getUpButton() {
        return 0;
    }

    @Override
    public int getDownButton() {
        return 1;
    }

    @Override
    public int getInvertButton() {
        return 3;
    }

    @Override
    public int getQuitButton() {
        return 11;
    }

}
