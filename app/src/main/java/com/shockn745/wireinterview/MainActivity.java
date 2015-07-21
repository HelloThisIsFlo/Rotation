package com.shockn745.wireinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ImageView mMinion1;
    private ImageView mMinion2;
    private SeekBar mSeekBar;
    private Button mRotateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find views by id
        mMinion1 = (ImageView) findViewById(R.id.minion_1_image_view);
        mMinion2 = (ImageView) findViewById(R.id.minion_2_image_view);
        mSeekBar = (SeekBar) findViewById(R.id.rotation_seek_bar);
        mRotateButton = (Button) findViewById(R.id.rotate_button);


        // Set the camera distance to give a more natural flip movement
        // And to respect the demo video



        mSeekBar.setMax(180);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMinion1.setRotation(progress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        mRotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyAnimation(mMinion1);
            }
        });

    }

    private void applyAnimation(View v){
        CustomRotationAnimation myAnimation = new CustomRotationAnimation(v);
        myAnimation.setDuration(5000);
        myAnimation.setInterpolator(new LinearInterpolator());
        myAnimation.setRepeatCount(Animation.INFINITE);

        v.startAnimation(myAnimation);
    }



}
