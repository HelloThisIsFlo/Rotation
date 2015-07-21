package com.shockn745.wireinterview;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Class to allow rotation along the relative Y axis of the animated View
 * As opposed to the absolute Y axis used in {@link View#setRotationY(float)}
 *
 * @author Florian Kempenich
 */
public class CustomRotationAnimation extends Animation {

    private float centerX;
    private float centerY;

    private Camera camera;
    private View mView;

    private boolean mStartHidden;

    public CustomRotationAnimation(View view, boolean startHidden) {
        mView = view;
        mStartHidden = startHidden;

        // Get the center of the view
        centerX = Math.abs((view.getRight() - view.getLeft()) / 2);
        centerY = Math.abs((view.getTop() - view.getBottom()) / 2);

        camera = new Camera();

        // Move camera away to give a more natural flip movement
        // And to respect the demo video
        // Hardcoded value, could have put in setting resource file, but no point here.
        camera.setLocation(0, 0, 50);
    }

    @Override
    protected void applyTransformation(float interpolatedTime,
                                       Transformation t) {

        // Get the matrix that the transformation is using
        final Matrix matrix = t.getMatrix();

        // Get the matrix for a 3D rotation along the Y axis
        camera.save();
        camera.rotateY(360 * interpolatedTime);
        camera.getMatrix(matrix);
        camera.restore();

        float originalRotation = mView.getRotation();

        /*
         * Matrix multiplication to allow rotation along a custom axis / center
         *
         * Simple explanation :
         * - View rotated to 0 degree          |___ "Set Up"
         * - View moved to origin (0,0)        |
         * - View is rotated along the Y axis
         * - View rotated to original angle    |___ "Clean Up"
         * - View moved to original position   |
         *
         * Actually all these animations are not performed, instead matrices are multiplied
         * to obtain the desired effect in one step.
         *
         * Here the term "concat" actually refers to matrix multiplication and not actual
         * concatenation.
         *
         * cf. Euler angles & rotation matrices
         */
        matrix.preRotate(-originalRotation);
        matrix.postRotate(originalRotation);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);

        // Hide/Display view for half the time of the animation
        if (interpolatedTime > 0.25 && interpolatedTime < 0.75) {
            if (mStartHidden) {
                t.setAlpha(1);
            } else {
                t.setAlpha(0);
            }
        } else {
            if (mStartHidden) {
                t.setAlpha(0);
            } else {
                t.setAlpha(1);
            }
        }

        super.applyTransformation(interpolatedTime, t);

        // Added because the view would not update properly otherwise
        // TODO : Better solution ?
        mView.invalidate();
    }





}
