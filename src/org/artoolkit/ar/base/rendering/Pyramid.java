/**
 * Pyramid.java
 *
 * Custom class for pyramid shaped surface
 *
 * @author Pranav Lakshminarayanan
 */
package org.artoolkit.ar.base.rendering;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES10;

/**
 * Simple class to render a colored pyramid.
 */
public class Pyramid extends Shape {

    // CONSTRUCTORS ============================================================
    /**
     * Default constructor for Pyramid.
     */
    public Pyramid() {
        this(40.0f);
    }

    /**
     * Square pyramid centered at origin with height specified.
     *
     * @param height height of pyramid
     */
    public Pyramid(float height) {
        this(height, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Square pyramid centered at (x,y,z) with height specified.
     *
     * @param height height of pyramid
     * @param x x-coordinate of center of base
     * @param y y-coordinate of center of base
     * @param z z-coordinate of center of base
     */
    public Pyramid(float height, float x, float y, float z) {
        this(height, x, y, z, height, height);
    }

    /**
     * Pyramid centered at (x,y,z) with height and side lengths specified.
     *
     * @param height height of pyramid
     * @param x x-coordinate of center of base
     * @param y y-coordinate of center of base
     * @param z z-coordinate of center of base
     * @param xw width of base along x-axis
     * @param yw width of base along y-axis
     */
    public Pyramid(float height, float x, float y, float z,
            float xw, float yw) {
        setArrays(height, x, y, z, xw, yw);
    }

    // METHODS =================================================================
    /**
     * Specify vertices, colors, and faces of pyramid.
     *
     * @param height height of pyramid
     * @param x x-coordinate of center of base
     * @param y y-coordinate of center of base
     * @param z z-coordinate of center of base
     * @param xw width of base along x-axis
     * @param yw width of base along y-axis
     */
    private void setArrays(float height, float x, float y, float z,
            float xw, float yw) {

        float hx = xw / 2.0f;
        float hy = yw / 2.0f;

        float[] ver = {
            x - hx, y - hy, z, // 0
            x + hx, y - hy, z, // 1
            x + hx, y + hy, z, // 2
            x - hx, y + hy, z, // 3
            x, y, z + height // 4, peak
        };
        this.vertices = ver;

        float c = 1.0f;
        float[] col = {
            0, 0, 0, c, // 0 black
            c, 0, 0, c, // 1 red
            c, c, 0, c, // 2 yellow
            0, c, 0, c, // 3 green
            0, 0, c, c // 4 blue
        };
        this.colors = col;

        short[] ind = {
            0, 1, 2, 0, 2, 3,
            0, 4, 1, 1, 4, 2,
            2, 4, 3, 3, 4, 0
        };
        this.indices = ind;

        this.mVertexBuffer = RenderUtils.buildFloatBuffer(this.vertices);
        this.mColorBuffer = RenderUtils.buildFloatBuffer(this.colors);
        this.mIndexBuffer = RenderUtils.buildShortBuffer(this.indices);

    }

    /**
     * Create surface from arrays.
     *
     * NOTES: Length of each color indicator: 4 Length of each vertex: 3 Number
     * of elements: 18
     *
     * @param unused unused
     */
    public void draw(GL10 unused) {

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, mColorBuffer);
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertexBuffer);

        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);

        GLES10.glDrawElements(
                GLES10.GL_TRIANGLES, 18,
                GLES10.GL_UNSIGNED_SHORT, mIndexBuffer);

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);

    }

}
