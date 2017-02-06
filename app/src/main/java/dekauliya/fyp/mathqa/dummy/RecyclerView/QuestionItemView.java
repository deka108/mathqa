package dekauliya.fyp.mathqa.dummy.RecyclerView;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.R;
import io.github.kexanie.library.MathView;

/**
 * Created by dekauliya on 4/2/17.
 */
@EViewGroup(R.layout.question_item)
public class QuestionItemView extends LinearLayout {
    @ViewById(R.id.question_title) TextView questionTitle;
    @ViewById(R.id.question_ratingbar) RatingBar questionDifficulty;
    @ViewById(R.id.question_latex) MathView questionContent;
    @ViewById(R.id.question_concept) TextView questionConcept;

    public QuestionItemView(Context context) {
        super(context);
    }

    public void bind(Question q) {
        if (q != null) {
            questionTitle.setText("Question #" + q.getId());

//            questionDifficulty.setRating(Float.parseFloat(q.getDifficulty_level()));
            questionDifficulty.setRating(Float.parseFloat(q.getDifficulty_level())/2);
            Logger.d("Difficulty level: " + q.getDifficulty_level());
            questionContent.setText(q.getContent());
            questionContent.setVerticalScrollBarEnabled(false);
        }
//        questionConcept.setText(q.getConcept());
    }
}
