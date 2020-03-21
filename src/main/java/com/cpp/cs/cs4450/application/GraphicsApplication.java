package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.camera.FirstPersonCameraController;
import com.cpp.cs.cs4450.config.Configuration;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.graphics.LWJGLGraphicsEngine;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.Chunk;
import com.cpp.cs.cs4450.ui.LWJGLUserInterface;
import com.cpp.cs.cs4450.ui.UserInterface;
import com.cpp.cs.cs4450.util.ChunkFactory;

import org.lwjgl.opengl.DisplayMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class GraphicsApplication {
    private static final int SIZE = 30;
    private static final double PERSISTENCE = .25;
    private static final float CUBE_SIZE = 0.1f;

    public static void launch(final String ...args){
        launch(Collections.unmodifiableList(Arrays.asList(args)));
    }

    public static void launch(final List<String> args){
        final Chunk chunk = ChunkFactory.createChunk(SIZE, CUBE_SIZE, PERSISTENCE);
        final List<Renderable> renders = Collections.unmodifiableList(Collections.singletonList(chunk));
        final DisplayMode displayMode = new DisplayMode(Configuration.DISPLAY_WINDOW_WIDTH, Configuration.DISPLAY_WINDOW_HEIGHT);

        final GraphicsEngine graphicsEngine = new LWJGLGraphicsEngine(displayMode, renders, Configuration.DISPLAY_WINDOW_TITLE);
        final UserInterface ui = new LWJGLUserInterface();
        final CameraController camera = new FirstPersonCameraController(-1.5f, -1.5f, -1.5f);

        final Engine engine = new Engine(ui, graphicsEngine, camera);
        engine.run();
    }

}
