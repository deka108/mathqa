package dekauliya.fyp.mathqa.Utils;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

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

import org.androidannotations.annotations.EBean;

import java.util.List;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 10/2/17.
 *
 * Utilities for picking image from file system or camera, including asking for permissions for
 * Android version >= 22.
 */
@EBean
public class ImagePickerUtils {
    Activity mActivity;

    // Permission Related
    private MultiplePermissionsListener mMultiplePermissionsListener;
    private MultiplePermissionsListener mSnackbarOnDeniedMultiplePermissionsListener;
    private CompositeMultiplePermissionsListener mAllListeners;
    private PermissionRequestErrorListener mErrorListener;

    public void pickImage(Activity activity){
        if (Build.VERSION.SDK_INT > 22){
            this.mActivity = activity;
            if (mAllListeners == null){
                initPermissionListeners();
            }

            Dexter.withActivity(activity)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(mAllListeners)
                    .withErrorListener(mErrorListener)
                    .check();
        }else{
            // no need to check for permission for SDK < 22
            CropImage.startPickImageActivity(activity);
        }
    }

    public void initPermissionListeners(){
        mMultiplePermissionsListener = new
                MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            CropImage.startPickImageActivity(mActivity);
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
                        .with(ViewUtils.getRootView(mActivity),
                                mActivity.getString(R.string.camera_storage_access))
                        .withOpenSettingsButton(R.string.settings_str)
                        .build();

        mErrorListener = new PermissionRequestErrorListener() {
            @Override public void onError(DexterError error) {
                Logger.e("Dexter. There was an error: " + error.toString());
            }
        };

        mAllListeners = new
                CompositeMultiplePermissionsListener(mMultiplePermissionsListener,
                mSnackbarOnDeniedMultiplePermissionsListener);
    }
}
