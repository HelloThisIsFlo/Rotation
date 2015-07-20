package com.shockn745.wireinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
                if (progress <= 90) {
                    mMinion1.setRotationY(progress);
                    mMinion1.setVisibility(View.VISIBLE);
                    mMinion2.setVisibility(View.GONE);
                } else if (progress > 90) {
                    mMinion2.setRotationY(180 + progress);
                    mMinion1.setVisibility(View.GONE);
                    mMinion2.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

}
