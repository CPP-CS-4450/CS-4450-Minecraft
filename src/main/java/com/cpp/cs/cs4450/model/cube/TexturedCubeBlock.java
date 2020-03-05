package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import com.cpp.cs.cs4450.util.VertexUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.newdawn.slick.opengl.Texture;


import java.util.List;

public class TexturedCubeBlock extends CubeBlock implements Renderable, Textured {
    private final String path;
    private Texture texture;


    public TexturedCubeBlock(float x, float y, float z, List<CubeSide> sides, String path) {
        super(x, y, z, sides);
        this.path = path;
    }

    @Override
    public void render(){
        texture.bind();
        //GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        //GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glColor3f(1f, 1f, 1f);


        GL11.glBegin(GL11.GL_QUADS);
        for(CubeSide side : sides){
            System.out.println(side.getType().toString());
            System.out.println(VertexUtils.getSorted(side.getVertices()).toString() + "\n\n");

            CubeSideType type = side.getType();

            List<ReadableVector3f> vertices = VertexUtils.getSorted(side.getVertices());

            float w = texture.getWidth();
            float h = texture.getHeight();

            if (type == CubeSideType.TOP || type == CubeSideType.BOTTOM) {
                vertices.forEach(v -> {
                    GL11.glTexCoord2f(v.getX() / w, v.getZ() / h);
                    GL11.glVertex3f(v.getX(), v.getY(), v.getZ());
                });
            } else if (type == CubeSideType.LEFT || type == CubeSideType.RIGHT) {
                vertices.forEach(v -> {
                    GL11.glTexCoord2f(v.getY() / w, v.getZ() / h);
                    GL11.glVertex3f(v.getX(), v.getY(), v.getZ());
                });
            } else {
                vertices.forEach(v -> {
                    GL11.glTexCoord2f(v.getX() / w, v.getY() / h);
                    GL11.glVertex3f(v.getX(), v.getY(), v.getZ());
                });
            }



        }
        GL11.glEnd();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture, int size) {
        this.texture = texture;
    }

    @Override
    public String getPath() {
        return path;
    }
}
