package dekauliya.fyp.mathqa.DetailViews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataServiceRx;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.Solution;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import io.github.kexanie.library.MathView;

@EFragment(R.layout.solution_detail)
public class SolutionDetailFragment extends Fragment implements DataServiceRx.ISolutionListener{
    private OnDetailFragmentInteractionListener mListener;

    @FragmentArg("questionArg")
    Question questionArg;

    @Bean
    DataServiceRx dataServiceRx;

    @ViewById(R.id.sd_solution_title)
    TextView solutionTitle;

    @ViewById(R.id.sd_solution_latex)
    MathView solutionContent;

    @AfterViews
    void loadSolution(){
        dataServiceRx.getSolution(questionArg.getId(), this);
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

    @Override
    public void onSolutionRetrieved(Solution solution) {
        solutionTitle.setText("Solution to Q." + questionArg.getId());
        solutionContent.setText(ViewUtils.convertLatex(solution.getContent()));
    }
}
