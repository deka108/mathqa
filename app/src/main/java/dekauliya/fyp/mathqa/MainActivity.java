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
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PageSelected;

import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

@EActivity
public class MainActivity extends AppCompatActivity implements
        FastScroller.OnScrollStateChangeListener{
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
     * Bundle key representing the Active Fragment
     */
    private static final String STATE_ACTIVE_FRAGMENT = "active_fragment";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Fragment mFragment;
    private RecyclerView mRecyclerView;
    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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


//    @Override
//    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
//                                 @SelectableAdapter.Mode int mode) {
//        mRecyclerView = recyclerView;
//        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
//        mSwipeRefreshLayout = swipeRefreshLayout;
//        initializeSwipeToRefresh();
//    }
//
//    @Override
//    public void initSearchView(Menu menu) {
//
//    }
//
//    private void initializeSwipeToRefresh() {
//        // Swipe down to force synchronize
//        //mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setDistanceToTriggerSync(390);
//        //mSwipeRefreshLayout.setEnabled(true); //Controlled by fragments!
//        mSwipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_purple, android.R.color.holo_blue_light,
//                android.R.color.holo_green_light, android.R.color.holo_orange_light);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Passing true as parameter we always animate the changes between the old and the new data set
//                DataServiceRef.getInstance().updateNewItems();
//                mAdapter.updateDataSet(DataServiceRef.getInstance().getData(),
//                        true);
//                mSwipeRefreshLayout.setRefreshing(true);
//                mRefreshHandler.sendEmptyMessageDelayed(0, 1500L); //Simulate network time
//            }
//        });
//    }

//
//    @Override
//    public boolean onItemClick(int position) {
//        IFlexible flexibleItem = mAdapter.getItem(position);
//
//       if (flexibleItem instanceof ConceptSubItem) {
//           ConceptSubItem conceptSubItem = (ConceptSubItem) flexibleItem;
//           ConceptActivity_.intent(getApplicationContext()).flags(FLAG_ACTIVITY_NEW_TASK)
//                   .concept(conceptSubItem.getConcept()).start();
//       }
//        return false;
//    }
//
//    @Override
//    public void onUpdateEmptyView(int size) {
//        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
//        View emptyView = findViewById(R.id.empty_view);
//        TextView emptyText = (TextView) findViewById(R.id.empty_text);
//
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
//    }

    @Override
    public void onBackPressed() {
        // Close the App
//        DataServiceRef.onDestroy();
        super.onBackPressed();
    }
}
