package dekauliya.fyp.mathqa;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Models.QuestionContent;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

@EActivity(R.layout.question_list)
public class QuestionListActivity extends AppCompatActivity {

    @ViewById(R.id.question_list)
    ListView questionList;

    @Bean
    QuestionListAdapter adapter;

    @AfterViews
    void bindAdapter(){
        questionList.setAdapter(adapter);
    }

    @ItemClick
    void questionListItemClicked(QuestionContent qc){
        Logger.d("CLICKED!");
        makeText(this, "QuestionTest No. " + qc.questionNo, LENGTH_SHORT).show();
    }

}
