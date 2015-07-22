package com.shockn745.wireinterview.fragments;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.shockn745.wireinterview.AnimatedMinion;
import com.shockn745.wireinterview.R;

/**
 * Fragment for the gyro demo
 */
public class GyroFragment extends BaseFragment implements SensorEventListener {

    private static final String LOG_TAG = GyroFragment.class.getSimpleName();

    private AnimatedMinion mMinion1;
    private AnimatedMinion mMinion2;
    private SeekBar mSaturationSeekBar;

    SensorManager mSensorManager;
    Sensor mSensor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Find views by id
        ImageView mMinion1ImageView = (ImageView) rootView.findViewById(R.id.minion_1_image_view);
        ImageView mMinion2ImageView = (ImageView) rootView.findViewById(R.id.minion_2_image_view);
        mSaturationSeekBar = (SeekBar) rootView.findViewById(R.id.saturation_seek_bar);

        // Init AnimatedMinions
        mMinion1 = new AnimatedMinion();
        mMinion2 = new AnimatedMinion();
        initAnimatedMinion(mMinion1, mMinion1ImageView, false, R.drawable.full_res_minion_1);
        initAnimatedMinion(mMinion2, mMinion2ImageView, true, R.drawable.full_res_minion_2);

        // Init the gyro sensor
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

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
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void startAnimationsIfReady() {
        if (mMinion1.isInitialized() && mMinion2.isInitialized()) {
            mMinion1.startAnimation();
            mMinion2.startAnimation();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mMinion1.getImageView().setRotation(event.values[2] * 180);
        mMinion2.getImageView().setRotation(event.values[2] * 180);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
