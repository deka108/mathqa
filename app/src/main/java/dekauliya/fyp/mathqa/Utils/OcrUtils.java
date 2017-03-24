package dekauliya.fyp.mathqa.Utils;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.HashMap;

import dekauliya.fyp.mathqa.CameraOcr.PreprocessingOptions;
import dekauliya.fyp.mathqa.IOnOcrOptionSetListener;

import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_TESSERACT;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_CATALANO;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_LEPTONICA;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_NOP;

/**
 * Created by dekauliya on 18/3/17.
 */
@EBean
public class OcrUtils {
    @RootContext
    Context context;
    MaterialDialog dialog;
    IOnOcrOptionSetListener mListener;

    OcrOptions ocrOptions;

    @AfterInject
    public void updateDialog(){
         dialog = new MaterialDialog.Builder(context)
                 .autoDismiss(false)
                 .cancelable(false)
                 .canceledOnTouchOutside(false)
                 .content("Please wait..")
                 .progress(true, 0)
                 .progressIndeterminateStyle(true)
                 .build();

        mListener = (IOnOcrOptionSetListener) context;

        ocrOptions = new OcrOptions();
        ocrEngines.put("Tesseract", OCR_TESSERACT);
        ocrEngines.put("Google Text API", OCR_GOOGLE_API);

        preprocessors.put("NOP", PROCESSOR_NOP);
        preprocessors.put("Catalano", PROCESSOR_CATALANO);
        preprocessors.put("Leptonica", PROCESSOR_LEPTONICA);
    }

    HashMap<String, Integer> ocrEngines = new HashMap<>();
    HashMap<String, Integer> preprocessors = new HashMap<>();

    public void pickOcrEngine(){
        new MaterialDialog.Builder(context)
                .title("Choose an OCR Engine")
                .items(ocrEngines.keySet())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        ocrOptions.ocrEngine = ocrEngines.get(text);
                        pickPreprocessor();
                    }
                })
                .show();
    }

    public void alertDialog(){
        new MaterialDialog.Builder(context)
                .content("NO IMAGE URI available!")
                .positiveText("OK")
                .show();
    }

    public void pickPreprocessor(){
        new MaterialDialog.Builder(context)
                .title("Choose a Preprocessor")
                .items(preprocessors.keySet())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        ocrOptions.preprocessor = preprocessors.get(text);
                        mListener.onOcrOptionSet(ocrOptions);
                    }
                })
                .show();

    }

    public void pickPreprocessorOptions(final PreprocessingOptions opts){
        new MaterialDialog.Builder(context)
                .title("Choose preprocessing options")
                .items(opts.getOptions())
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice(){

                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        for(CharSequence chosenOption: text){
                            opts.setOption((String) chosenOption, true);
                        }
                        mListener.onPreprocessorOptionSet(ocrOptions);
                        return true;
                    }
                })
                .positiveText("Choose")
                .show();

    }

    public void showDialog(String text){
        dismissDialog();
        dialog.setTitle(text);
        dialog.show();
    }

    public void dismissDialog(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public class OcrOptions{
        int ocrEngine;
        int preprocessor;

        public int getPreprocessor(){
            return this.preprocessor;
        }

        public int getOcrEngine(){
            return this.ocrEngine;
        }
    }
}
