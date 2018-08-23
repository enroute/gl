package com.ztfun.gl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

/*******************************************************************************
 * Copyright (c) 2016-2020 TCL Communication Technology
 *
 * All rights reserved.
 *******************************************************************************/
/**
 * Describe class GLActivity here.
 *
 *
 * Created: Thu Aug 23 14:34:53 2018
 *
 * @author <a href="mailto:mario.li@tcl.com">Mario Li</a>
 * @version 1.0
 */
public class GLActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGl();
        if (rendererSet) {
            setContentView(glSurfaceView);
        } else {
            // fail
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initGl() {
        glSurfaceView = new GLSurfaceView(this);

        final ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        final boolean supportsEs2 = ci.reqGlEsVersion >= 0x20000
            // what following is for emulator
            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")));

        if (supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new GLRenderer());
            rendererSet = true;
        } else {
            // the device does not support OpenGL ES 2.0
        }
    }

    @Override
    public final void onPause() {
        super.onPause();

        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    public final void onResume() {
        super.onResume();

        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

}
