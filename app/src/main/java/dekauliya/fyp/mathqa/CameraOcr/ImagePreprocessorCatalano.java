package dekauliya.fyp.mathqa.CameraOcr;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.List;

import Catalano.Imaging.Concurrent.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Concurrent.Filters.Closing;
import Catalano.Imaging.Concurrent.Filters.ConservativeSmoothing;
import Catalano.Imaging.Concurrent.Filters.Dilatation;
import Catalano.Imaging.Concurrent.Filters.Erosion;
import Catalano.Imaging.Concurrent.Filters.HysteresisThreshold;
import Catalano.Imaging.Concurrent.Filters.ImageNormalization;
import Catalano.Imaging.Concurrent.Filters.Invert;
import Catalano.Imaging.Concurrent.Filters.MaximumEntropyThreshold;
import Catalano.Imaging.Concurrent.Filters.Median;
import Catalano.Imaging.Concurrent.Filters.MorphologicGradientImage;
import Catalano.Imaging.Concurrent.Filters.Opening;
import Catalano.Imaging.Concurrent.Filters.OtsuThreshold;
import Catalano.Imaging.Concurrent.Filters.Sharpen;
import Catalano.Imaging.Concurrent.Filters.SobelEdgeDetector;
import Catalano.Imaging.Concurrent.Filters.Variance;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BrightnessCorrection;
import Catalano.Imaging.Filters.CannyEdgeDetector;
import Catalano.Imaging.Filters.ContrastCorrection;
import Catalano.Imaging.Filters.ExtractBoundary;
import Catalano.Imaging.Filters.FillHoles;
import Catalano.Imaging.Filters.RotateBilinear;
import Catalano.Imaging.Filters.RotateNearestNeighbor;
import Catalano.Imaging.Tools.Blob;
import Catalano.Imaging.Tools.BlobDetection;
import Catalano.Imaging.Tools.BlobExtractor;
import Catalano.Imaging.Tools.DocumentSkewChecker;


/**
 * Created by dekauliya on 21/1/17.
 *
 * Image processor implementation using Catalano.
 *
 */
@EBean
public class ImagePreprocessorCatalano extends ImagePreprocessorBase {

    public ImagePreprocessorCatalano(Context context) {
        super(context);
    }
    // grayscale image
    // median filter
    // computation of threshold values
    // bilinear interpolation
    // binarized image
    // ocr

    // otsu: bad for low contrast and non -uniform illumination

    // best: niblack, yanowitz
    //
    // Apply Otsu threshold to the image
    // distanc etransform
    // stroke width on the distance transform
    // apply morphological operation to disconnect image
    // filter bounding box
    // use convexhull

    @Background(serial="ocr")
    @Override
    public void preprocess(Bitmap bmpOriginal){
        Bitmap mutableBitmap = bmpOriginal.copy(Bitmap.Config.ARGB_8888, true);
        FastBitmap fBmp = new FastBitmap(mutableBitmap);
        fBmp.toGrayscale();
        BrightnessCorrection bc = new BrightnessCorrection();
        bc.applyInPlace(fBmp);
        ContrastCorrection cc = new ContrastCorrection();
        cc.applyInPlace(fBmp);

        // Binarization
        OtsuThreshold ot = new OtsuThreshold();
        ot.applyInPlace(fBmp);

        // De-skewing
        DocumentSkewChecker dsc = new DocumentSkewChecker();
        double angle = dsc.getSkewAngle(fBmp);
        RotateBilinear r = new RotateBilinear(-angle);
        r.setFillColor(255);
        r.applyInPlace(fBmp);

        mListener.onBitmapPreprocessed(fBmp.toBitmap());
    }

    @Background(serial="ocr")
    @Override
    public void customPreprocess(Bitmap bitmap, PreprocessingOptions props) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        FastBitmap fBmp = new FastBitmap(mutableBitmap);
        fBmp.toGrayscale();

        if (props.options.get(props.BRIGHTNESS_NORM)) {
            BrightnessCorrection bc = new BrightnessCorrection();
            bc.applyInPlace(fBmp);
        }

        if (props.options.get(props.CONTRAST_NORM)) {
            ContrastCorrection cc = new ContrastCorrection();
            cc.applyInPlace(fBmp);
        }

        // Mean and Variance
        if (props.options.get(props.IMAGE_NORM)){
            ImageNormalization in = new ImageNormalization();
            in.applyInPlace(fBmp);
        }

        if (props.options.get(props.SHARPEN)){
            Sharpen sh = new Sharpen();
            sh.applyInPlace(fBmp);
        }

        if (props.options.get(props.SMOOTHING)){
            ConservativeSmoothing cs = new ConservativeSmoothing();
            cs.applyInPlace(fBmp);
        }

