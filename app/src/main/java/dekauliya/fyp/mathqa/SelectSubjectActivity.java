package dekauliya.fyp.mathqa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class SelectSubjectActivity extends AppCompatActivity {

    @Pref
    MathQaPrefs_ mathQaPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Click({R.id.btn_subj_psle, R.id.btn_subj_elem, R.id.btn_subj_add, R.id.btn_subj_h2})
    void selectSubject(View clickedView){
        Button btn = (Button) clickedView;
        switch(btn.getId()){
            case R.id.btn_subj_psle:
                mathQaPrefs.subject_id().put(MathQaConstants.SUBJECT_PSLE);
                break;
            case R.id.btn_subj_elem:
                mathQaPrefs.subject_id().put(MathQaConstants.SUBJECT_ELEMENTARY);
                break;
            case R.id.btn_subj_add:
                mathQaPrefs.subject_id().put(MathQaConstants.SUBJECT_ADDITIONAL);
                break;
            case R.id.btn_subj_h2:
                mathQaPrefs.subject_id().put(MathQaConstants.SUBJECT_H2);
                break;
            default:
                break;
        }

        Logger.d("BUTTON CLICKED + " + btn.getText());
        MainActivity_.intent(getApplicationContext()).start();
    }

}