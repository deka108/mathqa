package dekauliya.fyp.mathqa.CameraOcr;

/**
 * Created by dekauliya on 18/3/17.
 *
 * Leptonica Image Pre-processing options.
 */

public class LeptonicaOptions extends PreprocessingOptions {

    String[] leptonicaOptions = new String[]{CONVERT, DESKEW,
            BRIGHTNESS_NORM, CONTRAST_NORM,
            EDGE_SOBEL,  INVERT,  SHARPEN, BINARIZE_SAUVOLA, BINARIZE_OTSU,
            BACKGROUND_NORM, CONTRAST_NORM, TOPHAT_BLACK, TOPHAT_WHITE};

    public LeptonicaOptions(){
        super();
        super.initOptions(leptonicaOptions);
    }
}
