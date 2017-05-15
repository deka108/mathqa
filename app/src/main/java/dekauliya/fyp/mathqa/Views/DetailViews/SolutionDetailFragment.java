package dekauliya.fyp.mathqa.Views.DetailViews;

import android.content.Context;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Views.BaseFragment;
import dekauliya.fyp.mathqa.DataServices.DataService;
import dekauliya.fyp.mathqa.DataServices.IDataListener;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.Solution;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import io.github.kexanie.library.MathView;

/**
 * Fragment for displaying a full mathematical solution to the Question which uses LaTeX for the
 * mathematical content.
 */
@EFragment(R.layout.fragment_solution_detail)
public class SolutionDetailFragment extends BaseFragment implements IDataListener{
    private OnDetailFragmentInteractionListener mListener;

    @FragmentArg("questionArg")
    Question questionArg;

    @Bean
    DataService dataService;

    @ViewById(R.id.sd_solution_title)
    TextView solutionTitle;

    @ViewById(R.id.sd_solution_latex)
    MathView solutionContent;

    @ViewById(R.id.sd_solution_latex_alt)
    TextView solutionContentAlt;

    @AfterViews
    void loadSolution(){
        progressActivity.showLoading();
        dataService.getSolution(questionArg.getId(), this);
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
    public void onDataRetrieved() {
        solutionTitle.setText(String.format(getString(R.string.sd_solution_title),
                questionArg.getId()));
        Solution solution = dataService.getSolution();
        if (solution != null) {
            ViewUtils.displayLatex(solutionContent, solutionContentAlt, solution.getContent(), false);
            progressActivity.showContent();
        }else{
            showEmptyPage(getString(R.string.empty_solution_for_question));
        }
    }

    @Override
    public void onError() {
        showErrorPage();
    }
}
