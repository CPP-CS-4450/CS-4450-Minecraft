package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.input.KeyboardMouseUserInput;
import com.cpp.cs.cs4450.input.UserInput;
import com.cpp.cs.cs4450.input.WirelessControllerUserInput;
import com.cpp.cs.cs4450.input.controller.BasicWirelessController;
import com.cpp.cs.cs4450.input.controller.WirelessController;
import com.cpp.cs.cs4450.input.controller.XboxWirelessController;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import java.util.Collection;
import java.util.Objects;

public final class UserInputFactory {
    public static final String CONTROLLER_FLAG = "--c";


    private UserInputFactory(){
        throw new UnsupportedOperationException();
    }



    public static UserInput getUserInput(Collection<String> args){
        WirelessController controller = null;
        if(args.contains(CONTROLLER_FLAG)){
            controller = getController();
        }

        if(Objects.nonNull(controller)){
            return new WirelessControllerUserInput(controller);
        }

        return new KeyboardMouseUserInput();
    }


    private static WirelessController getController(){
        try{
            Controllers.create();
            Controllers.poll();

            if(Controllers.getControllerCount() <= 0){
                return null;
            }

            final Controller controller = Controllers.getController(0);
            if(controller.getName().startsWith("Xbox")){
                return new XboxWirelessController(controller);
            }


            return new BasicWirelessController(controller);
        } catch (LWJGLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
