package com.shockn745.wireinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int ANIM_DURATION = 6000;

    private ImageView mMinion1;
    private ImageView mMinion2;
    private SeekBar mRotationSeekBar;
    private Button mTestButton;

    private CustomRotationAnimation mMinion1Animation;
    private CustomRotationAnimation mMinion2Animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find views by id
        mMinion1 = (ImageView) findViewById(R.id.minion_1_image_view);
        mMinion2 = (ImageView) findViewById(R.id.minion_2_image_view);
        mRotationSeekBar = (SeekBar) findViewById(R.id.rotation_seek_bar);
        mTestButton = (Button) findViewById(R.id.rotate_button);


        // Init & start the animation after layout
        mMinion1.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so getTop/Bottom/etc != 0

                        // Remove listener after first layout
                        mMinion1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mMinion1Animation =
                                createCustomRotationAnimation(mMinion1, false);

                        startAnimationsIfReady();
                    }
                });
        mMinion2.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so getTop/Bottom/etc != 0

                        // Remove listener after first layout
                        mMinion2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mMinion2Animation =
                                createCustomRotationAnimation(mMinion2, true);

                        startAnimationsIfReady();
                    }
                });

        // Set up the rotationSeekBar
        // Max value (in degree)
        mRotationSeekBar.setMax(180);
        mRotationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Set rotation (in degree)
                mMinion1.setRotation(progress);
                mMinion2.setRotation(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // TODO REMOVE TEST : Test button
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    /**
     * Create and init CustomRotationAnimation
     * @param v View to be animated
     * @param startHidden True if view hidden at first
     * @return The animation
     */
    private CustomRotationAnimation createCustomRotationAnimation(View v, boolean startHidden) {
        CustomRotationAnimation animation = new CustomRotationAnimation(v, startHidden);
        animation.setDuration(ANIM_DURATION);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);

        return animation;
    }

    /**
     * Start animations if both animations have been initialized
     */
    private void startAnimationsIfReady() {
        if (mMinion1Animation != null && mMinion2Animation != null) {
            mMinion1.startAnimation(mMinion1Animation);
            mMinion2.startAnimation(mMinion2Animation);
        }
    }
}
