package dekauliya.fyp.mathqa.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 7/2/17.
 */
@EBean
public class FabUtils {
    @RootContext
    Activity activity;

    @Bean
    ImagePickerUtils imagePicker;

    @Bean
    SearchDialogUtils searchUtils;

    // FAB Related
    @ViewById(R.id.fab_search)
    FloatingActionMenu fabSearch;
    @ViewById(R.id.fab_textsearch)
    FloatingActionButton fabText;
    @ViewById(R.id.fab_imgsearch)
    FloatingActionButton fabImg;
    @ViewById(R.id.fab_formulasearch)
    FloatingActionButton fabFormula;

    Drawable searchIcon;
    Drawable textIcon;
    Drawable imgIcon;
    Drawable formulaIcon;
    Drawable closeIcon;

    @AfterViews
    public void setUpFab(){
        searchIcon = DrawableUtils.getDrawable(DrawableType.SEARCH, activity,
                R.color.material_color_white);
        textIcon = DrawableUtils.getDrawable(DrawableType.TEXT_SEARCH, activity, 16,
                R.color.material_color_white);
        imgIcon =  DrawableUtils.getDrawable(DrawableType.CAMERA_SEARCH, activity, 16,
                R.color.material_color_white);
        formulaIcon = DrawableUtils.getDrawable(DrawableType.FORMULA_SEARCH, activity, 16,
                R.color.material_color_white);
        closeIcon = DrawableUtils.getDrawable(DrawableType.CLOSE, activity,
                R.color.material_color_white);

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
                searchUtils.displaySearchDialog();
            }
        });

        fabFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSearch.close(true);
                searchUtils.displayFormulaInputPreview();
            }
        });

        fabImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSearch.close(true);
                imagePicker.pickImage(activity);
            }
        });
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
}
