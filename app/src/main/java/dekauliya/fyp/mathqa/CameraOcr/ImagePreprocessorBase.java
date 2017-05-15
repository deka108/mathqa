package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static dekauliya.fyp.mathqa.MathQaInterface.MAX_RESCALED_SIZE;


/**
 * Created by dekauliya on 21/1/17.
 *
 * Provides common features and methods of an Image processor which involve bitmap processing,
 * preprocessing and downsampling.
 *
 */
@EBean
public class ImagePreprocessorBase {
    Context context;
    IOnOcrProcessingListener mListener;

    public ImagePreprocessorBase(Context context){
        this.context = context;
        this.mListener = (IOnOcrProcessingListener) context;
    }

    @Background(serial="ocr")
    public void getBitmapFromUri(Uri imageUri) {
        Logger.d("Getting resized bitmaps...");
        BitmapFactory.Options opts = new BitmapFactory.Options();

        try {
            // Get real size
            InputStream input = context.getContentResolver().openInputStream(imageUri);
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(input, null, opts);
            input.close();
            int originalWidth = opts.outWidth;
            int originalHeight= opts.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return;

            // Down sampling
            int originalSize = (originalHeight > originalWidth) ? originalHeight : originalWidth;
            double ratio = (originalSize > MAX_RESCALED_SIZE) ?
                    (originalSize/MAX_RESCALED_SIZE) : 1.0;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = getPowerOfTwoForSampleRatio(ratio);

            // Scaling
            opts.inJustDecodeBounds = false;
            input = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, opts);
            input.close();

            mListener.onBitmapReady(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    @Background(serial="ocr")
    public void convertGrayscale(Bitmap bmpOriginal){
        int width = bmpOriginal.getWidth();
        int height = bmpOriginal.getHeight();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        canvas.drawBitmap(bmpOriginal, 0, 0, paint);
        preprocess(bmpGrayscale);
    }

    public void preprocess(Bitmap bitmap, boolean toGrayScale){
        if (toGrayScale){
            convertGrayscale(bitmap);
        }else{
            preprocess(bitmap);
        }
    }

    public void preprocess(Bitmap bitmap){

    }

    public  void customPreprocess(Bitmap bitmap, PreprocessingOptions props){

    }
}
