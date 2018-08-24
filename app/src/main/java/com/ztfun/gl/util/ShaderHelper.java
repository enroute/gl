/*******************************************************************************
 * Copyright (c) 2016-2020 TCL Communication Technology
 *
 * All rights reserved.
 *******************************************************************************/
package com.ztfun.gl.util;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

/**
 * Describe class ShaderHelper here.
 *
 *
 * Created: Thu Aug 23 15:39:03 2018
 *
 * @author <a href="mailto:mario.li@tcl.com">Mario Li</a>
 * @version 1.0
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            // if (LoggerConfig.ON) {
            //     Log.w(TAG, "Could not create new shader.");
            // }
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);

        // Compile and check shader
        glCompileShader(shaderObjectId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(shaderObjectId);
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId =  glCreateProgram();
        if (programObjectId ==  0) {
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        return validateStatus[0] !=  0;
    }
}
