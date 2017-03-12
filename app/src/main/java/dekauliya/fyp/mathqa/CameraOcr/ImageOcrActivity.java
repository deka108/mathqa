package dekauliya.fyp.mathqa.CameraOcr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.SearchViews.SearchActivity_;
import dekauliya.fyp.mathqa.SearchViews.SearchType;
import dekauliya.fyp.mathqa.Utils.ImagePickerUtils;

import static dekauliya.fyp.mathqa.MathQaInterface.CAPTURED_IMAGE_URI;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_OPTION;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_TESSERACT;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_CATALANO;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_LEPTONICA;


@EActivity(R.layout.activity_image_preview)
public class ImageOcrActivity extends AppCompatActivity implements IOnOcrProcessingListener {
    Activity activity = this;
    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.ip_image_preview) ImageView mImageView;
    @ViewById(R.id.ip_ocr_result)
    MaterialEditText mOcrResult;
    @ViewById(R.id.btn_ocr_text_search)  Button searchBtn;
    @ViewById(R.id.btn_ocr_img_capture) Button imgCaptureBtn;

    @Bean
    ImagePreprocessorBase mImagePreprocessor;
    TextRecogniserAbstract mTextRecogniser;

    @Bean
    ImagePickerUtils imagePicker;

    int ocrOption;
    int preprocessorOption = PROCESSOR_LEPTONICA;

    ProgressDialog mProgressDialog;

    @AfterViews
    void setUpViews(){

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textQuery = mOcrResult.getText().toString();
                SearchActivity_.intent(activity)
                        .searchTypeExtra(SearchType.FULL_TEXT)
                        .searchQuery(textQuery)
                        .start();
            }
        });

        imgCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.pickImage(activity);
            }
        });

        mProgressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(CAPTURED_IMAGE_URI);
        ocrOption = intent.getIntExtra(OCR_OPTION, OCR_TESSERACT);

        initOptions();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try{
            if (uri != null && mImagePreprocessor != null){
                mImagePreprocessor.getBitmapFromUri(uri);
            }
        }catch(Exception e){
            Logger.e(e.getMessage());
            if(mImagePreprocessor instanceof ImagePreprocessorCatalano){
                Logger.d("Preprocessor instance of catalano");
            }else if (mImagePreprocessor instanceof ImagePreprocessorLeptonica){
                Logger.d("Preprocessor instance of Leptonica");
            }
        }
    }

    private void initOptions() {
        switch(preprocessorOption){
            case PROCESSOR_CATALANO:
                mImagePreprocessor = new ImagePreprocessorCatalano(this);
                break;
            case PROCESSOR_LEPTONICA:
                mImagePreprocessor = new ImagePreprocessorLeptonica(this);
                break;
        }

        switch(ocrOption){
            case OCR_TESSERACT:
                mTextRecogniser = new TextRecogniserTesseract(this);
                break;
            case OCR_GOOGLE_API:
                mTextRecogniser = new TextRecogniserTextApi(this);
                break;
        }

        mTextRecogniser.setmImagePreprocessor(mImagePreprocessor);
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

    @UiThread
    @Override
    public void onBitmapReady(Bitmap bitmap) {
        // Display progress dialog
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(getString(R.string.ocr_preprocessing));
            mProgressDialog.show();
        }

        // Preprocess Bitmap
        mTextRecogniser.preprocessImage(bitmap);
    }

    @UiThread
    @Override
    public void onImagePreprocessed(Bitmap preprocessedBmp) {
        // Display preprocessed Image
        mImageView.setImageBitmap(preprocessedBmp);
        // Perform OCR
        mTextRecogniser.recogniseText(preprocessedBmp);
    }

    @Override
    public void onOcrProcessed(String result) {
        updateOcrResult(result);
    }

    @UiThread
    void updateOcrResult(String textResult) {
        Logger.d("OCR Result: " + textResult);

        mOcrResult.setText(textResult);

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
