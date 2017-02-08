package dekauliya.fyp.mathqa.CameraOcr.TesseractTool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;


/**
 * Created by dekauliya on 25/9/16.
 */

public class TessAsyncEngine extends AsyncTask<Object, Void, String> {
    private static final String TAG = TessAsyncEngine.class.getName();
    private Bitmap mBitmap;
    private Activity mContext;
    private String mImagePath;
    public TesseractAsyncResponse delegate = null;

    public TessAsyncEngine(TesseractAsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Object... params) {
        try{
            if(params.length < 2) {
                Logger.e("Error passing parameter to execute - missing params");
                return null;
            }

            if(!(params[0] instanceof Activity) || !(params[1] instanceof Bitmap))  {
                Logger.e("Error passing parameter to execute(context, bitmap)");
                return null;
            }

            mContext = (Activity) params[0];
            mBitmap = (Bitmap) params[1];


            if(mContext == null || mBitmap == null) {
                Log.e(TAG, "Error passed null parameter to execute(context, bitmap)");
                return null;
            }

            TessEngine tessEngine = TessEngine.getInstance(mContext);

            if (params.length == 3 && params[2] != null && params[2] instanceof String){
                mImagePath = (String) params[2];
                mBitmap = correctBitmap(mBitmap, mImagePath);
            }else{
                mBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }

//            String result = tessEngine.detectText(mBitmap);
//            Logger.d("RESULT of OCR: " + result);
//            return result;
            return "TEST";

        }catch (Exception ex) {
            Logger.e("Error: " + ex + "\n" + ex.getMessage());
        }
        return null;
    }

    public Bitmap correctBitmap(Bitmap bitmap, String imagePath){
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(imagePath);

            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
