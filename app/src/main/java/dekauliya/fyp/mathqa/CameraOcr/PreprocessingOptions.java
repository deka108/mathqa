package dekauliya.fyp.mathqa.CameraOcr;

import java.util.HashMap;

/**
 * Created by dekauliya on 19/3/17.
 */

public class PreprocessingOptions {
    String[] options;
    HashMap<String, Boolean> values;

    public PreprocessingOptions(){
        values = new HashMap();
    }

    public void initOptions(String[] optionArgs){
        options  = optionArgs;
        for(String option: options){
            values.put(option, false);
        }
    }

    public void setOption(String option, boolean newValue){
        values.put(option, newValue);
    }
}
