package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.leptonica.android.AdaptiveMap;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Convert;
import com.googlecode.leptonica.android.Edge;
import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.MorphApp;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.leptonica.android.WriteFile;
import com.orhanobut.logger.Logger;

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
        pixs = AdaptiveMap.backgroundNormMorph(pixs);
        pixs = Binarize.sauvolaBinarizeTiled(pixs);
        double angle = Skew.findSkew(pixs);
        pixs = Rotate.rotate(pixs, (float) angle);

        mListener.onBitmapPreprocessed(WriteFile.writeBitmap(pixs));
    }

    @Background(serial="ocr")
    @Override
    public void customPreprocess(Bitmap bitmap, PreprocessingOptions prepOpts) {
        Pix pixs = ReadFile.readBitmap(bitmap);

        if (prepOpts.options.get(prepOpts.CONVERT)) {
            pixs = Convert.convertTo8(pixs);
        }

        // enhanced
        if (prepOpts.options.get(prepOpts.SHARPEN)){
            pixs = Enhance.unsharpMasking(pixs);
        }

        if (prepOpts.options.get(prepOpts.CONTRAST_NORM)) {
            pixs = AdaptiveMap.pixContrastNorm(pixs);
        }
        if (prepOpts.options.get(prepOpts.BACKGROUND_NORM)) {
            pixs = AdaptiveMap.backgroundNormMorph(pixs);
        }
        if (prepOpts.options.get(prepOpts.EDGE_SOBEL)) {
            pixs = Edge.pixSobelEdgeFilter(pixs, Edge.L_ALL_EDGES);
        }
        if (prepOpts.options.get(prepOpts.TOPHAT_BLACK)) {
            pixs = MorphApp.pixFastTophatBlack(pixs);
        }
        if (prepOpts.options.get(prepOpts.TOPHAT_WHITE)) {
            pixs = MorphApp.pixFastTophatWhite(pixs);
        }

        if (prepOpts.options.get(prepOpts.INVERT)){
            pixs.invert();
        }

        if (prepOpts.options.get(prepOpts.BINARIZE_OTSU)) {
            pixs = Binarize.otsuAdaptiveThreshold(pixs);
        }
        if (prepOpts.options.get(prepOpts.BINARIZE_SAUVOLA)){
            pixs = Binarize.sauvolaBinarizeTiled(pixs);
        }

        double angle = Skew.findSkew(pixs);
        Logger.d("Leptonicas angle: " + angle);
        pixs = Rotate.rotate(pixs, (float) angle);

        mListener.onBitmapPreprocessed(WriteFile.writeBitmap(pixs));
    }


}
