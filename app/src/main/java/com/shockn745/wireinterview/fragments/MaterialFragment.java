package com.shockn745.wireinterview.fragments;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
 * Fragment for the material demo
 *
 * @author Florian Kempenich
 */
public class MaterialFragment extends BaseFragment {

    private static final String LOG_TAG = MaterialFragment.class.getSimpleName();

    private static final int ROTATION_SPAN = 270;

    private AnimatedMinion mMinion1;
    private AnimatedMinion mMinion2;
    private SeekBar mRotationSeekBar;
    private SeekBar mSaturationSeekBar;

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
}
