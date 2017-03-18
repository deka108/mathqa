package dekauliya.fyp.mathqa.Utils;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by dekauliya on 18/3/17.
 */
@EBean
public class OcrUtils {
    @RootContext
    Context context;
    MaterialDialog dialog;

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
    }

    String[] ocrEngines = new String[]{"Google Text API", "Tesseract"};

    public void pickOcrEngine(){
        new MaterialDialog.Builder(context)
                .title("Choose an OCR Engine")
                .items(ocrEngines)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        if (which == 0){
                            Logger.d("Google TEXTAPI!!!");
                        }else{
                            Logger.d("Leptonica!!!");
                        }
                        return true;
                    }
                })
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
}
