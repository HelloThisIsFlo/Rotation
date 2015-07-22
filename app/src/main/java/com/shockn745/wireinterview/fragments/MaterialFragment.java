package com.shockn745.wireinterview.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.shockn745.wireinterview.AnimatedMinion;
import com.shockn745.wireinterview.R;

/**
 * Fragment for the material demo
 *
 * @author Florian Kempenich
 */
public class MaterialFragment extends BaseFragment {

    private static final String LOG_TAG = MaterialFragment.class.getSimpleName();

    private static final int ANIMATION_DURATION = 350;

    private static final int ROTATION_SPAN = 270;

    private AnimatedMinion mMinion1;
    private AnimatedMinion mMinion2;
    private SeekBar mRotationSeekBar;
    private SeekBar mSaturationSeekBar;

    private FloatingActionButton mMainFAB;
    private FloatingActionButton mRotationFAB;
    private FloatingActionButton mSaturationFAB;

    private CardView mRotationCardView;
    private CardView mSaturationCardView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_material, container, false);

        // Find views by id
        ImageView mMinion1ImageView = (ImageView) rootView.findViewById(R.id.minion_1_image_view);
        ImageView mMinion2ImageView = (ImageView) rootView.findViewById(R.id.minion_2_image_view);
        mRotationSeekBar = (SeekBar) rootView.findViewById(R.id.rotation_seek_bar);
        mSaturationSeekBar = (SeekBar) rootView.findViewById(R.id.saturation_seek_bar);
        mMainFAB = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mSaturationFAB = (FloatingActionButton) rootView.findViewById(R.id.fab_saturation);
        mRotationFAB = (FloatingActionButton) rootView.findViewById(R.id.fab_rotation);
        mRotationCardView = (CardView) rootView.findViewById(R.id.card_rotation);
        mSaturationCardView = (CardView) rootView.findViewById(R.id.card_saturation);

        // Set OnClickListeners
        mMainFAB.setOnClickListener(new MainFABOnClickListener());
        mRotationFAB.setOnClickListener(new RotationFABOnClickListener());
        mSaturationFAB.setOnClickListener(new SaturationFABOnClickListener());

        // Init AnimatedMinions
        mMinion1 = new AnimatedMinion();
        mMinion2 = new AnimatedMinion();
        initAnimatedMinion(mMinion1, mMinion1ImageView, false, R.drawable.full_res_minion_1);
        initAnimatedMinion(mMinion2, mMinion2ImageView, true, R.drawable.full_res_minion_2);

        // Set up the rotationSeekBar
        // Max value (in degree * 100)
        // Degree * 100 : For smoother scrolling
        mRotationSeekBar.setMax(ROTATION_SPAN*100);
        mRotationSeekBar.setProgress(ROTATION_SPAN*100/2);
        mRotationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Set rotation (in degree)
                float rotation = -(ROTATION_SPAN/2) + ((float) progress) / 100;
                mMinion1.getImageView().setRotation(rotation);
                mMinion2.getImageView().setRotation(rotation);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // Set up the saturation Seekbar
        final ColorMatrix colorMatrix = new ColorMatrix();
        final int maxProgress = 1000;
        mSaturationSeekBar.setMax(maxProgress);
        mSaturationSeekBar.setProgress(maxProgress);
        mSaturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorMatrix.setSaturation(((float) progress) / 1000);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

                mMinion1.getImageView().setColorFilter(filter);
                mMinion2.getImageView().setColorFilter(filter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return rootView;
    }

    @Override
    protected void startAnimationsIfReady() {
        if (mMinion1.isInitialized() && mMinion2.isInitialized()) {
            mMinion1.startAnimation();
            mMinion2.startAnimation();
        }
    }


    //////////////////////
    // OnClickListeners //
    //////////////////////

    private class MainFABOnClickListener implements View.OnClickListener {

        boolean open = false;

        @Override
        public void onClick(View v) {

            AnimatorSet animatorSet = new AnimatorSet();

            if (!open) {
                open = true;

                /// Open the FAB "menu"
                // Set visibility
                mRotationFAB.setVisibility(View.VISIBLE);
                mSaturationFAB.setVisibility(View.VISIBLE);

                // Create the interpolator
                Interpolator interpolator = AnimationUtils.loadInterpolator(
                        getActivity(),
                        android.R.interpolator.fast_out_slow_in
                );

                // Create the Object animators
                ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(
                        mRotationFAB,
                        "translationY",
                        getActivity().getResources().getDimension(R.dimen.rotation_translation),
                        0
                );
                ObjectAnimator saturationAnimator = ObjectAnimator.ofFloat(
                        mSaturationFAB,
                        "translationY",
                        getActivity().getResources().getDimension(R.dimen.saturation_translation),
                        0
                );
                ObjectAnimator mainFABAnimator = ObjectAnimator.ofFloat(
                        mMainFAB,
                        "rotation",
                        0,
                        -45
                );

                // Set up the ObjectAnimators
                rotationAnimator.setDuration(ANIMATION_DURATION)
                        .setInterpolator(interpolator);
                saturationAnimator.setDuration(ANIMATION_DURATION)
                        .setInterpolator(interpolator);
                mainFABAnimator.setInterpolator(interpolator);

                // Create dependencies
                animatorSet.play(rotationAnimator)
                        .with(saturationAnimator)
                        .with(mainFABAnimator);

                // Start animation
                animatorSet.start();
            } else {
                open = false;

                /// Close the FAB "menu"
                // Create the interpolator
                Interpolator interpolator = AnimationUtils.loadInterpolator(
                        getActivity(),
                        android.R.interpolator.fast_out_slow_in
                );

                // Create the Object animators
                ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(
                        mRotationFAB,
                        "translationY",
                        0,
                        getActivity().getResources().getDimension(R.dimen.rotation_translation)
                );
                ObjectAnimator saturationAnimator = ObjectAnimator.ofFloat(
                        mSaturationFAB,
                        "translationY",
                        0,
                        getActivity().getResources().getDimension(R.dimen.saturation_translation)
                );
                ObjectAnimator mainFABAnimator = ObjectAnimator.ofFloat(
                        mMainFAB,
                        "rotation",
                        -45,
                        0
                );
                Animator hideRotationSeekbar = createSeekBarCardAnimator(mRotationCardView, true);
                Animator hideSaturationSeekbar = createSeekBarCardAnimator(mSaturationCardView, true);



                // Set the ObjectAnimators
                rotationAnimator.setDuration(ANIMATION_DURATION)
                        .setInterpolator(interpolator);
                rotationAnimator.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRotationFAB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                saturationAnimator.setDuration(ANIMATION_DURATION)
                        .setInterpolator(interpolator);
                saturationAnimator.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSaturationFAB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                mainFABAnimator.setInterpolator(interpolator);

                // Create dependencies
                animatorSet.play(rotationAnimator)
                        .with(saturationAnimator)
                        .with(mainFABAnimator);

                if (mRotationCardView.getVisibility() == View.VISIBLE
                        || mSaturationCardView.getVisibility() == View.VISIBLE) {

                    animatorSet.play(rotationAnimator)
                            .after(hideRotationSeekbar)
                            .after(hideSaturationSeekbar);
                }

                // Start animation
                animatorSet.start();
            }


        }
    }

    private class RotationFABOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mRotationCardView.getVisibility() == View.INVISIBLE) {
                // Reveal rotation seekBar
                Animator revealAnim = createSeekBarCardAnimator(mRotationCardView, false);
                revealAnim.start();
            } else {
                // Hide rotation SeekBar
                Animator hideAnim = createSeekBarCardAnimator(mRotationCardView, true);
                hideAnim.start();
            }
        }
    }

    private class SaturationFABOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mSaturationCardView.getVisibility() == View.INVISIBLE) {
                // Reveal rotation seekBar
                Animator revealAnim = createSeekBarCardAnimator(mSaturationCardView, false);
                revealAnim.start();

            } else {
                // Hide rotation SeekBar
                Animator hideAnim = createSeekBarCardAnimator(mSaturationCardView, true);
                hideAnim.start();
            }
        }
    }

    /**
     * Creates the animator to reveal/hide seekBar CardViews
     * @param cardView CardView to animate
     * @param hide True if hiding animation
     * @return ObjectAnimator
     */
    private Animator createSeekBarCardAnimator(final CardView cardView, boolean hide){
        if (!hide) {
            // Get center
            int centerX = Math.abs(cardView.getRight());
            int centerY = Math.abs((cardView.getTop() - cardView.getBottom()) / 2);

            // Hack. mRotationCardView.getWidth() wasn't big enough
            // TODO Implement properly
            int radius = (int) (cardView.getWidth() * 1.3);

            Animator revealAnim = ViewAnimationUtils
                    .createCircularReveal(cardView, centerX, centerY, 0, radius)
                    .setDuration(ANIMATION_DURATION * 2);
            Interpolator interpolator = AnimationUtils.loadInterpolator(
                    getActivity(),
                    android.R.interpolator.fast_out_slow_in
            );
            revealAnim.setInterpolator(interpolator);

            cardView.setVisibility(View.VISIBLE);

            return revealAnim;
        } else {
            // Reveal rotation seekBar
            // Get center
            int centerX = Math.abs(cardView.getRight());
            int centerY = Math.abs((cardView.getTop() - cardView.getBottom()) / 2);

            // Hack. mRotationCardView.getWidth() wasn't big enough
            // TODO Implement properly
            int radius = (int) (cardView.getWidth() * 1.3);

            Animator hideAnim = ViewAnimationUtils
                    .createCircularReveal(cardView, centerX, centerY, radius, 0)
                    .setDuration(ANIMATION_DURATION * 2);
            Interpolator interpolator = AnimationUtils.loadInterpolator(
                    getActivity(),
                    android.R.interpolator.fast_out_slow_in
            );
            hideAnim.setInterpolator(interpolator);

            hideAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    cardView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {}
                @Override
                public void onAnimationRepeat(Animator animation) {}
                @Override
                public void onAnimationStart(Animator animation) {}
            });

            return hideAnim;
        }


    }

}
