package dekauliya.fyp.mathqa.dummy.RecyclerView;

import android.content.Context;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dekauliya on 4/2/17.
 */
@EBean
public class QuestionFinder {
    MathQaRestRxJavaApi clientObservable = MathQaRestRxJavaService.createService(MathQaRestRxJavaApi
            .class);

    @RootContext
    Context context;

    public void loadQuestionData(final IFinish iFinish){
        clientObservable.getQuestions(27, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Question>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Question> value) {
                        StringBuffer sb = new StringBuffer();
                        for(Question question: value){
                            sb.append(String.format("Question #%s: %s", question.getId(), question
                                    .getContent()));
                            sb.append("\n");
                        }
                        Logger.d(sb.toString());
                        iFinish.onFinish(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void findQuestions(IFinish iFinish){
        loadQuestionData(iFinish);
    }
}
