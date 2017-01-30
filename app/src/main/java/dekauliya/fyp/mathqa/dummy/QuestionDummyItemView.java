package dekauliya.fyp.mathqa.dummy;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Models.QuestionContent;
import dekauliya.fyp.mathqa.R;
import io.github.kexanie.library.MathView;

/**
 * Created by dekauliya on 23/1/17.
 */

@EViewGroup(R.layout.item_question)
public class QuestionDummyItemView extends LinearLayout {

    @ViewById(R.id.question_no) TextView questionNo;
    @ViewById(R.id.question_content)
    MathView questionContent;
//    @ViewById(R.id.question_content) TextView questionContent;
    @ViewById(R.id.question_foot) TextView questionFoot;

    public QuestionDummyItemView(Context mContext){
        super(mContext);
    }

    public void bind(QuestionContent qc){
        questionContent.setText(qc.content);
        questionContent.setFocusable(false);
        questionContent.setFocusableInTouchMode(false);
        questionNo.setText("QuestionTest No. " + qc.questionNo);
        questionFoot.setText("Difficulty Level: " + qc.difficulty);
    }
}
