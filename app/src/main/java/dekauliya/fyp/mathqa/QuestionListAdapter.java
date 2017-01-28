package dekauliya.fyp.mathqa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import dekauliya.fyp.mathqa.Models.QuestionContent;

/**
 * Created by dekauliya on 23/1/17.
 */
@EBean
public class QuestionListAdapter extends BaseAdapter {
    List<QuestionContent> questions;

    @Bean(QuestionFinder.class)
    QuestionFinder questionFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter(){
        questions = questionFinder.findAllQuestions();
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QuestionItemView qiv;

        if (view == null){
            qiv = QuestionItemView_.build(context);
        }else{
            qiv = (QuestionItemView) view;
        }

        qiv.bind((QuestionContent) getItem(i));

        return qiv;
    }

}
