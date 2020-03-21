package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import com.cpp.cs.cs4450.util.VertexUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.ReadableVector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MultiTexturedBlock extends TexturedBlock implements Renderable, Textured, Bounded, Invertible {
    private static final String INVALID_VERTICES_ERROR_MESSAGE = "Invalid number of vertices";


    public MultiTexturedBlock(
            final float x,
            final float y,
            final float z,
            final BlockType type,
            final Bound bounds,
            final List<CubeSide> sides,
            final Map<CubeSideType, String> paths
    ) {
        super(x, y, z, type, bounds, sides, paths);
    }

    @Override
    public void render(){
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);

        for(final CubeSide side : sides){
            if(side.getVertices().size() != TEX_COORDS.size()){
                throw new RuntimeException(INVALID_VERTICES_ERROR_MESSAGE);
            }

            final Texture texture = inverted ? inverts.get(side.getType()) : textures.get(side.getType());
            texture.bind();
            GL11.glBegin(GL11.GL_QUADS);

            final float offset = texture.getWidth() / texture.getHeight();

            final Queue<ReadableVector3f> queue = VertexUtils.renderQueue(side.getType(), side.getVertices());

            while(!queue.isEmpty()) {
                for (final ReadableVector2f tex : TEX_COORDS) {
                    final ReadableVector3f vertex = queue.poll();
                    if (vertex != null) {
                        GL11.glTexCoord2f(tex.getX() * offset, tex.getY() * offset);
                        GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
                    }
                }
            }
            GL11.glEnd();
        }
    }

}
