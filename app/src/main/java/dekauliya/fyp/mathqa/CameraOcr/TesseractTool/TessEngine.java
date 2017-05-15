package dekauliya.fyp.mathqa.CameraOcr.TesseractTool;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import dekauliya.fyp.mathqa.CameraOcr.IOnOcrProcessingListener;

/**
 * Created by dekauliya on 25/9/16.
 *
 * Tesseract OCR Engine for initialising Tesseract, getting Tesseract instance and OCR processing.
 */
@EBean
public class TessEngine {
    private static final String TAG = TessEngine.class.getName();
    private static final String ENG_LANG = "eng";
    private TessBaseAPI mBaseApi;
    private int mOcrEngineMode = TessBaseAPI.OEM_TESSERACT_ONLY;
    private static TessEngine instance;
    private static Context mContext;
    private Bitmap mBitmap;

    TessEngine(Context context){
        mContext = context;
    }

    TessBaseAPI getBaseApi() {
        return mBaseApi;
    }

    public static TessEngine getInstance(Context context){
        if (instance == null){
            return new TessEngine(context);
        }
        mContext = context;
        return instance;
    }

    @Background
    public void initOcrEngine(){
        TessDataManager tdm = TessDataManager.getInstance(mContext);
        tdm.initTessTrainedData();
        mBaseApi = new TessBaseAPI();
        mBaseApi.init(TessDataManager.getDataPath(), ENG_LANG);
        mBaseApi.setDebug(true);
    }

    @Background
    public void detectText(Bitmap bitmap, IOnOcrProcessingListener listener){
        String result;
        if (mBaseApi == null){
            initOcrEngine();
        }
        mBaseApi.setImage(bitmap);
        result = mBaseApi.getUTF8Text();
        mBaseApi.clear();
        listener.onOcrProcessed(result);
    }
}
