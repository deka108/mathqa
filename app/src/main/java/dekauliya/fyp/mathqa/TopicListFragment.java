package dekauliya.fyp.mathqa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestService;
import eu.davidea.flexibleadapter.FlexibleAdapter;
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

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadTopics();
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

    List<IFlexible> getData(){
        String[] names = {"Deka", "Refo", "Amanah", "Akbar"};
        List<IFlexible> data = new ArrayList<>();
//        for(String name: names){
//            data.add(name);
//        }
        return data;
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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

//            recyclerView.setAdapter(new TopicRecyclerViewAdapter(DummyContent.ITEMS, mListener));

            // get data items
            List<IFlexible> items = getData();

            // init adapter: bind data to views
            FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items);

            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
