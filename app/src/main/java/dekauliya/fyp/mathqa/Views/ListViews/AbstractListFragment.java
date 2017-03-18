package dekauliya.fyp.mathqa.Views.ListViews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import dekauliya.fyp.mathqa.DataServices.IDataListener;
import dekauliya.fyp.mathqa.MathQaPrefs_;
import dekauliya.fyp.mathqa.Views.BaseFragment;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;

/**
 * Created by dekauliya on 5/2/17.
 */
@EFragment
public abstract class AbstractListFragment extends BaseFragment implements IDataListener,
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
