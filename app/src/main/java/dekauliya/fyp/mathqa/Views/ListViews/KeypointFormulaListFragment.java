package dekauliya.fyp.mathqa.Views.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataServiceRx;
import dekauliya.fyp.mathqa.DataServices.DataType;
import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import dekauliya.fyp.mathqa.Views.DetailViews.ConceptDetailActivity_;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.DividerItemDecoration;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@EFragment(R.layout.fragment_recyclerview_list)
public class KeypointFormulaListFragment extends AbstractListFragment {
    @ViewById(R.id.fragment_rv)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fast_scroller)
    FastScroller mFastScroller;

    @ViewById(R.id.fragment_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bean
    DataServiceRx dataServiceRx;

    @FragmentArg("conceptArg")
    Concept conceptArg;

    DataType dataType = DataType.KEYPOINT_FORMULA;

    @AfterInject
    void loadKeypointFormulas(){
        dataServiceRx.getConceptFormulaData(conceptArg.getId(), this);
    }

    @AfterViews
    void setUpListview(){
        if (mAdapter == null) {
            progressActivity.showLoading();
        }
        mAdapter = new FlexibleAdapter(dataServiceRx.getDataByType(dataType), this);
        mRecyclerView.setLayoutManager(createNewLinearLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.divider, 0));

        mAdapter.setFastScroller(mFastScroller, GraphicUtils.getColorAccent(getActivity()),
                (ConceptDetailActivity_) getActivity());
        mSwipeRefreshLayout.setEnabled(false);

        mListener.onFragmentChange(mSwipeRefreshLayout, mRecyclerView, SelectableAdapter.MODE_IDLE);
    }

    @Override
    public void onDataRetrieved() {
        mAdapter.updateDataSet(dataServiceRx.getData(dataType));
        if (dataServiceRx.isDataEmpty(dataType)){
            showEmptyPage(getString(R.string.empty_formula_for_concept));
        }else{
            progressActivity.showContent();
        }
    }

    @Override
    public void onError() {
        showErrorPage();
    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }
}
