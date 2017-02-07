package dekauliya.fyp.mathqa;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;

import dekauliya.fyp.mathqa.ListViews.OnListFragmentInteractionListener;
import dekauliya.fyp.mathqa.ListViews.QuestionTopicListFragment_;
import dekauliya.fyp.mathqa.ListViews.TopicConceptListFragment_;
import dekauliya.fyp.mathqa.Utils.FabUtils;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

@EActivity
public class RealMainActivity extends AppCompatActivity implements
        FastScroller.OnScrollStateChangeListener,
        FlexibleAdapter.OnUpdateListener,
        OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private RecyclerView mRecyclerView;
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final Handler mRefreshHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0: // Stop
                    mSwipeRefreshLayout.setRefreshing(false);
                    return true;
                case 1: // Start
                    mSwipeRefreshLayout.setRefreshing(true);
                    return true;
                case 2: // Show empty view
                    ViewCompat.animate(findViewById(R.id.empty_view)).alpha(1);
                    return true;
                default:
                    return false;
            }
        }
    });

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FabUtils.setUpFab(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_real_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFastScrollerStateChange(boolean scrolling) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        static final int PAGE_COUNT = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
            switch(position){
                case 0:
                    return TopicConceptListFragment_.builder().build();
                case 1:
                    return QuestionTopicListFragment_.builder().build();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
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

    /**
     * Handling RecyclerView when empty.
     * <p><b>Note:</b> The order, how the 3 Views (RecyclerView, EmptyView, FastScroller)
     * are placed in the Layout, is important!</p>
     */
    @Override
    public void onUpdateEmptyView(int size) {
//        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
//        View emptyView = findViewById(R.id.empty_view);
//        TextView emptyText = (TextView) findViewById(R.id.empty_text);
//        if (emptyText != null)
//            emptyText.setText(getString(R.string.no_items));
//        if (size > 0) {
//            fastScroller.setVisibility(View.VISIBLE);
//            mRefreshHandler.removeMessages(2);
//            emptyView.setAlpha(0);
//        } else {
//            emptyView.setAlpha(0);
//            mRefreshHandler.sendEmptyMessage(2);
//            fastScroller.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                 int mode) {
        mRecyclerView = recyclerView;
        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
        mSwipeRefreshLayout = swipeRefreshLayout;
        initializeSwipeToRefresh();
    }

    private void initializeSwipeToRefresh() {
        // Swipe down to force synchronize
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(390);
        mSwipeRefreshLayout.setEnabled(true); //Controlled by fragments!
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }
}
