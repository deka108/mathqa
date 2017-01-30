package dekauliya.fyp.mathqa.UI.CameraOcr;

/**
 * Created by dekauliya on 21/1/17.
 */

public interface IOcrConfig {
    /** The default OCR engine to use. */
    public static final String DEFAULT_OCR_ENGINE_MODE = "Tesseract";

    /** ISO 639-3 language code indicating the default recognition language. */
    public static final String DEFAULT_SOURCE_LANGUAGE_CODE = "eng";

    /** Resource to use for data file downloads. */
    static final String DOWNLOAD_BASE = "https://github.com/tesseract-ocr/tessdata/raw/3.04.00/";

    static final String TESSDATA_PATH = "tessdata";
}
