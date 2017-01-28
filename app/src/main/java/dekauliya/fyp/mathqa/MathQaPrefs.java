package dekauliya.fyp.mathqa;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by dekauliya on 28/1/17.
 */

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface MathQaPrefs {
    int subject_id();
}
