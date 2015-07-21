package com.shockn745.wireinterview;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    ImageView mMinion1;
    ImageView mMinion2;
    SeekBar mSeekBar;
    SeekBar mSaturationSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find views by id
        mMinion1 = (ImageView) findViewById(R.id.minion_1_image_view);
        mMinion2 = (ImageView) findViewById(R.id.minion_2_image_view);
        mSeekBar = (SeekBar) findViewById(R.id.rotation_seek_bar);
        mSaturationSeekBar = (SeekBar) findViewById(R.id.saturation_seek_bar);


        // Set the camera distance to give a more natural flip movement
        // And to respect the demo video
        float scale = this.getResources().getDisplayMetrics().density;
        float distance = 4000;
        mMinion1.setCameraDistance(distance * scale);
        mMinion2.setCameraDistance(distance * scale);



        mSeekBar.setMax(1000);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // Init the saturation Seekbar
        final ColorMatrix colorMatrix = new ColorMatrix();
        mSaturationSeekBar.setMax(1000);
        mSaturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorMatrix.setSaturation(((float) progress) / 1000);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

                mMinion1.setColorFilter(filter);
                mMinion2.setColorFilter(filter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}
