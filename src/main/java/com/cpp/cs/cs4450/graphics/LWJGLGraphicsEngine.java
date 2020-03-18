package com.cpp.cs.cs4450.graphics;

import com.cpp.cs.cs4450.util.Color;
import com.cpp.cs.cs4450.util.TextureLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


import java.io.IOException;
import java.util.List;


public class LWJGLGraphicsEngine implements GraphicsEngine {
    private static final String DEFAULT_TITLE = "LWJGL Computer Graphics Program";
    private static final int FPS = 60;
    private static final Color BACKGROUND_COLOR = new Color(0,0,0,0);


    private final DisplayMode displayMode;
    private final List<Renderable> renders;


    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders) {
        this(displayMode, renders, DEFAULT_TITLE);
    }

    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders, final String title) {
        this.displayMode = displayMode;
        this.renders = renders;
        initDisplay(title);
        initGL11();
        initTextures();
    }

    public void render(){
        renders.forEach(Renderable::render);
    }

    public void shutdown(){
        Display.destroy();
    }

    @Override
    public void setPerspective(final float x, final float y, final float z, final float pitch, final float yaw) {
        GL11.glLoadIdentity();
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(x, y, z);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void update() {
        Display.update();
        Display.sync(FPS);
    }

    private void initDisplay(final String title){
        try {
            Display.setTitle(title);
            Display.setDisplayMode(displayMode);
            Display.create();
        } catch (LWJGLException e) {
            throw new GraphicsException(e.getLocalizedMessage());
        }
    }

    private void initGL11(){
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(),BACKGROUND_COLOR.getAlpha());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(100.0f, ((float)displayMode.getWidth() / ( float) displayMode.getHeight()), 0.1f, 300.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glDepthFunc(GL11.GL_LESS);
    }

    private void initTextures(){
        try {
            TextureLoader.load(TexturedObjectConverter.convert(renders));
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
