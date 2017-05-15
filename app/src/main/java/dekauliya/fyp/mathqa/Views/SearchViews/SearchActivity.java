package dekauliya.fyp.mathqa.Views.SearchViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.FabUtils;
import dekauliya.fyp.mathqa.Views.BaseActivity;
import dekauliya.fyp.mathqa.Views.ListViews.OnListFragmentInteractionListener;
import dekauliya.fyp.mathqa.Views.ListViews.QuestionSearchResultFragment_;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.SelectableAdapter;

/**
 * Activity for displaying the mathematical document search result which involves latest query, and
 * list of relevant questions that match the query.
 */
@EActivity
public class SearchActivity extends BaseActivity implements OnListFragmentInteractionListener, FastScroller.OnScrollStateChangeListener {
    @Extra
    SearchType searchTypeExtra;

    @Extra
    String searchQuery;

    @Bean
    FabUtils fabUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getSearchTitle());

        fabUtils.setUpFab();
    }

    public String getSearchTitle(){
        switch(searchTypeExtra){
            case FORMULA:
                return "Formula Search Results";
            case FULL_TEXT:
                return "Full Text Search Results";
            case TEXT_DB:
                return "Database Search Results";
        }
        return "Search Results";
    }


    @AfterViews
    void performSearch(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Logger.d("Search query extra: " + searchQuery);
        ft.replace(R.id.fragment_search_container, QuestionSearchResultFragment_.builder()
                .searchTypeArg(searchTypeExtra)
                .queryArg(searchQuery)
                .build());
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                 @SelectableAdapter.Mode int mode) {

    }

    @Override
    public void onFastScrollerStateChange(boolean scrolling) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        performSearch();
    }
}
