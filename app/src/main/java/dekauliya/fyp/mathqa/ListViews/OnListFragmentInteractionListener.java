package dekauliya.fyp.mathqa.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by dekauliya on 5/2/17.
 */

public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(AbstractFlexibleItem item);

    void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                          @SelectableAdapter.Mode int mode);
}
