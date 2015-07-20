package com.shockn745.wireinterview;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    ImageView mMinion1;
    ImageView mMinion2;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find views by id
        mMinion1 = (ImageView) findViewById(R.id.minion_1_image_view);
        mMinion2 = (ImageView) findViewById(R.id.minion_2_image_view);
        mSeekBar = (SeekBar) findViewById(R.id.rotation_seek_bar);


        // Set the camera distance to give a more natural flip movement
        // And to respect the demo video
        float scale = this.getResources().getDisplayMetrics().density;
        float distance = 4000;
        mMinion1.setCameraDistance(distance * scale);
        mMinion2.setCameraDistance(distance * scale);

        mSeekBar.setMax(180);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                applyAnimation(mMinion1);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    private void applyAnimation(View v){
        MyAnimation myAnimation = new MyAnimation();
        myAnimation.setDuration(5000);
        myAnimation.setFillAfter(true);
        myAnimation.setInterpolator(new OvershootInterpolator());

        v.startAnimation(myAnimation);
    }

    public class MyAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            final Matrix matrix = t.getMatrix();

//            matrix.postRotate(30);
            float[] values = new float[9];
            matrix.getValues(values);

            for (float v : values) {
                Log.d("test", "Value : " + v);
            }
            Log.d("test", "-----------");
            double angleRad = Math.toRadians(20*interpolatedTime);
            float cos = (float) Math.cos(angleRad);
            float sin = (float) Math.sin(angleRad);
            matrix.setValues(new float[]{cos, -sin, 0, sin, cos, 0, 0, 0, 1});
            Log.d("test", "cos = " + cos);
            Log.d("test", "sin = " + sin);
            Log.d("test", "----------------------------------------");

//            double angleRad = Math.toRadians(30);
//            float cos = (float) Math.cos(angleRad);
//            float sin = (float) Math.sin(angleRad);
//            matrix.setValues(new float[]{1,0,0,0,cos,-sin,0,sin,cos});

            super.applyTransformation(interpolatedTime, t);
        }
    }


}
