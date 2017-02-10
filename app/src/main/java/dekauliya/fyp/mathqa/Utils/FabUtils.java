package dekauliya.fyp.mathqa.Utils;

import org.androidannotations.annotations.EBean;

/**
 * Created by dekauliya on 7/2/17.
 */
@EBean
public class FabUtils {

//    public static void setUpFab(final Activity activity){
//        Drawable searchIcon = DrawableUtils.getDrawable(DrawableType.SEARCH, activity,
//                R.color.material_color_white);
//        Drawable textIcon = DrawableUtils.getDrawable(DrawableType.TEXT_SEARCH, activity, 16,
//                R.color.material_color_white);
//        Drawable imgIcon =  DrawableUtils.getDrawable(DrawableType.CAMERA_SEARCH, activity, 16,
//                R.color.material_color_white);
//        Drawable formulaIcon = DrawableUtils.getDrawable(DrawableType.FORMULA_SEARCH, activity, 16,
//                R.color.material_color_white);
//        Drawable closeIcon = DrawableUtils.getDrawable(DrawableType.CLOSE, activity,
//                R.color.material_color_white);
//
//        FloatingActionMenu fabSearch = (FloatingActionMenu) activity.findViewById(R.id.fab_search);
//        FloatingActionButton fabText = (FloatingActionButton) activity.findViewById(R.id
//                .fab_textsearch);
//        FloatingActionButton fabImg = (FloatingActionButton) activity.findViewById(R.id
//                .fab_imgsearch);
//        FloatingActionButton fabFormula = (FloatingActionButton) activity.findViewById(R.id
//                .fab_formulasearch);
//
//        fabSearch.getMenuIconView().setImageDrawable(searchIcon);
//        fabText.setImageDrawable(textIcon);
//        fabImg.setImageDrawable(imgIcon);
//        fabFormula.setImageDrawable(formulaIcon);
//
//        createCustomAnimation(fabSearch, searchIcon, closeIcon);
//        fabSearch.setClosedOnTouchOutside(true);
//
//        fabText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        fabFormula.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        fabImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePickerUtils.pickImage(activity);
//            }
//        });
//
//    }
//
//    private static void createCustomAnimation(final FloatingActionMenu menu, final Drawable open,
//                                              final Drawable close) {
//        AnimatorSet set = new AnimatorSet();
//
//        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
//        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);
//
//        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
//        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);
//
//        scaleOutX.setDuration(50);
//        scaleOutY.setDuration(50);
//
//        scaleInX.setDuration(150);
//        scaleInY.setDuration(150);
//
//        scaleInX.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                menu.getMenuIconView().setImageDrawable(menu.isOpened() ? open : close);
//            }
//        });
//
//        set.play(scaleOutX).with(scaleOutY);
//        set.play(scaleInX).with(scaleInY).after(scaleOutX);
//        set.setInterpolator(new OvershootInterpolator(2));
//
//        menu.setIconToggleAnimatorSet(set);
//    }
}
