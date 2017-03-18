package dekauliya.fyp.mathqa.Views.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataService;
import dekauliya.fyp.mathqa.DataServices.DataType;
import dekauliya.fyp.mathqa.DataServices.IDataListener;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import dekauliya.fyp.mathqa.Views.DetailViews.ConceptDetailActivity_;
import dekauliya.fyp.mathqa.Views.ListViews.Items.ConceptSubItem;
import dekauliya.fyp.mathqa.Views.TopicConceptActivity_;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.DividerItemDecoration;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@EFragment(R.layout.fragment_recyclerview_list)
public class TopicConceptListFragment extends AbstractListFragment {
    @ViewById(R.id.fragment_rv)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fast_scroller)
    FastScroller mFastScroller;

    @Bean
    DataService dataService;

    @ViewById(R.id.fragment_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    DataType dataType = DataType.TOPIC_CONCEPT;

    @AfterInject
    void loadTopics(){
        int subjectId = getSubjectId();
        dataService.getTopicConceptData(subjectId, this);
    }

    @AfterViews
    void setUpListview(){
        if (mAdapter == null) {
            progressActivity.showLoading();
        }
        mAdapter = new FlexibleAdapter(dataService.getDataByType(dataType), this);
        mRecyclerView.setLayoutManager(createNewLinearLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.divider, 0));

        mAdapter.setFastScroller(mFastScroller, GraphicUtils.getColorAccent(getActivity()),
                (TopicConceptActivity_) getActivity());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataService.getTopicConceptData(getSubjectId(), (IDataListener) getActivity());
                mSwipeRefreshLayout.setEnabled(true);
//                progressActivity.showLoading();
            }
        });

        mListener.onFragmentChange(mSwipeRefreshLayout, mRecyclerView, SelectableAdapter.MODE_IDLE);
    }

    @Override
    public void onDataRetrieved() {
        mAdapter.updateDataSet(dataService.getData(dataType));

        if (dataService.isDataEmpty(dataType)){
            showEmptyPage(getString(R.string.empty_topic_for_subject));
        }else {
            progressActivity.showContent();
        }
    }

    @Override
    public void onError() {
        showErrorPage();
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = mAdapter.getItem(position);
        if (item instanceof ConceptSubItem){
            ConceptSubItem c = ((ConceptSubItem) item);
            ConceptDetailActivity_.intent(getContext()).conceptExtra(c.getConcept()).start();
        }
        return false;
    }
}
