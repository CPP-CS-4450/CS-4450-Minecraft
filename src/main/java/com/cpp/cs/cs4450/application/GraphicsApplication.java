/***************************************************************
 * file: GraphicsApplication.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Class that is an implementation of an application for displaying graphics.
 *
 *
 *
 ****************************************************************/
package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.camera.FirstPersonCameraController;
import com.cpp.cs.cs4450.config.Configuration;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.graphics.LWJGLGraphicsEngine;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.chunk.Chunk;
import com.cpp.cs.cs4450.ui.LWJGLUserInterface;
import com.cpp.cs.cs4450.ui.UserInterface;
import com.cpp.cs.cs4450.util.ChunkFactory;
import com.cpp.cs.cs4450.util.ChunkFactory.ChunkOptions;
import com.cpp.cs.cs4450.util.CollisionDetector;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This class that is an implementation of an application for displaying graphics.
 * It is responsible for instantiating all the systems of the program as well as
 * configuring it.
 */
public abstract class GraphicsApplication {
    /**
     * Command line flag to disable lighting
     */
    private static final String DISABLE_LIGHTING_FLAG = "--dl";
    /**
     * The programs default start coordinates
     */
    private static final ReadableVector3f DEFAULT_START_COORDINATES = new Vector3f(-1.5f, -1.5f, -1.5f);

    /**
     * Launches the application
     *
     * @param args command line arguments
     */
    public static void launch(final String ...args){
        launch(Arrays.asList(args));
    }

    /**
     * Launches the application
     *
     * @param args List of arguments
     */
    public static void launch(final List<String> args){
        launch(args, new HashSet<>(args));
    }

    /**
     * Launches the application
     *
     * @param args List of arguments
     * @param cache Cache of hashed arguments
     */
    private static void launch(final List<String> args, final Set<String> cache){
        final GraphicsApplicationImpl application = new GraphicsApplicationImpl(args, cache);
        application.launch();
    }

    /**
     * Internal implementation of the GraphicsApplication class
     */
    private static final class GraphicsApplicationImpl extends GraphicsApplication {
        /**
         * Arguments list
         */
        private final List<String> args;
        /**
         * Arguments cache
         */
        private final Set<String> cache;

        /**
         * Constructor
         *
         * @param args List of arguments
         * @param cache Cache of hashed arguments
         */
        private GraphicsApplicationImpl(final List<String> args, final Set<String> cache) {
            this.args = Collections.unmodifiableList(args);
            this.cache = Collections.unmodifiableSet(cache);
        }

        /**
         * Launches the Application
         */
        private void launch(){
            final String title = Configuration.DISPLAY_WINDOW_TITLE;

            final int dw = Configuration.DISPLAY_WINDOW_WIDTH;
            final int dh = Configuration.DISPLAY_WINDOW_HEIGHT;
            final int ppi = Configuration.PIXELS_PER_INCH;

            final DisplayMode display = initOpenGLContext(dw, dh, ppi, title);

            final boolean lighting = !parseArgBooleanValue(DISABLE_LIGHTING_FLAG);
            final ChunkOptions options = ChunkOptions.builder()
                    .withSize(parseArgIntValue(ChunkOptions.SIZE_FLAG))
                    .withScale(parseArgFloatValue(ChunkOptions.SCALE_FLAG))
                    .withPersistence(parseArgDoubleValue(ChunkOptions.PERSISTENCE_FLAG))
                    .withOptimized(!parseArgBooleanValue(ChunkOptions.DISABLE_OPTIMIZED_FLAG))
                    .withBufferRendering(parseArgBooleanValue(ChunkOptions.ENABLE_BUFFER_RENDERING_FLAG, ChunkOptions.DISABLE_BUFFER_RENDERING_FLAG))
                    .withRandom(parseArgBooleanValue(ChunkOptions.RANDOM_FLAG))
                    .withPath(parseArgValue(ChunkOptions.TEXTURE_PATH_FLAG))
                    .build();

            final Chunk chunk = ChunkFactory.createChunk(options);
            final List<Renderable> renders = Collections.unmodifiableList(Collections.singletonList(chunk));
  
            final GraphicsEngine graphicsEngine = new LWJGLGraphicsEngine(display, renders, Configuration.DISPLAY_WINDOW_TITLE, lighting);
            final UserInterface ui = new LWJGLUserInterface();
            final CameraController camera = new FirstPersonCameraController(DEFAULT_START_COORDINATES);
            final CollisionDetector collisionDetector = new CollisionDetector(chunk.getBounds());

            final Engine engine = new Engine(ui, graphicsEngine, camera, collisionDetector);
            engine.run();
        }

        /**
         * Initializes the OpenGL context for graphics rendering
         *
         * @param dw Display width
         * @param dh Display height
         * @param ppi Display pixels per inch
         * @param title Display title
         * @return Programs Display
         */
        private DisplayMode initOpenGLContext(final int dw, final int dh, final int ppi, final String title){
            try {
                Log.setVerbose(false);

                DisplayMode display = new DisplayMode(dw, dh);
                for (final DisplayMode mode : Display.getAvailableDisplayModes()){
                    if(mode.getWidth() == dw && mode.getHeight() == dh && mode.getBitsPerPixel() == ppi){
                        display = mode;
                        break;
                    }
                }

                Display.setTitle(title);
                Display.setDisplayMode(display);
                Display.create();

                return display;
            } catch (LWJGLException e){
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }

        /**
         * Parses args for flag
         *
         * @param flag Argument to look for
         * @return Argument value
         */
        private String parseArgValue(final String flag){
            if(!cache.contains(flag)) return null;

            final int index = args.indexOf(flag);
            if(index < 0){
                return null;
            }

            try {
                return args.get(index + 1);
            } catch (ArrayIndexOutOfBoundsException e){
                return null;
            }
        }

        /**
         * Parses args for flag
         *
         * @param flag Argument to look for
         * @return Argument value
         */
        private int parseArgIntValue(final String flag){
            try {
                return Integer.parseInt(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        /**
         * Parses args for flag
         *
         * @param flag Argument to look for
         * @return Argument value
         */
        private double parseArgDoubleValue(final String flag){
            try {
                return Double.parseDouble(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        /**
         * Parses args for flag
         *
         * @param flag Argument to look for
         * @return Argument value
         */
        private float parseArgFloatValue(final String flag){
            try {
                return Float.parseFloat(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        /**
         * Checks if argument exists
         *
         * @param flag Argument to look for
         * @return True if argument exists, false otherwise
         */
        private boolean parseArgBooleanValue(final String flag){
            return cache.contains(flag);
        }

        /**
         * Checks for enabling/disabling argument flags
         *
         * @param enable Flag to enable
         * @param disable Flag to disable
         * @return True if enable, false otherwise
         */
        private boolean parseArgBooleanValue(final String enable, final String disable){
            return cache.contains(enable) && !cache.contains(disable);
        }
    }

}
