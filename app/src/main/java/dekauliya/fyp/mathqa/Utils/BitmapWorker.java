package dekauliya.fyp.mathqa.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static dekauliya.fyp.mathqa.ImagePreviewActivity.MAX_RESCALED_SIZE;

/**
 * Created by dekauliya on 21/1/17.
 */

public class BitmapWorker extends AsyncTask<Void, Void, Bitmap> {
    private Uri mImageUri;
    private final OnImageLoadedListener mOnImageLoadedListener;

    protected Context mContext;

    public BitmapWorker(Context context, Uri imageUri, OnImageLoadedListener onImageLoadedListener){
        mContext = context;
        mImageUri = imageUri;
        mOnImageLoadedListener = onImageLoadedListener;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        try {
            // Get real size
            InputStream input = mContext.getContentResolver().openInputStream(mImageUri);
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(input, null, opts);
            input.close();
            int originalWidth = opts.outWidth;
            int originalHeight= opts.outHeight;
            Logger.d("Original Width: %d, Height: %d", originalWidth, originalHeight);
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;

            // Down sampling
            int originalSize = (originalHeight > originalWidth) ? originalHeight : originalWidth;
            double ratio = (originalSize > MAX_RESCALED_SIZE) ?
                    (originalSize/MAX_RESCALED_SIZE) : 1.0;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = getPowerOfTwoForSampleRatio(ratio);

            // Scaling
            opts.inJustDecodeBounds = false;
            input = mContext.getContentResolver().openInputStream(mImageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, opts);
            input.close();

            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //BEGIN_INCLUDE(complete_background_work)
        boolean success = false;

        // if cancel was called on this task or the "exit early" flag is set then we're done
        if (isCancelled()) {
            bitmap = null;
        }

        if (bitmap != null){
            success = true;
        }

        if(mOnImageLoadedListener != null){
            mOnImageLoadedListener.onImageLoaded(success, bitmap);
        }

    }

    /**
     * Interface definition for callback on image loaded successfully.
     */
    public interface OnImageLoadedListener {

        /**
         * Called once the image has been loaded.
         * @param success True if the image was loaded successfully, false if
         *                there was an error.
         */
        void onImageLoaded(boolean success, Bitmap bitmap);
    }

}
