package dekauliya.fyp.mathqa.CameraOcr;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by dekauliya on 19/3/17.
 *
 * Definitions and common features of Image processing options.
 */

public class PreprocessingOptions {
    public static String BACKGROUND_NORM = "background_norm";
    public static String BINARIZE_BRADLEY = "binarize_bradley";
    public static String BINARIZE_HYSTERESIS = "binarize_hysteresis";
    public static String BINARIZE_OTSU = "binarize_otsu";
    public static String BINARIZE_SAUVOLA = "binarize_sauvola";
    public static String BINARIZE_MAX_ENTROPY = "binarize_max_entropy";
    public static String BLOB = "blob";
    public static String BLOB_DETECT = "blob_detect";
    public static String BORDER_REMOVAL = "border_removal";
    public static String BRIGHTNESS_NORM = "brightness_norm";
    public static String CLOSING = "closing";
    public static String CONVERT = "convert";
    public static String EXTRACT_BOUNDARY = "extract_boundary";
    public static String CONTRAST_NORM = "contrast_norm";
    public static String DESKEW = "deskew";
    public static String DILATATION = "dilatation";
    public static String EDGE_SOBEL = "edge_sobel";
    public static String EDGE_CANNY = "edge_canny";
    public static String FILL_HOLES = "fill_holes";
    public static String EROSION = "erosion";
    public static String IMAGE_NORM = "image_norm";
    public static String INVERT = "invert";
    public static String MEDIAN = "median";
    public static String MORPHOLOGIC_GRADIENT = "morphologic_gradient";
    public static String OPENING = "opening";
    public static String TOPHAT_BLACK = "tophat_black";
    public static String TOPHAT_WHITE = "tophat_white";
    public static String SHARPEN = "sharpen";
    public static String SMOOTHING = "smoothing";
    public static String VARIANCE = "variance";

    String[] optValues;
    HashMap<String, Boolean> options;

    public PreprocessingOptions(){
        options = new HashMap();
    }

    public void initOptions(String[] optionArgs){
        optValues = optionArgs;
        for(String option: optValues){
            options.put(option, false);
        }
    }

    public void setOption(String option, boolean newValue){
        options.put(option, newValue);
    }

    public Set<String> getOptions(){
        return options.keySet();
    }
}
