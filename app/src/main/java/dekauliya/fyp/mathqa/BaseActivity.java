package dekauliya.fyp.mathqa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import dekauliya.fyp.mathqa.CameraOcr.ImageOcrActivity_;
import dekauliya.fyp.mathqa.Utils.ViewUtils;

import static dekauliya.fyp.mathqa.MathQaInterface.CAPTURED_IMAGE_URI;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_OPTION;


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imageUri).start(this);
        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            if (resultCode == Activity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                Intent intent = new Intent(this, ImageOcrActivity_.class);
                intent.putExtra(CAPTURED_IMAGE_URI, resultUri);
                intent.putExtra(OCR_OPTION, OCR_GOOGLE_API);
                startActivity(intent);

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Snackbar.make(ViewUtils.getRootView(this), getString(R.string.unable_to_crop),
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
