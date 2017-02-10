package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import dekauliya.fyp.mathqa.CameraOcr.TesseractTool.TessEngine;

/**
 * Created by dekauliya on 10/2/17.
 */

@EBean
public class TextRecogniserTesseract extends TextRecogniserAbstract {
    TessEngine mTessEngine;

    public TextRecogniserTesseract(Context context) {
        super(context);

        mListener = (IOnOcrProcessingListener) context;

        if (mTessEngine == null){
            // Initialize the OCR engine
            mTessEngine = TessEngine.getInstance(context);
            mTessEngine.initOcrEngine();
        }
    }

    @Background(serial="ocr")
    @Override
    public void recogniseText(Bitmap bitmap) {
        mTessEngine.detectText(bitmap, mListener);
    }

    @Override
    public void preprocessImage(Bitmap bitmap) {
        mImagePreprocessor.preprocess(bitmap);
    }
}