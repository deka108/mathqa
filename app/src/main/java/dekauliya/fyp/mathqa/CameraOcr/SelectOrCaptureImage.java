package dekauliya.fyp.mathqa.CameraOcr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.orhanobut.logger.Logger;
import com.theartofdev.edmodo.cropper.CropImage;

import org.androidannotations.annotations.EActivity;

import java.util.List;

import dekauliya.fyp.mathqa.R;

import static dekauliya.fyp.mathqa.MathQaInterface.CAPTURED_IMAGE_URI;

@EActivity(R.layout.activity_select_or_capture_image)
public class SelectOrCaptureImage extends AppCompatActivity {

    ViewGroup rootView =
            (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

    MultiplePermissionsListener mMultiplePermissionsListener;
    MultiplePermissionsListener mSnackbarOnDeniedMultiplePermissionsListener;
    CompositeMultiplePermissionsListener mAllListeners;
    PermissionRequestErrorListener mErrorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeListeners(this);
        accessFileOrCamera();
        Dexter.withActivity(this).continueRequestingPendingPermissions(
                mMultiplePermissionsListener);

    }

    void accessFileOrCamera(){
        if (Build.VERSION.SDK_INT > 22){
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(mAllListeners)
                    .withErrorListener(mErrorListener)
                    .check();
        }else{
            // no need to check for permission for SDK < 22
            CropImage.startPickImageActivity(this);
        }
    }

    void initializeListeners(final Activity activity){
        mMultiplePermissionsListener = new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    CropImage.startPickImageActivity(activity);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                           PermissionToken token) {
                token.continuePermissionRequest();
            }
        };

        mSnackbarOnDeniedMultiplePermissionsListener =
        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                .with(rootView, getString(R.string.camera_storage_access))
                .withOpenSettingsButton(R.string.settings_str)
                .build();

        mErrorListener = new PermissionRequestErrorListener() {
            @Override public void onError(DexterError error) {
                Logger.e("Dexter. There was an error: " + error.toString());
            }
        };

        mAllListeners = new CompositeMultiplePermissionsListener(mMultiplePermissionsListener,
                mSnackbarOnDeniedMultiplePermissionsListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imageUri).start(this);
        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            if (resultCode == Activity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                Intent intent = new Intent(this, ImagePreviewActivity_.class);
                intent.putExtra(CAPTURED_IMAGE_URI, resultUri);
                startActivity(intent);

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Snackbar.make(rootView, "Unable to crop the image.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
