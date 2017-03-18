package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.leptonica.android.AdaptiveMap;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Edge;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.leptonica.android.WriteFile;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

/**
 * Created by dekauliya on 21/1/17.
 */
@EBean
public class ImagePreprocessorLeptonica extends ImagePreprocessorBase{

    public ImagePreprocessorLeptonica(Context context) {
        super(context);
    }

    @Background(serial="ocr")
    @Override
    public void preprocess(Bitmap bitmap) {
        Pix pixs = ReadFile.readBitmap(bitmap);

        pixs = AdaptiveMap.pixContrastNorm(pixs);
        pixs = Binarize.otsuAdaptiveThreshold(pixs);
        pixs = AdaptiveMap.backgroundNormMorph(pixs);
//        pixs = Binarize.sauvolaBinarizeTiled(pixs);
        pixs = Edge.pixSobelEdgeFilter(pixs, Edge.L_ALL_EDGES);
        double angle = Skew.findSkew(pixs);
        pixs = Rotate.rotate(pixs, (float) angle);

        mListener.onImagePreprocessed(WriteFile.writeBitmap(pixs));
    }
}
