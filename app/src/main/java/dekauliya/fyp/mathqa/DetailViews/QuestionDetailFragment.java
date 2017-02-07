package dekauliya.fyp.mathqa.DetailViews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import io.github.kexanie.library.MathView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EFragment(R.layout.question_detail)
public class QuestionDetailFragment extends Fragment {
    private OnDetailFragmentInteractionListener mListener;

    @FragmentArg("questionArg")
    Question questionArg;

    @FragmentArg("conceptArg")
    Concept conceptArg;

    @FragmentArg("subconceptArg")
    SubConcept subconceptArg;

    @ViewById(R.id.qd_question_title)
    TextView questionTitle;

    @ViewById(R.id.qd_question_ratingbar)
    RatingBar questionRatingBar;

    @ViewById(R.id.qd_question_concept)
    TextView questionConcept;

    @ViewById(R.id.qd_question_latex)
    MathView questionContent;

    @AfterViews
    void setContent(){
        questionTitle.setText("Question #" + questionArg.getId());
        questionRatingBar.setNumStars(5);
        questionRatingBar.setRating(
                ViewUtils.getDifficultyLevelFloat(questionArg.getDifficulty_level()));

        if (subconceptArg == null && conceptArg == null){
            questionConcept.setVisibility(GONE);
        }else{
            questionConcept.setVisibility(VISIBLE);
            if (subconceptArg != null){
                questionConcept.setText(subconceptArg.getName());
            } else if (conceptArg != null){
                questionConcept.setText(conceptArg.getName());
            }
        }

        questionContent.setText(ViewUtils.convertLatex(questionArg.getContent()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
