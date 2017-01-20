package dekauliya.fyp.mathqa.TesseractTool;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by dekauliya on 25/9/16.
 */

public class TessEngine {
    private static final String TAG = TessEngine.class.getName();
    private static final String ENG_LANG = "eng";
    private Context mContext;
    private Bitmap mBitmap;

    private TessEngine(Context context){
        TessDataManager.initTessTrainedData(context);
        mContext = context;
    }

    public static TessEngine generate(Context context){
        return new TessEngine(context);
    }

    public String detectText(Bitmap bitmap){
        TessBaseAPI tessBaseApi = new TessBaseAPI();
        tessBaseApi.setDebug(true);
        tessBaseApi.init(TessDataManager.getDataPath(), ENG_LANG);
        tessBaseApi.setImage(bitmap);
        String result = tessBaseApi.getUTF8Text();
        tessBaseApi.end();
        return result;
    }
}
