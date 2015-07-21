package com.shockn745.wireinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ImageView mMinion1;
    private ImageView mMinion2;
    private SeekBar mRotationSeekBar;
    private Button mTestButton;

    private CustomRotationAnimation mAnimation;

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
                        mAnimation = new CustomRotationAnimation(mMinion1);
                        mAnimation.setDuration(5000);
                        mAnimation.setInterpolator(new LinearInterpolator());
                        mAnimation.setRepeatCount(Animation.INFINITE);

                        mMinion1.startAnimation(mAnimation);
                    }
                });


        // Set up the rotationSeekBar
        // Max value (in degree)
        mRotationSeekBar.setMax(180);
        mRotationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Set rotation (in degree)
                mAnimation.setZRotation(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomRotationAnimation myAnimation = new CustomRotationAnimation(mMinion1);
                myAnimation.setDuration(5000);
                myAnimation.setInterpolator(new LinearInterpolator());
                myAnimation.setRepeatCount(Animation.INFINITE);

                mMinion1.startAnimation(myAnimation);
            }
        });

    }

}
