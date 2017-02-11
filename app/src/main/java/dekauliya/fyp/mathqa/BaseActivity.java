package dekauliya.fyp.mathqa;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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

import java.util.List;

import dekauliya.fyp.mathqa.SearchViews.FormulaHelper;
import dekauliya.fyp.mathqa.SearchViews.FormulaType;
import dekauliya.fyp.mathqa.SearchViews.SearchImageActivity_;
import dekauliya.fyp.mathqa.Utils.DrawableType;
import dekauliya.fyp.mathqa.Utils.DrawableUtils;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import io.github.kexanie.library.MathView;

import static dekauliya.fyp.mathqa.MathQaInterface.CAPTURED_IMAGE_URI;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_GOOGLE_API;
import static dekauliya.fyp.mathqa.MathQaInterface.OCR_OPTION;

public class BaseActivity extends AppCompatActivity {
    private Activity activity;

    // Formula Related
    private List<String> formulaStrs;
    private List<String> formulas;
    private FormulaType formulaType;
    private static FormulaType[] formulaTypes = FormulaType.values();
    LinearLayout fsLLDialog;
    MathView fsMathPreview;
    EditText fsEditText;
    TextView fsPreviewPlaceholder;

    // Permission Related
    private MultiplePermissionsListener mMultiplePermissionsListener;
    private MultiplePermissionsListener mSnackbarOnDeniedMultiplePermissionsListener;
    private CompositeMultiplePermissionsListener mAllListeners;
    private PermissionRequestErrorListener mErrorListener;

    // FAB Related
    FloatingActionMenu fabSearch;
    FloatingActionButton fabText;
    FloatingActionButton fabImg;
    FloatingActionButton fabFormula;

    Drawable searchIcon;
    Drawable textIcon;
    Drawable imgIcon;
    Drawable formulaIcon;
    Drawable closeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setUpFab();
    }

    public void setUpFab(){
        searchIcon = DrawableUtils.getDrawable(DrawableType.SEARCH, this,
                R.color.material_color_white);
        textIcon = DrawableUtils.getDrawable(DrawableType.TEXT_SEARCH, this, 16,
                R.color.material_color_white);
        imgIcon =  DrawableUtils.getDrawable(DrawableType.CAMERA_SEARCH, this, 16,
                R.color.material_color_white);
        formulaIcon = DrawableUtils.getDrawable(DrawableType.FORMULA_SEARCH, this, 16,
                R.color.material_color_white);
        closeIcon = DrawableUtils.getDrawable(DrawableType.CLOSE, this,
                R.color.material_color_white);

        fabSearch = (FloatingActionMenu) this.findViewById(R.id.fab_search);
        fabText = (FloatingActionButton) this.findViewById(R.id
                .fab_textsearch);
        fabImg = (FloatingActionButton) this.findViewById(R.id
                .fab_imgsearch);
        fabFormula = (FloatingActionButton) this.findViewById(R.id
                .fab_formulasearch);

        fabSearch.getMenuIconView().setImageDrawable(searchIcon);
        fabText.setImageDrawable(textIcon);
        fabImg.setImageDrawable(imgIcon);
        fabFormula.setImageDrawable(formulaIcon);

        animateSearchFabIcon();
        fabSearch.setClosedOnTouchOutside(true);

        fabText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSearch.close(true);
            }
        });

        fabFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSearch.close(true);
                displayFormulaInputPreview();
            }
        });

        fabImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSearch.close(true);
                pickImage();
            }
        });
    }

    private void displayFormulaInputPreview() {
        final MaterialDialog formulaPreviewDialog = new MaterialDialog.Builder(this)
                .title("Formula Search")
                .titleColorRes(R.color.material_color_amber_500)
                .customView(R.layout.fragment_formula_input_preview, true)
                .neutralText("Insert Formula")
                .positiveText("Search")
                .negativeText("Cancel")
                .autoDismiss(false)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        displayFormulaOptions();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();

        View formulaView = formulaPreviewDialog.getCustomView();
        fsLLDialog = (LinearLayout) formulaView;

        for(int i = 0; i< fsLLDialog.getChildCount(); i++){
            View childView = fsLLDialog.getChildAt(i);
            if ( childView.getId() == R.id.fs_latex_preview ){
                fsMathPreview = (MathView) childView;
            }else if ( childView.getId() == R.id.fs_latex_input){
                fsEditText = (EditText) childView;
            }else if (childView.getId() == R.id.fs_preview_placeholder){
                fsPreviewPlaceholder = (TextView) childView;
            }
        }

        fsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String etContent = editable.toString();
                if (etContent.length() > 0 ){
                    fsPreviewPlaceholder.setVisibility(View.GONE);
                    fsMathPreview.setText(ViewUtils.getLatex(etContent));
                }else{
                    fsPreviewPlaceholder.setVisibility(View.VISIBLE);
                }
            }
        });

        formulaStrs = FormulaHelper.getFormulaStrArray(FormulaType.ALL, this);
        formulas = FormulaHelper.getFormulaArray(FormulaType.ALL, this);
        formulaPreviewDialog.show();
    }

    private void displayFormulaOptions() {
        new MaterialDialog.Builder(this)
                .title("Select Formula Category")
                .items(R.array.formula_title)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        formulaType = formulaTypes[which];
                        formulaStrs = FormulaHelper.getFormulaStrArray(formulaType, activity);
                        formulas = FormulaHelper.getFormulaArray(formulaType, activity);
                        displayFormulaList();
                    }
                }).show();
    }

    private void displayFormulaList() {
        new MaterialDialog.Builder(this)
                .title("Select Formula")
                .items(formulaStrs)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which,
                                            CharSequence text) {
                        if (formulas != null && which >= 0 && which < formulas.size()) {
                            int cursorPos = fsEditText.getSelectionEnd();
                            StringBuilder editTextStr = new StringBuilder(fsEditText.getText());
                            editTextStr.insert(cursorPos, formulas.get(which));
                            fsEditText.setText(editTextStr.toString());
                            fsEditText.setSelection(editTextStr.length());
                        }
                    }
                }).show();
    }

    private void animateSearchFabIcon() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabSearch.getMenuIconView(), "scaleX",
                1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabSearch.getMenuIconView(), "scaleY",
                1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabSearch.getMenuIconView(), "scaleX",
                0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabSearch.getMenuIconView(), "scaleY",
                0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fabSearch.getMenuIconView().setImageDrawable(fabSearch.isOpened() ? searchIcon :
                        closeIcon);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabSearch.setIconToggleAnimatorSet(set);
    }

    public void pickImage(){
        if (Build.VERSION.SDK_INT > 22){

            if (mAllListeners == null){
                initPermissionListeners();
            }

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
            CropImage.startPickImageActivity(activity);
        }
    }

    public void initPermissionListeners(){
        mMultiplePermissionsListener = new
                MultiplePermissionsListener() {
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
                        .with(ViewUtils.getRootView(activity),
                                activity.getString(R.string.camera_storage_access))
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
                Intent intent = new Intent(this, SearchImageActivity_.class);
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
