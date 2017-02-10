package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.EBean;

/**
 * Created by dekauliya on 10/2/17.
 */
@EBean
public abstract class TextRecogniserAbstract {
    Context context;
    IOnOcrProcessingListener mListener;
    ImagePreprocessorBase mImagePreprocessor;

    public TextRecogniserAbstract(Context context){
        this.context = context;
        this.mListener = (IOnOcrProcessingListener) context;
    }

    public void setmImagePreprocessor(ImagePreprocessorBase mImagePreprocessor) {
        this.mImagePreprocessor = mImagePreprocessor;
    }

    public abstract void recogniseText(Bitmap bitmap);
    public abstract void preprocessImage(Bitmap bitmap);
}

