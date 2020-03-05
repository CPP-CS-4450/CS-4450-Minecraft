package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.camera.FirstPersonCameraController;
import com.cpp.cs.cs4450.config.Configuration;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.graphics.LWJGLGraphicsEngine;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.ui.LWJGLUserInterface;
import com.cpp.cs.cs4450.ui.UserInterface;
import com.cpp.cs.cs4450.util.CubeFactory;
import org.lwjgl.opengl.DisplayMode;

import java.util.List;

public abstract class GraphicsApplication {

    public static void launch(final String ...args){
        final Cube cube = CubeFactory.create(0,0,0,2);
        final List<Renderable> renders = List.of(cube);
        final DisplayMode displayMode = new DisplayMode(Configuration.DISPLAY_WINDOW_WIDTH, Configuration.DISPLAY_WINDOW_HEIGHT);

        final GraphicsEngine graphicsEngine = new LWJGLGraphicsEngine(displayMode, renders, Configuration.DISPLAY_WINDOW_TITLE);
        final UserInterface ui = new LWJGLUserInterface();
        final CameraController camera = new FirstPersonCameraController();

        final Engine engine = new Engine(ui, graphicsEngine, camera);
        engine.run();
    }

}
