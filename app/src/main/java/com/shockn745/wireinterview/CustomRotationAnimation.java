package com.shockn745.wireinterview;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Shock on 21.07.15.
 */
public class CustomRotationAnimation extends Animation {

    private float centerX;
    private float centerY;

    private Camera camera;
    private View mView;

    public CustomRotationAnimation(View view) {
        mView = view;
        centerX = Math.abs((view.getRight() - view.getLeft()) / 2);
        centerY = Math.abs((view.getTop() - view.getBottom()) / 2);

        camera = new Camera();
        camera.setLocation(0,0,5000);
    }

    @Override
    protected void applyTransformation(float interpolatedTime,
                                       Transformation t) {

        final Matrix matrix = t.getMatrix();



        camera.save();
        camera.rotateY(360 * interpolatedTime);
        camera.getMatrix(matrix);
        camera.restore();




        float rotation = mView.getRotation();
        matrix.preRotate(-rotation);
        matrix.postRotate(rotation);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);



        super.applyTransformation(interpolatedTime, t);
    }


    /** Create the rotation matrix around the Z axis
     *
     * @param angle Angle in degree
     * @return Rotation Matrix
     */
    private Matrix createRotationZMatrix(float angle) {
        //TODO optimize : DO NOT create new matrix each time
        Matrix rotationZ = new Matrix();

        double angleRad = Math.toRadians(angle);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        rotationZ.setValues(new float[]{cos, -sin, 0, sin, cos, 0, 0, 0, 1});

        return rotationZ;
    }

    /** Create the rotation matrix around the Y axis
     *
     * @param angle Angle in degree
     * @return Rotation Matrix
     */
    private Matrix createRotationYMatrix(float angle) {
        //TODO optimize : DO NOT create new matrix each time
        Matrix rotationY = new Matrix();

        double angleRad = Math.toRadians(angle);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        rotationY.setValues(new float[]{1, 0, 0, 0, cos, sin, 0, -sin, cos});

        return rotationY;
    }
}
