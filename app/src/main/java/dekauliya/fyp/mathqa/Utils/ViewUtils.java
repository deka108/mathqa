package dekauliya.fyp.mathqa.Utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import dekauliya.fyp.mathqa.R;
import io.github.kexanie.library.MathView;

/**
 * Created by dekauliya on 6/2/17.
 */

public class ViewUtils {

    public static String convertLatex(String originalLatex){
        String result = originalLatex.replaceAll("\\[", "\\(");
        result = result.replaceAll("\\]", "\\)");
        result = result.replace(";", " <br/><br/> ");
        return result;
    }

    public static void displayLatex(MathView latexView, TextView altView, String latexStr){
        try {
            latexView.setText(convertLatex(latexStr));
            latexView.setVisibility(View.VISIBLE);
            altView.setVisibility(View.GONE);
        }catch(Exception e){
            Logger.e(e.getMessage());
            altView.setText(String.format(
                    altView.getContext().getString(R.string.error_latex_rendering), latexStr));
            altView.setVisibility(View.VISIBLE);
            latexView.setVisibility(View.GONE);
        }
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
}
