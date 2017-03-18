package dekauliya.fyp.mathqa.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import org.androidannotations.annotations.EActivity;

import dekauliya.fyp.mathqa.CameraOcr.ImageOcrActivity_;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.ViewUtils;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_TESSERACT;
import static dekauliya.fyp.mathqa.MathQaInterface.PROCESSOR_NOP;


@EActivity
public class BaseActivity extends AppCompatActivity {
    Uri originalImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK){
            originalImgUri = CropImage.getPickImageResultUri(this, data);
            startCropActivity(originalImgUri);
        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            if (resultCode == Activity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri croppedUri = result.getUri();
                if (originalImgUri != null) {
                    ImageOcrActivity_.intent(this)
                            .croppedImgUri(croppedUri)
                            .ocrOption(OCR_TESSERACT)
                            .originalImgUri(originalImgUri)
                            .preprocessorOption(PROCESSOR_NOP)
                            .flags(FLAG_ACTIVITY_SINGLE_TOP).start();
                }else{
                    ImageOcrActivity_.intent(this)
                            .croppedImgUri(croppedUri)
                            .ocrOption(OCR_GOOGLE_API)
                            .preprocessorOption(PROCESSOR_NOP)
                            .flags(FLAG_ACTIVITY_SINGLE_TOP).start();
                }


            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Snackbar.make(ViewUtils.getRootView(this), getString(R.string.unable_to_crop),
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    protected void startCropActivity(Uri imageUri){
        CropImage.activity(imageUri).start(this);
    }
}
