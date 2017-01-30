package dekauliya.fyp.mathqa.dummy;

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
public class QuestionDummyListAdapter extends BaseAdapter {
    List<QuestionContent> questions;

    @Bean(QuestionDummyFinder.class)
    QuestionDummyFinder questionDummyFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter(){
        questions = questionDummyFinder.findAllQuestions();
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
        QuestionDummyItemView qiv;

        if (view == null){
            qiv = QuestionDummyItemView_.build(context);
        }else{
            qiv = (QuestionDummyItemView) view;
        }

        qiv.bind((QuestionContent) getItem(i));

        return qiv;
    }

}
