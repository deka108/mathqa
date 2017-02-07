package dekauliya.fyp.mathqa.ListViews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import dekauliya.fyp.mathqa.DataServices.IDataListener;
import dekauliya.fyp.mathqa.MathQaPrefs_;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;

/**
 * Created by dekauliya on 5/2/17.
 */
@EBean
public abstract class AbstractListFragment extends Fragment implements IDataListener,
        FlexibleAdapter.OnItemClickListener {
    protected OnListFragmentInteractionListener mListener;

    @Pref
    MathQaPrefs_ prefs;

    protected FlexibleAdapter mAdapter;

    int getSubjectId(){
        return prefs.subject_id().get();
    }

    protected LinearLayoutManager createNewLinearLayoutManager() {
        return new SmoothScrollLinearLayoutManager(getActivity());
    }

    public void performFabAction() {
        //default implementation does nothing
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void initializeFab(){

    }

    @Override
    public abstract void onDataRetrieved();

    public FlexibleAdapter getmAdapter() {
        return mAdapter;
    }

}
