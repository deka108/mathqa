package dekauliya.fyp.mathqa.ListViews;

import org.androidannotations.annotations.EFragment;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 6/2/17.
 */
@EFragment(R.layout.fragment_recyclerview_list)
public class QuestionSearchResultFragment extends AbstractListFragment{
    @Override
    public void onDataRetrieved() {

    }

    @Override
    public void onError() {

    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }
}
