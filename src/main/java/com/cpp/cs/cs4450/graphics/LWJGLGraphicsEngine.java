package com.cpp.cs.cs4450.graphics;

import com.cpp.cs.cs4450.model.CubeChunk;
import com.cpp.cs.cs4450.model.cube.Cube;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class LWJGLGraphicsEngine implements GraphicsEngine {
    private static final String DEFAULT_TITLE = "LWJGL Computer Graphics Program";
    private static final Color INIT_COLOR = Color.BLACK;
    private static final int FPS = 60;


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

        Display.update();
        Display.sync(FPS);

        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void shutdown(){
        Display.destroy();
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
        GL11.glClearColor(INIT_COLOR.getRed(), INIT_COLOR.getGreen(), INIT_COLOR.getBlue(), INIT_COLOR.getAlpha());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(100.0f, ((float)displayMode.getWidth() / ( float) displayMode.getHeight()), 0.1f, 300.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glDepthFunc(GL11.GL_LESS);
        //glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    private void initText(){

        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);

    }

    private void initTextures(){
        try {
            for(Renderable render : renders){
                if(render instanceof Textured){
                    Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(((Textured) render).getPath()));
                    ((Textured) render).setTexture(texture, 0);
                }

                if(render instanceof CubeChunk) {
                    Cube[][][] cubes = ((CubeChunk) render).getCubes();
                    for (final Cube[][] matrix : cubes) {
                        for (final Cube[] array : matrix) {
                            for (Cube cube : array) {
                                if (cube instanceof Textured) {
                                    Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(((Textured) cube).getPath()));
                                    ((Textured) cube).setTexture(texture, 0);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
