package com.shockn745.wireinterview.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.shockn745.wireinterview.AnimatedMinion;
import com.shockn745.wireinterview.BitmapHelper;
import com.shockn745.wireinterview.CustomRotationAnimation;

/**
 * Base fragment to factor operations common to all fragments
 *
 * @author Florian Kempenich
 */
public abstract class BaseFragment extends Fragment {

    private static final int ANIM_DURATION = 6000;

    /**
     * Create and init CustomRotationAnimation
     * @param animatedMinion Must contains the ImageView. Will be loaded with the Animation
     */
    private void createCustomRotationAnimation(AnimatedMinion animatedMinion) {
        if (animatedMinion.getImageView() != null) {
            CustomRotationAnimation animation = new CustomRotationAnimation(
                    animatedMinion.getImageView(),
                    animatedMinion.startsHidden()
            );
            animation.setDuration(ANIM_DURATION);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);

            animatedMinion.setAnimation(animation);
        }
    }

    /**
     * Start animations if both AnimatedMinions have been initialized
     * Implement in subclass having an handle on the AnimatedMinions
     */
    protected abstract void startAnimationsIfReady();


    /**
     * Init the animatedMinion with the imageView, the animation and the bitmap drawable
     * Also start the animations
     * @param minion animatedMinion to be initialized
     * @param imageView imageView to add to the AnimatedMinion
     * @param startHidden true if starts animation hidden
     * @param resId resource id of the drawable
     */
    protected void initAnimatedMinion(
            final AnimatedMinion minion,
            ImageView imageView,
            boolean startHidden,
            final int resId){

        // Initialization - Fist part
        minion.setImageView(imageView);
        minion.setStartsHidden(startHidden);

        // Initialization - Second part : After layout
        minion.getImageView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so getTop/Bottom/etc != 0

                        // Remove listener after first layout
                        minion.getImageView()
                                .getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        // Load drawable
                        Bitmap bitmap = BitmapHelper.decodeSampledBitmapFromResource(
                                getResources(),
                                resId,
                                minion.getImageView().getWidth(),
                                minion.getImageView().getHeight()
                        );

                        minion.getImageView().setImageBitmap(bitmap);

                        // Create the animation
                        createCustomRotationAnimation(minion);

                        startAnimationsIfReady();
                    }
                });
    }
}
