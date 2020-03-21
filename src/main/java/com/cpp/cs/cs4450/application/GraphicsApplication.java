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
import com.cpp.cs.cs4450.util.CollisionDetector;

import org.lwjgl.opengl.DisplayMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class GraphicsApplication {
    private static final int SIZE = 30;
    private static final double PERSISTENCE = .25;
    private static final float CUBE_SIZE = 0.1f;
    private static final boolean DEFAULT_LIGHTING = true;
    private static final boolean DEFAULT_TEXTURE_MODE = false;
    private static final String FLAG_PREFIX = "--";
    private static final String YES_FLAG_OPTION = "Y";
    private static final String NO_FLAG_OPTION = "N";
    private static final String ENABLE_LIGHTING_FLAG = "--l";
    private static final String DISABLE_LIGHTING_FLAG = "--dl";
    private static final String ENABLE_RANDOM_FLAG = "--r";
    private static final String INVALID_ARG_ERROR_MESSAGE_FORMAT = "[%s] is an invalid option for arg flag [%s]";


    public static void launch(final String ...args){
        launch(Collections.unmodifiableList(Arrays.asList(args)));
    }

    public static void launch(final List<String> args){
        final boolean lighting = !parseArgs(args, DISABLE_LIGHTING_FLAG);
        final boolean random = parseArgs(args, ENABLE_RANDOM_FLAG);

        launch(lighting, random);
    }

    private static void launch(boolean lighting, boolean random){
        final Chunk chunk = ChunkFactory.createChunk(SIZE, CUBE_SIZE, PERSISTENCE, random);
        final List<Renderable> renders = Collections.unmodifiableList(Collections.singletonList(chunk));
        final DisplayMode displayMode = new DisplayMode(Configuration.DISPLAY_WINDOW_WIDTH, Configuration.DISPLAY_WINDOW_HEIGHT);

        final GraphicsEngine graphicsEngine = new LWJGLGraphicsEngine(displayMode, renders, Configuration.DISPLAY_WINDOW_TITLE, lighting);
        final UserInterface ui = new LWJGLUserInterface();
        final CameraController camera = new FirstPersonCameraController(-1.5f, -1.5f, -1.5f);
        final CollisionDetector collisionDetector = new CollisionDetector(chunk.getBounds());

        final Engine engine = new Engine(ui, graphicsEngine, camera, collisionDetector);
        engine.run();
    }

    private static boolean parseArgs(List<String> args, String flag){
        final int index = args.indexOf(flag);
        if(index == -1){
            return false;
        }

        try {
            final String config = args.get(index + 1);
            if(!config.startsWith(FLAG_PREFIX)){
                if(config.equalsIgnoreCase(YES_FLAG_OPTION)){
                    return true;
                } else if (config.equalsIgnoreCase(NO_FLAG_OPTION)){
                    return false;
                } else {
                    throw new RuntimeException(String.format(INVALID_ARG_ERROR_MESSAGE_FORMAT, config, flag));
                }
            } else {
                return true;
            }
        } catch (IndexOutOfBoundsException e){
            return true;
        }
    }

}
