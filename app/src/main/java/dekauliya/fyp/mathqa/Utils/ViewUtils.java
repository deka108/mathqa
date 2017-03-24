package dekauliya.fyp.mathqa.Utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.regex.Pattern;

import dekauliya.fyp.mathqa.R;
import io.github.kexanie.library.MathView;

import static android.view.View.GONE;

/**
 * Created by dekauliya on 6/2/17.
 */

public class ViewUtils {

    public static String convertLatex(String originalLatex){
//        String result = originalLatex.replaceAll(getRawString("\\["), getRawString("\\("));
//        result = result.replaceAll(getRawString("\\]"), getRawString("\\)"));
//        result = result.replaceAll(";", " <br/> ");
        String result = originalLatex;
        Logger.d("Final result: " + result);
        return result;
    }

    public static String getRawString(String inputStr){
        return Pattern.quote(inputStr);
    }

    public static void displayLatex(MathView latexView, TextView altView, String latexStr,
                                    boolean addDelimiter){
        try {
            if (latexStr.length() > 0) {
                if (addDelimiter) {
                    latexView.setText(convertLatex(getLatex(latexStr)));
                }else{
                    latexView.setText(convertLatex(latexStr));
                }
                latexView.setVisibility(View.VISIBLE);
                altView.setVisibility(GONE);
            }else{
                altView.setText(altView.getContext().getString(R.string.str_latex_alt_preview));
                altView.setVisibility(View.VISIBLE);
                latexView.setVisibility(GONE);
            }
        }catch(Exception e){
            Logger.e(e.getMessage());
            altView.setText(String.format(
                    altView.getContext().getString(R.string.error_latex_rendering), latexStr));
            altView.setVisibility(View.VISIBLE);
            latexView.setVisibility(GONE);
        }
    }

    public static void toggleLayout(LinearLayout visibleContainer, LinearLayout invisibleContainer){
        visibleContainer.setVisibility(View.VISIBLE);
        invisibleContainer.setVisibility(GONE);
    }

    public static void hideView(View view){
        view.setVisibility(GONE);
    }

    public static float getDifficultyLevelFloat(String diffLevel){
        return Float.parseFloat(diffLevel) / 2;
    }

    public static String getKeyPointTitle(String title) {
        return title.replaceAll("_", " ");
    }


    public static ViewGroup getRootView(Activity activity){
        return (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static String getLatex(String originalStr){
        return "$$" + originalStr + "$$";
    }

    public static Typeface getTypeface(TypefaceStyle type){
        switch (type) {
            case MEDIUM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    return Typeface.create("sans-serif-medium", Typeface.NORMAL);
                else
                    return Typeface.create("sans-serif", Typeface.BOLD);
            case REGULAR:
                return Typeface.create("sans-serif", Typeface.NORMAL);
        }
        return null;
    }
}
