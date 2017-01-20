package dekauliya.fyp.mathqa.RestApi;

/**
 * Created by dekauliya on 26/9/16.
 */

import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionApi{
    @GET("questions/")
    Call<List<Question>> loadQuestions();

    @GET("questions/{id}")
    Call<Question> loadQuestionDetail(@Path("id") int questionId);
}