        if (props.options.get(props.BINARIZE_MAX_ENTROPY)){
            MaximumEntropyThreshold met = new MaximumEntropyThreshold();
            met.applyInPlace(fBmp);
        }

        if (props.options.get(props.BINARIZE_HYSTERESIS)){
            HysteresisThreshold ht = new HysteresisThreshold();
            ht.applyInPlace(fBmp);
        }

        if (props.options.get(props.BINARIZE_BRADLEY)){
            BradleyLocalThreshold blt = new BradleyLocalThreshold();
            blt.applyInPlace(fBmp);
        }

        if (props.options.get(props.BINARIZE_OTSU)) {
            OtsuThreshold ot = new OtsuThreshold();
            ot.applyInPlace(fBmp);
        }

        // Deskewing
        DocumentSkewChecker dsc = new DocumentSkewChecker();
        double angle = dsc.getSkewAngle(fBmp);
        RotateNearestNeighbor rnn = new RotateNearestNeighbor(-angle);
        rnn.setFillColor(255);
        rnn.applyInPlace(fBmp);

        if (props.options.get(props.EDGE_SOBEL)){
            SobelEdgeDetector sed = new SobelEdgeDetector();
            sed.applyInPlace(fBmp);
            if (props.options.get(props.INVERT)){
                Invert i = new Invert();
                i.applyInPlace(fBmp);
            }
            if (props.options.get(props.FILL_HOLES)) {
                FillHoles fh = new FillHoles();
                fh.applyInPlace(fBmp);
            }
        }

        if (props.options.get(props.EDGE_CANNY)){
            CannyEdgeDetector ced = new CannyEdgeDetector();
            ced.applyInPlace(fBmp);
            if (props.options.get(props.INVERT)){
                Invert i = new Invert();
                i.applyInPlace(fBmp);
            }
            if (props.options.get(props.FILL_HOLES)) {
                FillHoles fh = new FillHoles();
                fh.applyInPlace(fBmp);
            }
        }

        if (props.options.get(props.EXTRACT_BOUNDARY)){
            ExtractBoundary eb = new ExtractBoundary();
            eb.applyInPlace(fBmp);
            if (props.options.get(props.INVERT)){
                Invert i = new Invert();
                i.applyInPlace(fBmp);
            }
        }

        if (props.options.get(props.BLOB)){
            BlobDetection bd = new BlobDetection();
            List<Blob> blobs = bd.ProcessImage(fBmp);
            Blob biggestBlob = blobs.get(bd.getIdBiggestBlob());
            BlobExtractor be = new BlobExtractor();
            fBmp = be.Extract(fBmp, biggestBlob);
//            ExtractBiggestBlob ebb = new ExtractBiggestBlob();
//            fBmp = ebb.Extract(fBmp);
        }


        if (props.options.get(props.MEDIAN)){
            Median m = new Median();
            m.applyInPlace(fBmp);
        }

        if (props.options.get(props.VARIANCE)){
            Variance v = new Variance();
            v.applyInPlace(fBmp);
            if (props.options.get(props.INVERT)){
                Invert i = new Invert();
                i.applyInPlace(fBmp);
            }
        }

        if (props.options.get(props.EROSION)){
            Erosion er = new Erosion();
            er.applyInPlace(fBmp);
        }


        if (props.options.get(props.DILATATION)){
            Dilatation dl = new Dilatation();
            dl.applyInPlace(fBmp);
        }

        if (props.options.get(props.MORPHOLOGIC_GRADIENT)){
            MorphologicGradientImage mgi = new MorphologicGradientImage();
            mgi.applyInPlace(fBmp);
            if (props.options.get(props.INVERT)){
                Invert i = new Invert();
                i.applyInPlace(fBmp);
            }
        }

        if (props.options.get(props.CLOSING)){
            Closing c = new Closing();
            c.applyInPlace(fBmp);
        }

        if (props.options.get(props.OPENING)){
            Opening o = new Opening();
            o.applyInPlace(fBmp);
        }

        // Border removal:
        // canny edge detection
        // dilate
        // find contours
        // find border / bounding rect
        // reduce border
        // rank filter
        // (see -despeckle and -enhance and -morphology open/close)
        if (props.options.get(props.BORDER_REMOVAL)){
            CannyEdgeDetector ced = new CannyEdgeDetector();
            ced.applyInPlace(fBmp);
            Dilatation d = new Dilatation();
            d.applyInPlace(fBmp);
            Median m = new Median();
            m.applyInPlace(fBmp);
        }

        // remove borders

        mListener.onBitmapPreprocessed(fBmp.toBitmap());
    }
}
