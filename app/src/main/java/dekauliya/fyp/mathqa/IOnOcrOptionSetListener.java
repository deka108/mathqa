package dekauliya.fyp.mathqa;

import dekauliya.fyp.mathqa.Utils.OcrUtils;

/**
 * Created by dekauliya on 19/3/17.
 */

public interface IOnOcrOptionSetListener {
     void onOcrOptionSet(OcrUtils.OcrOptions ocrOptions);

     void onPreprocessorOptionSet(OcrUtils.OcrOptions ocrOptions);
}
