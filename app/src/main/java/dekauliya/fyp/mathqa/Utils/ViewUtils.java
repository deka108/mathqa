package dekauliya.fyp.mathqa.Utils;

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

    public static float getDifficultyLevelFloat(String diffLevel){
        return Float.parseFloat(diffLevel) / 2;
    }
}
