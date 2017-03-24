package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.EBean;

/**
 * Created by dekauliya on 10/2/17.
 */
@EBean
public abstract class TextRecogniserBase {
    Context context;
    IOnOcrProcessingListener mListener;
    ImagePreprocessorBase mImagePreprocessor;

    public TextRecogniserBase(Context context){
        this.context = context;
        this.mListener = (IOnOcrProcessingListener) context;
    }

    public void setmImagePreprocessor(ImagePreprocessorBase mImagePreprocessor) {
        this.mImagePreprocessor = mImagePreprocessor;
    }

    public abstract void recogniseText(Bitmap bitmap);
    public abstract void preprocessBitmap(Bitmap bitmap);
}

