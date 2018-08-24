/*******************************************************************************
 * Copyright (c) 2016-2020 TCL Communication Technology
 *
 * All rights reserved.
 *******************************************************************************/
package com.ztfun.gl.util;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Describe class TextResourceReader here.
 *
 *
 * Created: Thu Aug 23 15:26:16 2018
 *
 * @author <a href="mailto:mario.li@tcl.com">Mario Li</a>
 * @version 1.0
 */
public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) !=  null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return body.toString();
    }
}
