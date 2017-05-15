package dekauliya.fyp.mathqa.CameraOcr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataService;
import dekauliya.fyp.mathqa.IOnOcrOptionSetListener;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.ImagePickerUtils;
import dekauliya.fyp.mathqa.Utils.OcrUtils;
import dekauliya.fyp.mathqa.Utils.SearchDialogUtils;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import dekauliya.fyp.mathqa.Views.BaseActivity;

import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_TESSERACT;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_CATALANO;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_LEPTONICA;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_NOP;

/**
 *
 * Provides the image preview of the image source for OCR and main menus for performing editing,
 * image processing, performing OCR and searching using OCR results.
 *
 */
@EActivity(R.layout.activity_image_preview)
public class ImageOcrActivity extends BaseActivity implements IOnOcrProcessingListener, IOnOcrOptionSetListener {
    Activity activity = this;
    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.ip_image_preview) ImageView mImageView;
    @ViewById(R.id.ip_ocr_result) EditText mOcrResult;
    @ViewById(R.id.ip_alt_textview) TextView altView;
    @ViewById(R.id.btn_ocr_text_search)  Button searchBtn;
    @ViewById(R.id.btn_ocr_img_capture) Button imgCaptureBtn;
    @ViewById(R.id.btn_ocr_run_ocr)  Button ocrBtn;
    @ViewById(R.id.btn_ocr_img_edit) Button imgEditBtn;
    @ViewById(R.id.btn_ocr_run_preprocessing) Button preprocessBtn;
//    @ViewById(R.id.btn_ocr_text_post) Button postBtn;

    @Bean
    ImagePreprocessorBase mImagePreprocessor;
    TextRecogniserBase mTextRecogniser;
    PreprocessingOptions preprocessingOptions;

    @Extra("ocrOptionExtra")
    int ocrOption;

    @Extra("preprocessorOptionExtra")
    int preprocessorOption;

    @Extra("croppedImgUriExtra")
    Uri croppedImgUri;

    @Extra("originalImgUriExtra")
    Uri originalImgUri;

    @Bean
    ImagePickerUtils imagePicker;

    @Bean
    OcrUtils ocrUtils;

    @Bean
    SearchDialogUtils searchDialogUtils;

    OcrUtils.OcrOptions ocrOptions;

    long startOcrTime;
    long endOcrTime;

    boolean ocrMode = true;

    @Bean
    DataService dataService;

    @AfterViews
    void setUpViews(){

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textQuery = mOcrResult.getText().toString();
                searchDialogUtils.displaySearchDialog(textQuery);
            }
        });

        imgCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.pickImage(activity);
            }
        });

        imgEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (originalImgUri != null)  {
                    startCropActivity(originalImgUri);
                }else{
                    ocrUtils.alertDialog();
                }
            }
        });

        ocrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (croppedImgUri != null)  {
                    ocrMode=true;
                    ocrUtils.pickOcrEngine();
                }else{
                    ocrUtils.alertDialog();
                }
            }
        });

