package dekauliya.fyp.mathqa.Views;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Views.ListViews.OnListFragmentInteractionListener;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by dekauliya on 18/3/17.
 *
 * Provides the common features of a MathQA ListActivity which contains
 * {@link android.support.v7.widget.RecyclerView} and scrolling actions via
 * {@link eu.davidea.fastscroller.FastScroller}
 */
public class BaseListActivity extends BaseActivity implements
        OnListFragmentInteractionListener,
        FlexibleAdapter.OnUpdateListener,
        FastScroller.OnScrollStateChangeListener{
    RecyclerView mRecyclerView;
    FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Handling RecyclerView when empty.
     * <p><b>Note:</b> The order, how the 3 Views (RecyclerView, EmptyView, FastScroller)
     * are placed in the Layout, is important!</p>
     */
    @Override
    public void onUpdateEmptyView(int size) {
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller);
        View emptyView = findViewById(R.id.empty_view);
        TextView emptyText = (TextView) findViewById(R.id.empty_text);
        if (emptyText != null)
            emptyText.setText(getString(R.string.no_items));
        if (size > 0) {
            fastScroller.setVisibility(View.VISIBLE);
            mRefreshHandler.removeMessages(2);
            emptyView.setAlpha(0);
        } else {
            emptyView.setAlpha(0);
            mRefreshHandler.sendEmptyMessage(2);
            fastScroller.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                 int mode) {
        mRecyclerView = recyclerView;
        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
        mSwipeRefreshLayout = swipeRefreshLayout;
//        initializeSwipeToRefresh();
    }

    public final Handler mRefreshHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
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

    public void initializeSwipeToRefresh() {
        // Swipe down to force synchronize
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
    }

    @Override
    public void onFastScrollerStateChange(boolean scrolling) {

    }
}
