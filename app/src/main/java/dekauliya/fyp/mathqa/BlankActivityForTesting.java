package dekauliya.fyp.mathqa;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.dummy.RecyclerView.QuestionAdapter;

@EActivity(R.layout.activity_blank_for_testing)
public class BlankActivityForTesting extends AppCompatActivity {

    @ViewById(R.id.blank_recycler_view)
    RecyclerView mRecyclerView;

    @Bean
    QuestionAdapter qAdapter;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DataService_.getInstance_(this).loadQuestionsData();
//    }

    @AfterViews
    void updateRecyclerView(){
        mRecyclerView.setAdapter(qAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}