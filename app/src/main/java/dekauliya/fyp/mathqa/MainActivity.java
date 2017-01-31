package dekauliya.fyp.mathqa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PageSelected;

import dekauliya.fyp.mathqa.Services.DataService;
import dekauliya.fyp.mathqa.UI.TopicList.ConceptItem;
import dekauliya.fyp.mathqa.dummy.DummyContent;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;

@EActivity
public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener,
        FlexibleAdapter.OnUpdateListener, FlexibleAdapter.OnItemClickListener,
        FastScroller.OnScrollStateChangeListener,
        OnFragmentInteractionListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private Fragment mFragment;
//
//    private final Handler mRefreshHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//        public boolean handleMessage(Message message) {
//            switch (message.what) {
//                case 0: // Stop
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    return true;
//                case 1: // Start
//                    mSwipeRefreshLayout.setRefreshing(true);
//                    return true;
//                case 2: // Show empty view
//                    ViewCompat.animate(findViewById(R.id.empty_view)).alpha(1);
//                    return true;
//                default:
//                    return false;
//            }
//        }
//    });

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mAdapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore previous state
        if (savedInstanceState != null && mAdapter != null) {
            // Selection
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
    }

    @PageSelected(R.id.main_viewpager)
    void onPageSelected(ViewPager view, int position) {
        switch(position){
            case 0:
                Toast.makeText(getApplicationContext(), "TOPICS", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "QUESTIONS", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFastScrollerStateChange(boolean scrolling) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private static Fragment topicListFragment = null;
        private static Fragment questionListFragment = null;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position){
                case 0:
                    if (topicListFragment == null){
                        topicListFragment = new TopicListFragment_();
                    }
                    return topicListFragment;
                case 1:
                    if (questionListFragment == null){
                        questionListFragment = new QuestionListFragment_();
                    }
                    return questionListFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Topics";
                case 1:
                    return "Questions";
            }
            return null;
        }
    }


    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, @SelectableAdapter.Mode int mode) {
        mRecyclerView = recyclerView;
        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
//        mSwipeRefreshLayout = swipeRefreshLayout;
//        initializeSwipeToRefresh();
//        initializeActionModeHelper(mode);
    }

    @Override
    public void initSearchView(Menu menu) {

    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible flexibleItem = mAdapter.getItem(position);

       if (flexibleItem instanceof ConceptItem) {
           ConceptItem conceptItem = (ConceptItem) flexibleItem;
           Toast.makeText(this, conceptItem.getConcept().getName(), Toast.LENGTH_SHORT).show();
           Logger.d("Concept clicked: " +  conceptItem.getConcept().getName());
       }
        return false;
    }

    @Override
    public void onUpdateEmptyView(int size) {
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
        View emptyView = findViewById(R.id.empty_view);
        TextView emptyText = (TextView) findViewById(R.id.empty_text);

        Logger.d("Some : " + fastScroller);
//        if (emptyText != null)
//            emptyText.setText(getString(R.string.no_items));
//        if (size > 0) {
//            fastScroller.setVisibility(View.VISIBLE);
////            mRefreshHandler.removeMessages(2);
//            emptyView.setAlpha(0);
//        } else {
//            emptyView.setAlpha(0);
////            mRefreshHandler.sendEmptyMessage(2);
//            fastScroller.setVisibility(View.GONE);
//        }
//        if (mAdapter != null) {
//            String message = (mAdapter.hasSearchText() ? "Filtered " : "Refreshed ");
//            message += size + " items in " + mAdapter.getTime() + "ms";
//            Snackbar.make(findViewById(R.id.select_subject_view), message, Snackbar.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onBackPressed() {
        // Close the App
        DataService.onDestroy();
        super.onBackPressed();
    }
}
