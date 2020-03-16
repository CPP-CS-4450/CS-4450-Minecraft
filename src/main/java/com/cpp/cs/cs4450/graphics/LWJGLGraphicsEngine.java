package com.cpp.cs.cs4450.graphics;

import com.cpp.cs.cs4450.util.TextureLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import java.awt.Color;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.List;

public class LWJGLGraphicsEngine implements GraphicsEngine {
    private static final String DEFAULT_TITLE = "LWJGL Computer Graphics Program";
    private static final Color INIT_COLOR = Color.BLACK;
    private static final Color SKY_COLOR = new Color(0.8f, 0.95f, 1.0f, 0.0f);
    private static final int FPS = 60;
    private static final List<Float> LIGHT_POSITIONS = List.of(0.0f, 0.0f, 0.0f, 1.0f);
    private static final List<Float> WHITE_LIGHT_POSITIONS = List.of(7.0f, 7.0f, 7.0f, 0.0f);
    private static final List<Float> DARK_LIGHT_POSITIONS = List.of(0.3f, 0.3f, 0.3f, 0.0f);

    private final DisplayMode displayMode;
    private final List<Renderable> renders;
    private final Vector3f light;


    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders) {
        this(displayMode, renders, DEFAULT_TITLE);
    }

    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders, final String title) {
        this(displayMode, renders, title, true);
    }

    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders, final String title, final boolean lighting) {
        this.displayMode = displayMode;
        this.renders = renders;
        this.light = new Vector3f(0f, 15f,3.5f);
        initDisplay(title);
        initGL11();
        initTextures();
        if(lighting) initLighting();
    }

    public void render(){
        renders.forEach(Renderable::render);
    }

    public void shutdown(){
        Display.destroy();
    }

    @Override
    public void updateLighting(final float dx, final float dy, final float dz){
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, initBuffer(List.of((light.x += dx), (light.y), (light.z += dz), 1.0f)));
    }

    @Override
    public void setPerspective(final float x, final float y, final float z, final float pitch, final float yaw) {
        GL11.glLoadIdentity();
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(x, y, z);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, initBuffer(List.of(light.x, light.y, light.z, 1.0f)));
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
        GL11.glClearColor(0.8f, 0.95f, 1.0f, 0.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(100.0f, ((float)displayMode.getWidth() / ( float) displayMode.getHeight()), 0.1f, 300.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glDepthFunc(GL11.GL_LESS);
    }

    private void initLighting(){
        final FloatBuffer lightPositions = initBuffer(LIGHT_POSITIONS);
        final FloatBuffer wightLightPositions = initBuffer(WHITE_LIGHT_POSITIONS);
        final FloatBuffer darkLightPositions = initBuffer(DARK_LIGHT_POSITIONS);

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, wightLightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, wightLightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, darkLightPositions);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
    }

    private static FloatBuffer initBuffer(final Collection<? extends Number> values){
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(values.size());
        for(final Number value : values) {
            buffer.put(value.floatValue());
        }

        return buffer.flip();
    }


    private void initTextures(){
        try {
            TextureLoader.load(TexturedObjectConverter.convert(renders));
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
