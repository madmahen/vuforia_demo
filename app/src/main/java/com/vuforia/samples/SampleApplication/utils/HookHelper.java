package com.vuforia.samples.SampleApplication.utils;

import android.hardware.Camera;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by weibo on 16-3-30.
 */
public class HookHelper {

  public static void hookCameraPreview() throws Exception {

    Class<?> cameraPreview = Class.forName("com.qualcomm.ar.pl.CameraPreview");
    Object cameraPreviewObj = cameraPreview.newInstance();

    Class<?> cameraCacheInfoClazz = Class.forName("com.qualcomm.ar.pl.CameraPreview$CameraCacheInfo");
    Constructor<?> ctor = cameraCacheInfoClazz.getDeclaredConstructor(cameraPreview);
    Object cameraCameraInfoObj = ctor.newInstance(cameraPreviewObj);

    Field cameraField = cameraCameraInfoObj.getClass().getDeclaredField("camera");
    cameraField.setAccessible(true);
    Camera camera = (Camera) cameraField.get(cameraCameraInfoObj);
    //Camera camera = hookOpenCamera();

    Field previewCallback = Class.forName("android.hardware.Camera").getDeclaredField("mPreviewCallback");
    previewCallback.setAccessible(true);
    CustomCameraPreview myCameraPreview = new CustomCameraPreview();
    previewCallback.set(camera, myCameraPreview);


  }

}