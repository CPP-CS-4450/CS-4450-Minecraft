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

public abstract class GraphicsApplication {
    private static final String DISABLE_LIGHTING_FLAG = "--dl";
    private static final ReadableVector3f DEFAULT_START_COORDINATES = new Vector3f(-1.5f, -1.5f, -1.5f);


    public static void launch(final String ...args){
        launch(Arrays.asList(args));
    }

    public static void launch(final List<String> args){
        launch(args, new HashSet<>(args));
    }

    private static void launch(final List<String> args, final Set<String> cache){
        final GraphicsApplicationImpl application = new GraphicsApplicationImpl(args, cache);
        application.launch();
    }

    private static final class GraphicsApplicationImpl extends GraphicsApplication {
        private final List<String> args;
        private final Set<String> cache;

        private GraphicsApplicationImpl(final List<String> args, final Set<String> cache) {
            this.args = Collections.unmodifiableList(args);
            this.cache = Collections.unmodifiableSet(cache);
        }

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

        private int parseArgIntValue(final String flag){
            try {
                return Integer.parseInt(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        private double parseArgDoubleValue(final String flag){
            try {
                return Double.parseDouble(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        private float parseArgFloatValue(final String flag){
            try {
                return Float.parseFloat(Objects.requireNonNull(parseArgValue(flag)));
            } catch (NumberFormatException | NullPointerException e){
                return -1;
            }
        }

        private boolean parseArgBooleanValue(final String flag){
            return cache.contains(flag);
        }

        private boolean parseArgBooleanValue(final String enable, final String disable){
            return cache.contains(enable) && !cache.contains(disable);
        }
    }

}
