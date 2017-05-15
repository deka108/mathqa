package dekauliya.fyp.mathqa.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import dekauliya.fyp.mathqa.MathQaInterface;
import dekauliya.fyp.mathqa.MathQaPrefs_;
import dekauliya.fyp.mathqa.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Activity for selecting MathQA subject level.
 */
@EActivity
public class SelectSubjectActivity extends AppCompatActivity{

    @Pref
    MathQaPrefs_ mathQaPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);
    }

    @Click({R.id.btn_subj_psle, R.id.btn_subj_elem, R.id.btn_subj_add, R.id.btn_subj_h2})
    void selectSubject(View clickedView){
        Button btn = (Button) clickedView;
        switch(btn.getId()){
            case R.id.btn_subj_psle:
                mathQaPrefs.subject_id().put(MathQaInterface.SUBJECT_PSLE);
                break;
            case R.id.btn_subj_elem:
                mathQaPrefs.subject_id().put(MathQaInterface.SUBJECT_ELEMENTARY);
                break;
            case R.id.btn_subj_add:
                mathQaPrefs.subject_id().put(MathQaInterface.SUBJECT_ADDITIONAL);
                break;
            case R.id.btn_subj_h2:
                mathQaPrefs.subject_id().put(MathQaInterface.SUBJECT_H2);
                break;
            default:
                break;
        }

        TopicConceptActivity_.intent(getApplicationContext()).flags(FLAG_ACTIVITY_NEW_TASK).start();
    }

}
