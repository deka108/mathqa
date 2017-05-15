package dekauliya.fyp.mathqa.CameraOcr;

/**
 * Created by dekauliya on 18/3/17.
 *
 * Catalano Image pre-processing options.
 */

public class CatalanoOptions extends PreprocessingOptions {
    String[] catalanoOptions = new String[]{
            CLOSING,
            CONTRAST_NORM,
            BRIGHTNESS_NORM,
            BACKGROUND_NORM,
            BLOB,
            FILL_HOLES,
            IMAGE_NORM,
            DILATATION,
            EROSION,
            OPENING,
            SHARPEN,
            SMOOTHING,
            MEDIAN,
            VARIANCE,
            MORPHOLOGIC_GRADIENT,
            EDGE_CANNY, // slow
            EDGE_SOBEL, // slow
            EXTRACT_BOUNDARY,
            TOPHAT_WHITE,
            TOPHAT_BLACK,
            INVERT,
            BINARIZE_BRADLEY, // fast
            BINARIZE_HYSTERESIS, // (e) slow
            BINARIZE_MAX_ENTROPY, // (e) slow
            BINARIZE_OTSU, // fast
            BORDER_REMOVAL,
            DESKEW};

    public CatalanoOptions(){
        super();
        super.initOptions(catalanoOptions);
    }
}

