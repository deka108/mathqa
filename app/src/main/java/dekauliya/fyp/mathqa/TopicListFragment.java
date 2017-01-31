package dekauliya.fyp.mathqa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestService;
import dekauliya.fyp.mathqa.Services.DataService;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.IFlexible;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@EFragment
public class TopicListFragment extends Fragment {
    @Pref
    MathQaPrefs_ mathQaPrefs;

    MathQaRestApi client = MathQaRestService.createService(MathQaRestApi.class);

    private OnFragmentInteractionListener mListener;

    @ViewById(R.id.topic_list_recyclerview)
    protected RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadTopics();
    }
    @Background
    void loadTopics() {
        int subjectId = mathQaPrefs.subject_id().get();
        Call<List<Topic>> call = client.getTopics(subjectId);
        try {
            List<Topic> topics = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTopicsAsynchronous() {
        int subjectId = mathQaPrefs.subject_id().get();
        Call<List<Topic>> call = client.getTopics(subjectId);
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                int statusCode = response.code();
                List<Topic> topics = response.body();

                if (response.isSuccessful()){

                }

                Logger.d(String.format("Status code: %s, Topics: %s", statusCode + "",
                        response.body().toString()));
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Logger.e(t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataService.getInstance().createExpandableData();
        Activity activity = getActivity();

        LinearLayoutManager layoutManager = new SmoothScrollLinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);

        // init adapter: bind data to views
        FlexibleAdapter<IFlexible> adapter =
                new FlexibleAdapter(DataService.getInstance().getData(), activity);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true); //Size of RV will not change
        // NOTE: Use default item animator 'canReuseUpdatedViewHolder()' will return true if
        // a Payload is provided. FlexibleAdapter is actually sending Payloads onItemChange.

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Custom divider item decorator
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
//                R.drawable.divider, 0));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
                layoutManager.getOrientation()));
        //Increase to add gap between sections (Works only with LinearLayout!)
        // Add 1 Scrollable Header

        // Add FastScroll to the RecyclerView, after the Adapter has been attached the RecyclerView!!!
        adapter.setFastScroller((FastScroller) getView().findViewById(R.id.fast_scroller),
                GraphicUtils.getColorAccent(activity), (MainActivity_) activity);

//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setEnabled(true);
        mListener.onFragmentChange(null, mRecyclerView, SelectableAdapter.MODE_IDLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
