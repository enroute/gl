/*******************************************************************************
 * Copyright (c) 2016-2020 TCL Communication Technology
 *
 * All rights reserved.
 *******************************************************************************/
package com.ztfun.gl;

import com.ztfun.gl.util.ShaderHelper;

import android.content.Context;
import com.ztfun.gl.util.TextResourceReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

/**
 * Describe class AirHockeyRenderer here.
 *
 *
 * Created: Thu Aug 23 15:11:49 2018
 *
 * @author <a href="mailto:mario.li@tcl.com">Mario Li</a>
 * @version 1.0
 */
public class AirHockeyRenderer extends GLRenderer {
    private static final int POSITION_COMPONENT_COUNT = 2; // X/Y
    private static final int BYTES_PER_FLOAT = 4;

    // float[] tableVerticesWithTriangles = {
    //     // Triangle 1
    //     -0.5f, -0.5f,
    //      0.5f,  0.5f,
    //     -0.5f,  0.5f,

    //     // Triangle 2
    //     -0.5f, -0.5f,
    //      0.5f, -0.5f,
    //      0.5f,  0.5f,

    //     // Line 1
    //     -0.5f,  0f,
    //      0.5f,  0f,

    //     // Mallets
    //     0f, -0.25f,
    //     0f,  0.25f
    // };

    float[] tableVerticesWithTriangles = {
        // Order of coordinates: X, Y, R, G, B
        // Triangle Fan
         0.0f,  0.0f, 1.0f, 1.0f, 1.0f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
         0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
         0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

        // Line 1
        -0.5f,  0f, 1f, 0f, 0f,
         0.5f,  0f, 1f, 0f, 0f,

        // Mallets
        0f, -0.25f, 0f, 0f, 1f,
        0f,  0.25f, 1f, 0f, 0f
    };

    private final FloatBuffer vertexData;
    private final Context context;
    private int program;

    // private static final String U_COLOR = "u_Color";
    // private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3; // R/G/B
    private static final int STRIDE =
        (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private int aColorLocation;
    
    public AirHockeyRenderer(Context context) {
        this.context = context;
        vertexData = ByteBuffer
            .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public final void onSurfaceCreated(final GL10 glUnused, final EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        /* 
        // validate program only in debug/developing mode
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }
        */

        glUseProgram(program);
        // uColorLocation = glGetUniformLocation(program, U_COLOR);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onDrawFrame(final GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);

        // Draw triangles
        // glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw line
        // glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        //  Draw the first mallet blue.
        // glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        //  Draw the second mallet red.
        // glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }


}
