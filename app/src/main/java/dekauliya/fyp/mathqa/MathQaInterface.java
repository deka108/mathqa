package dekauliya.fyp.mathqa;

/**
 * Created by dekauliya on 28/1/17.
 */

public interface MathQaInterface {
    int SUBJECT_ADDITIONAL = 1;
    int SUBJECT_ELEMENTARY = 2;
    int SUBJECT_H2 = 3;
    int SUBJECT_PSLE = 4;

    String BASE_URL = "http://172.26.104.156:8000/apiv2/";

    int MAX_RESCALED_SIZE = 640;

    int PROCESSOR_LEPTONICA = 1;
    int PROCESSOR_CATALANO = 2;

    String CAPTURED_IMAGE_URI = "capturedImageUri";
    String OCR_OPTION = "ocrOption";

    int OCR_TESSERACT = 1;
    int OCR_GOOGLE_API = 2;
}
