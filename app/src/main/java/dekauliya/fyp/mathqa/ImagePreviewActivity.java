package dekauliya.fyp.mathqa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.leptonica.android.AdaptiveMap;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.leptonica.android.WriteFile;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@EActivity()
public class ImagePreviewActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.ip_image_preview) ImageView imagePreview;
    @ViewById(R.id.ip_ocr_result) TextView ocrResult;

    public static final int MAX_RESCALED_SIZE = 640;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(MainActivity_.CAPTURED_IMAGE_URI);
        try{
            if (uri != null){
                preprocessImage(uri);
            }
        }catch(Exception e){
            Logger.e(getClass().getName(), e);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Background
    protected void preprocessImage(Uri imageUri) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        try {
            // Get real size
            InputStream input = this.getContentResolver().openInputStream(imageUri);
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
            input = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, opts);
            input.close();

            preprocessingPipeline(bitmap);
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

    @Background
    public void toGrayscale(Bitmap bmpOriginal){
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

        updateImage(bmpGrayscale);
    }

    @Background
    public void preprocessingPipeline(Bitmap bmpOriginal){
        Pix pixs = ReadFile.readBitmap(bmpOriginal);
        pixs = Enhance.unsharpMasking(pixs);
        pixs = AdaptiveMap.pixContrastNorm(pixs);
        pixs = Binarize.otsuAdaptiveThreshold(pixs);
        double angle = Skew.findSkew(pixs);
        pixs = Rotate.rotate(pixs, (float) angle);

        updateImage(WriteFile.writeBitmap(pixs));
    }

    @UiThread
    public void updateImage(Bitmap bmp){
        imagePreview.setImageBitmap(bmp);
    }

}
