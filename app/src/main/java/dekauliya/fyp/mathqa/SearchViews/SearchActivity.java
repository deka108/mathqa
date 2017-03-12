package dekauliya.fyp.mathqa.SearchViews;

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

import dekauliya.fyp.mathqa.BaseActivity;
import dekauliya.fyp.mathqa.ListViews.OnListFragmentInteractionListener;
import dekauliya.fyp.mathqa.ListViews.QuestionSearchResultFragment_;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.FabUtils;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.SelectableAdapter;

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

        fabUtils.setUpFab();
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
}
