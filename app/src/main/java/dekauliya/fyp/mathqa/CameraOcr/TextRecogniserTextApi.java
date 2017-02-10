package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 10/2/17.
 */

@EBean
public class TextRecogniserTextApi extends TextRecogniserAbstract {

    public TextRecogniserTextApi(Context context) {
        super(context);
    }

    @Override
    public void preprocessImage(Bitmap bitmap) {
        mListener.onImagePreprocessed(bitmap);
    }

    @Background(serial = "ocr")
    @Override
    public void recogniseText(Bitmap bitmap) {
        TextRecognizer detector = new TextRecognizer.Builder(context).build();
        String result = null;

        if (!detector.isOperational()){
            Logger.w("Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = context.registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(context, context.getString(R.string.low_storage_error),
                        Toast.LENGTH_SHORT)
                        .show();
                Logger.w(context.getString(R.string.low_storage_error));
            }
        } else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            frame.getGrayscaleImageData(); // check grayscale works?
            SparseArray<TextBlock> results = detector.detect(frame);

            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0; i<results.size(); i++){
                TextBlock item = results.valueAt(i);
                if (item != null && item.getValue() != null){
                    Logger.d("Text detected: " + item.getValue());
                    stringBuffer.append(item.getValue());
                }
            }
            result = stringBuffer.toString();
        }
        mListener.onOcrProcessed(result);
    }
}