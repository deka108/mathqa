package dekauliya.fyp.mathqa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import dekauliya.fyp.mathqa.dummy.UI.AAR.Data.ExpandableDataProvider;
import dekauliya.fyp.mathqa.dummy.UI.AAR.Data.ExpandableDataProviderFragment;
import dekauliya.fyp.mathqa.dummy.UI.AAR.Data.ExpandableDataProviderFragment_;
import dekauliya.fyp.mathqa.dummy.UI.AAR.ExpandableFragment;

@EActivity
public class TestRecyclerMathView extends AppCompatActivity implements
        ExpandableDataProviderFragment.IDataProvider{
    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new ExpandableDataProviderFragment_(), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ExpandableFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
    }

    public void getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        ((ExpandableDataProviderFragment) fragment).getDataProvider();
    }

    @Override
    public ExpandableDataProvider onDataLoaded(ExpandableDataProvider provider) {
        return provider;
    }
}