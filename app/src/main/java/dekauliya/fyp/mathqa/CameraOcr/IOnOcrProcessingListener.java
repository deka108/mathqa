package dekauliya.fyp.mathqa.CameraOcr;

import android.graphics.Bitmap;

/**
 * Created by dekauliya on 21/1/17.
 *
 * Common interfaces for image processor.
 */

public interface IOnOcrProcessingListener {
    void onBitmapPreprocessed(Bitmap bitmap);
    void onOcrProcessed(String result);
    void onBitmapReady(Bitmap bitmap);
}
