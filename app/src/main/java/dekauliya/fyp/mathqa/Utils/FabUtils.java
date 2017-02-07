package dekauliya.fyp.mathqa.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 7/2/17.
 */
public class FabUtils {

    public static void setUpFab(Activity activity){
        Drawable searchIcon = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_search)
                .colorRes(R.color.material_color_white)
                .sizeDp(24);

        Drawable textIcon = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_text_format)
                .colorRes(R.color.material_color_white)
                .sizeDp(24);

        Drawable imgIcon =  new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_photo_camera)
                .colorRes(R.color.material_color_white)
                .sizeDp(24);

        Drawable formulaIcon = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_functions)
                .colorRes(R.color.material_color_white)
                .sizeDp(24);

        Drawable closeIcon = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_close)
                .colorRes(R.color.material_color_white)
                .sizeDp(24);

        FloatingActionMenu fabSearch = (FloatingActionMenu) activity.findViewById(R.id.fab_search);
        fabSearch.getMenuIconView().setImageDrawable(searchIcon);

        FloatingActionButton fabText = (FloatingActionButton) activity.findViewById(R.id
                .fab_textsearch);
        fabText.setImageDrawable(textIcon);

        FloatingActionButton fabImg = (FloatingActionButton) activity.findViewById(R.id
                .fab_imgsearch);
        fabImg.setImageDrawable(imgIcon);

        FloatingActionButton fabFormula = (FloatingActionButton) activity.findViewById(R.id
                .fab_formulasearch);
        fabFormula.setImageDrawable(formulaIcon);

        createCustomAnimation(fabSearch, searchIcon, closeIcon);
        fabSearch.setClosedOnTouchOutside(true);
    }

    private static void createCustomAnimation(final FloatingActionMenu menu, final Drawable open,
                                              final Drawable close) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.getMenuIconView().setImageDrawable(menu.isOpened() ? open : close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu.setIconToggleAnimatorSet(set);
    }
}
