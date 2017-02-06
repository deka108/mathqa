package dekauliya.fyp.mathqa.dummy.UI.AAR.Data;

/**
 * Created by dekauliya on 2/2/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

@EFragment
public class ExpandableDataProviderFragment extends Fragment{

    @Bean
    ExpandableDataProvider mDataProvider;
    IDataProvider listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
//        mDataProvider = new ExpandableDataProvider_();
    }

//    @Background(serial="download")
//    void loadData() {
//        mDataProvider.loadData();
//    }

//    public AbstractExpandableDataProvider getDataProvider() {
//        return mDataProvider;
//    }

    @AfterInject
    public void getDataProvider() {
        listener.onDataLoaded(mDataProvider);
    }


    public interface IDataProvider{
        public ExpandableDataProvider onDataLoaded(ExpandableDataProvider provider);
    }
}

