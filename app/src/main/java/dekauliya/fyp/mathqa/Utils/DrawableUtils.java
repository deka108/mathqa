package dekauliya.fyp.mathqa.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 7/2/17.
 */

public class DrawableUtils {

    public static Drawable getDrawable(DrawableType type, Activity activity, int size, @ColorRes
                                       int colorRes){
        switch(type){
            case SEARCH:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_search)
                    .colorRes(colorRes).sizeDp(size);
            case CAMERA_SEARCH:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_photo_camera)
                    .colorRes(colorRes).sizeDp(size);
            case TEXT_SEARCH:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_text_format)
                        .colorRes(colorRes).sizeDp(size);
            case FORMULA_SEARCH:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_functions)
                        .colorRes(colorRes).sizeDp(size);
            case CLOSE:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_close)
                        .colorRes(colorRes).sizeDp(size);
            case NO_DATA:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_folder_open)
                        .colorRes(colorRes).sizeDp(size);
            case ERROR_OUTLINE:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_error_outline)
                        .colorRes(colorRes).sizeDp(size);
            case NO_WIFI:
                return new IconicsDrawable(activity).icon(GoogleMaterial.Icon.gmd_signal_wifi_off)
                        .colorRes(colorRes).sizeDp(size);
            default:
                return null;
        }
    }

    public static Drawable getDrawable(DrawableType type, Activity activity){
        return getDrawable(type, activity, 24, R.color.material_color_white);
    }

    public static Drawable getDrawable(DrawableType type, Activity activity, @ColorRes int
            colorRes){
        return getDrawable(type, activity, 24, colorRes);
    }
}
