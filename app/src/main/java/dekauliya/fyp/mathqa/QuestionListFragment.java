package dekauliya.fyp.mathqa;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import dekauliya.fyp.mathqa.DataServices.DataService_;
import dekauliya.fyp.mathqa.ListViews.Items.IDataListener;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.DividerItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
@EFragment
public class QuestionListFragment extends Fragment {

    public static final String TAG = QuestionListFragment.class.getSimpleName();

    @Pref
    MathQaPrefs_ mathQaPrefs;
    FlexibleAdapter mAdapter;
    RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuestionListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadQuestions(new IDataListener() {
            @Override
            public void onDataRetrieved() {
                displayList();
            }
        });
    }

    @Background
    void loadQuestions(IDataListener listener) {
        int subjectId = mathQaPrefs.subject_id().get();
//        DataServiceRef.getInstance().getQuestionTopicData(subjectId, listener);
//        DataService_.getInstance_(getContext()).getQuestionTopicData(subjectId, listener);
    }

    @UiThread
    void displayList() {
        List<AbstractFlexibleItem> data = DataService_.getInstance_(getContext()).getData();
        Logger.d("DATA SIZE: " + data.size());

        Activity activity = getActivity();
        mAdapter = new FlexibleAdapter(data, activity);

        LinearLayoutManager layoutManager = new SmoothScrollLinearLayoutManager(activity);
//        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true); //Size of RV will not change
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
                R.drawable.divider, 0));

        mAdapter.setFastScroller((FastScroller) getView().findViewById(R.id.fast_scroller),
                GraphicUtils.getColorAccent(activity), (MainActivity_) activity);


//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setEnabled(true);
//        Toast.makeText(getActivity(), "TEST", Toast.LENGTH_SHORT).show();
        // Sticky Headers
//        mAdapter.setDisplayHeadersAtStartUp(true)
//                .setStickyHeaders(true);
    }

}
