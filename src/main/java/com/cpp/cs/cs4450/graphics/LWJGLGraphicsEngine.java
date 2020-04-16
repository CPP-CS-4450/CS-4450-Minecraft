/***************************************************************
 * file: LWJGLGraphicsEngine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Responsible for the games graphics, using LWJGL and OpenGL to handle graphics
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

import com.cpp.cs.cs4450.util.Color;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;


/**
 * GraphicsEngine implementation that uses LWJGL 2.9.2 and OpenGL
 * to handle all the programs computer graphics
 */
public class LWJGLGraphicsEngine implements GraphicsEngine {
    /**
     * Default display title
     */
    private static final String DEFAULT_TITLE = "LWJGL Computer Graphics Program";
    /**
     * Frames per second
     */
    private static final int FPS = 60;
    /**
     * Color for black background
     */
    private static final Color BLACK_BACKGROUND = new Color(0.2f, 1, 0.2f, 0);
    /**
     * Color for blue sky background
     */
    private static final Color BACKGROUND_COLOR = new Color(0.8f, 0.95f, 1.0f, 0.0f);

    /**
     * Programs Display
     */
    private final DisplayMode displayMode;
    /**
     * Programs renderable objects
     */
    private final List<Renderable> renders;
    /**
     * Lighting position vector
     */
    private final Vector3f light;
    /**
     * Inverted flag
     */
    private boolean inverted;

    /**
     * Constructor
     *
     * @param displayMode Program's display
     * @param renders List of Renderable objects
     */
    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders) {
        this(displayMode, renders, DEFAULT_TITLE);
    }

    /**
     * Constructor
     *
     * @param displayMode Programs display
     * @param renders List of Renderable objects
     * @param title Display title
     */
    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders, final String title) {
        this(displayMode, renders, title, true);
    }

    /**
     * Constructor
     *
     * @param displayMode Programs display
     * @param renders List of Renderable objects
     * @param title Display title
     * @param lighting Graphics lighting flag
     */
    public LWJGLGraphicsEngine(final DisplayMode displayMode, final List<Renderable> renders, final String title, final boolean lighting) {
        this.displayMode = displayMode;
        this.renders = renders;
        this.light = new Vector3f(2f, 15f,2f);
        this.inverted = false;
        initGL11();
        if(lighting) initLighting();
    }

    /**
     * Renders objects in the program
     */
    @Override
    public void render(){
        renders.forEach(Renderable::render);
    }

    /**
     * Shuts down the programs graphics
     */
    @Override
    public void shutdown(){
        Display.destroy();
    }

    /**
     * Updates the programs lighting
     *
     * @param dx x-axis position
     * @param dy y-axis position
     * @param d z-axis position
     */
    @Override
    public void updateLighting(final float dx, final float dy, final float dz){
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, initFloatBuffer((light.x += dx), (light.y), (light.z += dz), 1.0f));
    }

    /**
     * Sets the programs view perspective to view the rendered graphics
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     * @param pitch perspective's pitch value
     * @param yaw perspective's yaw value
     */
    @Override
    public void setPerspective(final float x, final float y, final float z, final float pitch, final float yaw) {
        GL11.glLoadIdentity();
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(x, y, z);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, initFloatBuffer(light.x, light.y, light.z, 1.0f));
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Updates the programs display
     */
    @Override
    public void update() {
        Display.update();
        Display.sync(FPS);
    }

    /**
     * Inverts the program's invertible objects
     */
    @Override
    public void invert(){
        final Color background = inverted ? BACKGROUND_COLOR : BLACK_BACKGROUND;

        GL11.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), background.getAlpha());

        for(final Renderable render : renders){
            if(render instanceof Invertible){
                ((Invertible) render).invert();
            }
            if(render instanceof InvertibleContainer){
                ((InvertibleContainer) render).invert();
            }
        }

        inverted = !inverted;
    }

    /**
     * Initializes OpenGL to render graphics
     */
    private void initGL11(){
        GL11.glClearColor(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), BACKGROUND_COLOR.getAlpha());

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GLU.gluPerspective(100.0f, ((float)displayMode.getWidth() / ( float) displayMode.getHeight()), 0.1f, 300.0f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glDepthFunc(GL11.GL_LESS);
    }

    /**
     * Initializes the programs lighting
     */
    private void initLighting(){
        final FloatBuffer lightPositions = initFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f);
        final FloatBuffer wightLightPositions = initFloatBuffer(7.0f, 7.0f, 7.0f, 0.0f);
        final FloatBuffer darkLightPositions = initFloatBuffer(0.3f, 0.3f, 0.3f, 0.0f);

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, wightLightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, wightLightPositions);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, darkLightPositions);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
    }

    /**
     * Creates and initializes a float buffer from values
     *
     * @param f floating point values
     * @return FloatBuffer of values
     */
    private static FloatBuffer initFloatBuffer(final float ...f){
        return (FloatBuffer) ByteBuffer.allocateDirect(f.length << 2)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(f)
                .flip();
    }

}
