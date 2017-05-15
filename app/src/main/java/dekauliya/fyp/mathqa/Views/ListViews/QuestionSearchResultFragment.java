package dekauliya.fyp.mathqa.Views.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.DataServices.DataService;
import dekauliya.fyp.mathqa.DataServices.DataType;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.DrawableType;
import dekauliya.fyp.mathqa.Utils.DrawableUtils;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import dekauliya.fyp.mathqa.Utils.SearchDialogUtils;
import dekauliya.fyp.mathqa.Utils.ViewUtils;
import dekauliya.fyp.mathqa.Views.DetailViews.QuestionDetailActivity_;
import dekauliya.fyp.mathqa.Views.ListViews.Items.SearchResultSubItem;
import dekauliya.fyp.mathqa.Views.SearchViews.SearchActivity;
import dekauliya.fyp.mathqa.Views.SearchViews.SearchType;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.DividerItemDecoration;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.github.kexanie.library.MathView;

/**
 *
 * Provides list of search results depending on the search type and availability of the search
 * result.
 *
 * Created by dekauliya on 6/2/17.
 */
@EFragment(R.layout.fragment_search_result_recyclerview_list)
public class QuestionSearchResultFragment extends AbstractListFragment {
    @ViewById(R.id.fragment_rv)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fast_scroller)
    FastScroller mFastScroller;

    @ViewById(R.id.fragment_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bean
    DataService dataService;

    @Bean
    SearchDialogUtils searchUtils;

    @FragmentArg("searchTypeArg")
    SearchType searchTypeArg;

    @FragmentArg("queryArg")
    String queryArg;

    @ViewById(R.id.search_query)
    TextView searchQuery;

    @ViewById(R.id.btn_search)
    ImageButton searchBtn;

    @ViewById(R.id.sr_latex_query_container)
    LinearLayout latexQueryContainer;

    @ViewById(R.id.sr_latex_query)
    MathView latexQuery;

    @ViewById(R.id.sr_latex_alt)
    TextView latexAlt;

    DataType dataType = DataType.QUESTION_RESULT;

    @AfterInject
    void loadQuestionResults(){
        performSearch(queryArg);
    }

    private void performSearch(String query) {
        switch(searchTypeArg) {
            case TEXT_DB:
                dataService.searchTextDb(query, this);
                break;
            case FULL_TEXT:
                dataService.searchFullText(query, this);
                break;
            case FORMULA:
                dataService.searchFormula(query, this);
                break;
        }
    }

    private void displaySearch(String query) {
        switch(searchTypeArg) {
            case TEXT_DB: case FULL_TEXT:
                searchUtils.displaySearchDialog(query);
                break;
            case FORMULA:
                searchUtils.displayFormulaInputPreview(query);
                break;
        }
    }

    @AfterViews
    void setUpListview(){
        toggleLatexQuery();
        searchQuery.setText(queryArg);
        searchQuery.setMovementMethod(new ScrollingMovementMethod());
        searchBtn.setImageDrawable(DrawableUtils.getDrawable(DrawableType.SEARCH, getActivity(),
                R.color.material_color_white));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySearch(queryArg);
            }
        });

        progressActivity.showLoading();
        mAdapter = new FlexibleAdapter(dataService.getDataByType(dataType), this);
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

    private void toggleLatexQuery() {
        if (searchTypeArg == SearchType.FORMULA){
            latexQueryContainer.setVisibility(View.VISIBLE);
            ViewUtils.displayLatex(latexQuery, latexAlt, queryArg, true);
        }
    }


    @Override
    public void onDataRetrieved() {
        mAdapter.updateDataSet(dataService.getData(dataType));
        if (dataService.isDataEmpty(dataType)){
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
