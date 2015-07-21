package com.shockn745.wireinterview;

import android.widget.ImageView;

/**
 * Simple class to hold an ImageView and its related animation
 * Add some functions to make code more readable
 */
public class AnimatedMinion {

    private ImageView imageView;
    private CustomRotationAnimation animation;
    private boolean startHidden;

    public AnimatedMinion() {
        imageView = null;
        animation = null;
        startHidden = false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public CustomRotationAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(CustomRotationAnimation animation) {
        this.animation = animation;
    }

    public boolean startsHidden() {
        return startHidden;
    }

    public void setStartsHidden(boolean startHidden) {
        this.startHidden = startHidden;
    }

    /**
     * Starts animation if both animation and imageview have been set
     */
    public void startAnimation() {
        if (isInitialized()) {
            imageView.startAnimation(animation);
        }
    }

    /**
     * Return the state of the bean
     * @return True if initialized
     */
    public boolean isInitialized() {
        return animation != null && imageView != null;
    }


}
