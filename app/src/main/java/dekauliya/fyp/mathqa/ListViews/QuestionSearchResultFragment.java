package dekauliya.fyp.mathqa.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataServiceRx;
import dekauliya.fyp.mathqa.DataServices.DataType;
import dekauliya.fyp.mathqa.DetailViews.QuestionDetailActivity_;
import dekauliya.fyp.mathqa.ListViews.Items.SearchResultSubItem;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.SearchViews.SearchActivity;
import dekauliya.fyp.mathqa.SearchViews.SearchType;
import dekauliya.fyp.mathqa.Utils.DrawableType;
import dekauliya.fyp.mathqa.Utils.DrawableUtils;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.DividerItemDecoration;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by dekauliya on 6/2/17.
 */
@EFragment(R.layout.fragment_search_result_recyclerview_list)
public class QuestionSearchResultFragment extends AbstractListFragment{
    @ViewById(R.id.fragment_rv)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fast_scroller)
    FastScroller mFastScroller;

    @ViewById(R.id.fragment_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bean
    DataServiceRx dataServiceRx;

    @FragmentArg("searchTypeArg")
    SearchType searchTypeArg;

    @FragmentArg("queryArg")
    String queryArg;

    @ViewById(R.id.edit_search)
    EditText searchEditText;

    @ViewById(R.id.btn_search)
    ImageButton searchBtn;

    DataType dataType = DataType.QUESTION_RESULT;

    @AfterInject
    void loadQuestionResults(){
        switch(searchTypeArg) {
            case TEXT:
                dataServiceRx.searchQuestions(queryArg, this);
                break;
            case FORMULA:
                dataServiceRx.searchFormula(queryArg, this);
                break;
            case TEST_FORMULA:
                dataServiceRx.searchTestFormula(queryArg, this);
                break;
            case TEST_TEXT:
                dataServiceRx.searchTestQuestions(queryArg, this);
                break;
        }
    }

    @AfterViews
    void setUpListview(){
        searchBtn.setImageDrawable(DrawableUtils.getDrawable(DrawableType.SEARCH, getActivity(),
                R.color.material_color_white));
        if (mAdapter == null) {
            progressActivity.showLoading();
        }
        mAdapter = new FlexibleAdapter(dataServiceRx.getDataByType(dataType), this);
        mRecyclerView.setLayoutManager(createNewLinearLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.divider, 0));

        mAdapter.setFastScroller(mFastScroller, GraphicUtils.getColorAccent(getActivity()),
                (SearchActivity) getActivity());
        mSwipeRefreshLayout.setEnabled(false);

        mListener.onFragmentChange(mSwipeRefreshLayout, mRecyclerView, SelectableAdapter.MODE_IDLE);
    }


    @Override
    public void onDataRetrieved() {
        mAdapter.updateDataSet(dataServiceRx.getData(dataType));
        if (dataServiceRx.isDataEmpty(dataType)){
            showEmptyPage(getString(R.string.no_questions_result));
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
        IFlexible item = mAdapter.getItem(position);
        if (item instanceof SearchResultSubItem){
            SearchResultSubItem sr = (SearchResultSubItem) item;
            QuestionDetailActivity_.intent(getContext())
                    .questionExtra(sr.getQuestion())
                    .start();
        }
        return false;
    }
}
