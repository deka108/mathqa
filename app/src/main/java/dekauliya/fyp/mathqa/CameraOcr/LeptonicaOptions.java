package dekauliya.fyp.mathqa.CameraOcr;

/**
 * Created by dekauliya on 18/3/17.
 */

public class LeptonicaOptions extends PreprocessingOptions{
    String[] leptonicaOptions;

    public LeptonicaOptions(String[] options) {
        super();
        leptonicaOptions = options;
        super.initOptions(leptonicaOptions);
    }
}
