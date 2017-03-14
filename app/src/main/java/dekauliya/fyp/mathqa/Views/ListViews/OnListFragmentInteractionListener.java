package dekauliya.fyp.mathqa.Views.ListViews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import eu.davidea.flexibleadapter.SelectableAdapter;

/**
 * Created by dekauliya on 5/2/17.
 */

public interface OnListFragmentInteractionListener {
    void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                          @SelectableAdapter.Mode int mode);
}
