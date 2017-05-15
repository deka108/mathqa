package dekauliya.fyp.mathqa.Views;

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
 */

public class BaseListActivity extends BaseActivity implements
        OnListFragmentInteractionListener,
        FlexibleAdapter.OnUpdateListener,
        FastScroller.OnScrollStateChangeListener{
    RecyclerView mRecyclerView;
    FlexibleAdapter<AbstractFlexibleItem> mAdapter;

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
            emptyView.setAlpha(0);
        } else {
            emptyView.setAlpha(0);
            fastScroller.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                 int mode) {
        mRecyclerView = recyclerView;
        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
    }

    @Override
    public void onFastScrollerStateChange(boolean scrolling) {

    }
}