//        postBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataService.postText(mOcrResult.getText().toString());
//            }
//        });

        preprocessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (croppedImgUri != null)  {
                    ocrMode=false;
                    ocrUtils.pickPreprocessor();
                }else{
                    ocrUtils.alertDialog();
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updateViews();
    }

    public void updateViews(){
        if (croppedImgUri != null) {
            ViewUtils.hideView(altView);
            mImageView.setImageURI(croppedImgUri);
            mOcrResult.setText("");
        }
    }

    @Background(serial="ocr")
    public void runOcr() {
        try{
            initOcrOptions();
            startOcrTime = System.nanoTime();
            preprocessUriToBitmap();
        }catch(Exception e){
            ocrUtils.dismissDialog();
            Logger.e(e.getMessage());
            if(mImagePreprocessor instanceof ImagePreprocessorCatalano){
                Logger.d("Preprocessor instance of catalano");
            }else if (mImagePreprocessor instanceof ImagePreprocessorLeptonica){
                Logger.d("Preprocessor instance of leptonica");
            }
        }
    }

    @Override
    public void onOcrOptionSet(OcrUtils.OcrOptions ocrOptions) {
        this.ocrOptions = ocrOptions;
        if (ocrMode) {
            ocrUtils.showDialog("OCR Processing");
            runOcr();
        }else{
            initPreprocessor();
            ocrUtils.pickPreprocessorOptions(preprocessingOptions);
        }
    }

    @Override
    public void onPreprocessorOptionSet(OcrUtils.OcrOptions ocrOptions) {
        ocrUtils.showDialog("Image Pre-processing");
        preprocessUriToBitmap();
    }

    @Background(serial="ocr")
    public void initOcrOptions() {
        initPreprocessor();
        initOcrEngine();
    }

    private void initOcrEngine() {
        switch(ocrOptions.getOcrEngine()){
            case OCR_TESSERACT:
                Logger.d("ocr: tesseract");
                mTextRecogniser = new TextRecogniserTesseract(this);
                break;
            case OCR_GOOGLE_API:
                Logger.d("ocr: google text api");
                mTextRecogniser = new TextRecogniserTextApi(this);
                break;
        }
        if (mImagePreprocessor != null) {
            mTextRecogniser.setmImagePreprocessor(mImagePreprocessor);
        }
    }

    private void initPreprocessor() {
        switch(ocrOptions.getPreprocessor()){
            case PROCESSOR_CATALANO:
                Logger.d("preprocessor: catalano");
                mImagePreprocessor = new ImagePreprocessorCatalano(this);
                preprocessingOptions = new CatalanoOptions();
                break;
            case PROCESSOR_LEPTONICA:
                Logger.d("preprocessor: leptonica");
                mImagePreprocessor = new ImagePreprocessorLeptonica(this);
                preprocessingOptions = new LeptonicaOptions();
                break;
            case PROCESSOR_NOP:
                Logger.d("preprocessor: nop");
                mImagePreprocessor = new ImagePreprocessorNop(this);
                break;
        }
    }

    @Background(serial="ocr")
    public void preprocessUriToBitmap() {
        if (croppedImgUri != null && mImagePreprocessor != null){
            mImagePreprocessor.getBitmapFromUri(croppedImgUri);
        }else{
            ocrUtils.dismissDialog();
            Logger.d("No Image URI available!");
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        updateViews();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBitmapReady(Bitmap bitmap) {

        // Preprocess Bitmap
        if (ocrMode) {
            mTextRecogniser.preprocessBitmap(bitmap);
        }else{
            startOcrTime = System.nanoTime();
            mImagePreprocessor.customPreprocess(bitmap, preprocessingOptions);
        }

    }

    @UiThread
    @Override
    public void onBitmapPreprocessed(Bitmap preprocessedBmp) {
        // Display preprocessed Image
        mImageView.setImageBitmap(preprocessedBmp);
        if (ocrMode) {
            performOcr(preprocessedBmp);
        }else{
            ocrUtils.dismissDialog();
            endOcrTime = System.nanoTime();
            Logger.d(String.format("Time Elapsed: %1$f s", (endOcrTime-startOcrTime)/1000000000.0));
        }
    }

    @Background(serial="ocr")
    public void performOcr(Bitmap preprocessedBmp) {
        // Perform OCR
        mTextRecogniser.recogniseText(preprocessedBmp);
    }

    @Override
    public void onOcrProcessed(String result) {
        updateOcrResult(result);
    }

    @UiThread
    void updateOcrResult(String textResult) {
        endOcrTime = System.nanoTime();
        Logger.d(String.format("Time Elapsed: %1$f s", (endOcrTime-startOcrTime)/1000000000.0));
        Logger.d("OCR Result: " + textResult);

        mOcrResult.setText(textResult);
        ocrUtils.dismissDialog();

    }

}
