package com.shockn745.wireinterview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Helper class used to load bitmap drawable in the memory at the perfect size
 *
 * @author Google
 */
public class BitmapHelper {

    private final static String LOG_TAG = BitmapHelper.class.getSimpleName();

    /**
     * Loads a Bitmap in memory at the perfect size depending on the view containing it.
     * @param res Resource handle to get the drawable
     * @param resId Resource is of the drawable
     * @param reqWidth Width of the containing View
     * @param reqHeight Height of the containing View
     * @return Bitmap Loaded bitmap in memory
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * This method process the "inSampleSize" used in the BitmapFactory.Options when decoding
     * the bitmap.
     * This allow to load in memory a resized version of the bitmap always at the perfect size
     * depending on the dimension of the view containing it.
     * @param options BitmapFactory.Options used to get the original dimensions of the Bitmap
     * @param reqWidth Width of the View
     * @param reqHeight Height of the View
     * @return inSampleSize Value to pass in BitmapFactory.Options when loading the Bitmap in memory
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(LOG_TAG, "SampleSize : " + inSampleSize);
        return inSampleSize;
    }
}
