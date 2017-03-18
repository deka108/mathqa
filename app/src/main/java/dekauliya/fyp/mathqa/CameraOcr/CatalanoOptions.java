package dekauliya.fyp.mathqa.CameraOcr;

/**
 * Created by dekauliya on 18/3/17.
 */

public class CatalanoOptions extends PreprocessingOptions {
    String[] catalanoOptions = new String[]{};

    public CatalanoOptions(String[] optionArgs) {
        super();
        catalanoOptions = optionArgs;
        super.initOptions(catalanoOptions);
    }
}
