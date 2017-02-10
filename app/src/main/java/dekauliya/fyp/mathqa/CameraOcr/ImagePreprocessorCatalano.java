package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Tools.DocumentSkewChecker;

/**
 * Created by dekauliya on 21/1/17.
 */
@EBean
public class ImagePreprocessorCatalano extends ImagePreprocessorBase{

    public ImagePreprocessorCatalano(Context context) {
        super(context);
    }

    @Background(serial="ocr")
    public void preprocess(Bitmap bmpOriginal){
        Bitmap mutableBitmap = bmpOriginal.copy(Bitmap.Config.ARGB_8888, true);
        FastBitmap fBmp = new FastBitmap(mutableBitmap);
        fBmp.toGrayscale();

//        OtsuThreshold ot = new OtsuThreshold();
//        MaximumEntropyThreshold met = new MaximumEntropyThreshold();
//        BradleyLocalThreshold blt = new BradleyLocalThreshold();
//        blt.applyInPlace(fBmp);
//        DifferenceEdgeDetector ded = new DifferenceEdgeDetector();
//        ConservativeSmoothing cs = new ConservativeSmoothing();
        DocumentSkewChecker dsc = new DocumentSkewChecker();
//        ot.applyInPlace(fBmp);
//        cs.applyInPlace(fBmp);
//        ded.applyInPlace(fBmp);
        double angle = dsc.getSkewAngle(fBmp);
        Catalano.Imaging.Filters.Rotate r = new Catalano.Imaging.Filters.Rotate(angle, true);
        r.applyInPlace(fBmp);

        mListener.onImagePreprocessed(fBmp.toBitmap());
    }
}
