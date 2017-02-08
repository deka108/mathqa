package dekauliya.fyp.mathqa.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vlonjatg.progressactivity.ProgressActivity;

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

    public static void showErrorPage(ProgressActivity progressActivity, final Activity activity) {
        progressActivity.showError(
                DrawableUtils.getDrawable(DrawableType.NO_WIFI, activity,  R.color.colorAccent),
                activity.getString(R.string.no_internet), activity.getString(R.string
                        .no_internet_desc),
                activity.getString(R.string.error_try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(activity, "Try again button clicked", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void showEmptyPage(ProgressActivity progressActivity, Activity activity, String
            emptyTitle){
        progressActivity.showEmpty(
                DrawableUtils.getDrawable(DrawableType.NO_DATA, activity, R.color.colorAccent),
                emptyTitle, "");
    }
}
