package dekauliya.fyp.mathqa.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataServiceRx;
import dekauliya.fyp.mathqa.R;
import eu.davidea.fastscroller.FastScroller;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@EFragment(R.layout.fragment_recyclerview_list)
public class FormulaConceptFragment extends AbstractListFragment {

    @ViewById(R.id.fragment_rv)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fast_scroller)
    FastScroller mFastScroller;

    @ViewById(R.id.fragment_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bean
    DataServiceRx dataServiceRx;


    @Override
    public void onDataRetrieved() {

    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }
}
