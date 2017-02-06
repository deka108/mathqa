package dekauliya.fyp.mathqa.dummy.RecyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dekauliya.fyp.mathqa.Blank2ActivityTesting_;
import dekauliya.fyp.mathqa.Models.Question;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by dekauliya on 4/2/17.
 */
@EBean
public class QuestionAdapter extends RecyclerViewAdapterBase<Question, QuestionItemView>
        implements IFinish {

    List<Question> items = new ArrayList<>(Arrays.asList(Data.qs));

    @RootContext
    Context context;

    @Bean
    QuestionFinder qFinder;

    @AfterInject
    void loadQuestion(){
        Logger.d("TEST");
        qFinder.findQuestions(this);
    }

    @Override
    protected QuestionItemView onCreateItemView(ViewGroup parent, int viewType) {
        return QuestionItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<QuestionItemView> holder, int position) {
        QuestionItemView view = holder.getView();
        final Question question = items.get(position);
        view.bind(question);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "QUESTION #" + question.getId() + "!!", Toast
                        .LENGTH_SHORT).show();
                Blank2ActivityTesting_.intent(context).question(question).flags
                        (FLAG_ACTIVITY_CLEAR_TOP)
                        .start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onFinish(List<Question> objects) {
        items.clear();
        notifyItemRangeRemoved(0, items.size());
        items = objects;
        notifyDataSetChanged();
    }
}
