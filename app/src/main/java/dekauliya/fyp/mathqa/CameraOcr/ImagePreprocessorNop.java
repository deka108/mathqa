package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by dekauliya on 19/3/17.
 *
 * Image processor implementation for no action.
 *
 */

public class ImagePreprocessorNop extends ImagePreprocessorBase {
    public ImagePreprocessorNop(Context context) {
        super(context);
    }

    @Override
    public void preprocess(Bitmap bitmap) {
        mListener.onBitmapPreprocessed(bitmap);
    }

    @Override
    public void customPreprocess(Bitmap bitmap, PreprocessingOptions props) {

    }
}
