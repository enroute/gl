/*******************************************************************************
 * Copyright (c) 2016-2020 TCL Communication Technology
 *
 * All rights reserved.
 *******************************************************************************/
package com.ztfun.gl;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Describe class GLRenderer here.
 *
 *
 * Created: Thu Aug 23 14:51:46 2018
 *
 * @author <a href="mailto:mario.li@tcl.com">Mario Li</a>
 * @version 1.0
 */
public class GLRenderer implements Renderer {

    // Implementation of android.opengl.GLSurfaceView$Renderer
    @Override
    public final void onSurfaceCreated(final GL10 glUnused, final EGLConfig config) {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public final void onSurfaceChanged(final GL10 glUnused, int width, final int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public final void onDrawFrame(final GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
