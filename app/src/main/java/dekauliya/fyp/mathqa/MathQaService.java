package dekauliya.fyp.mathqa;

import java.util.List;

import dekauliya.fyp.mathqa.Models.KeyPoint;
import dekauliya.fyp.mathqa.Models.Keyword;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.Subject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dekauliya on 29/1/17.
 */

public interface MathQaService {
    @GET("subjects/")
    Call<List<Subject>> getSubjects();

    @GET("topics/")
    Call<List<String>> getTopics(@Query("subject") int subjectId);

    @GET("concepts/")
    Call<List<String>> getConcepts(@Query("topic") int topicId);

    @GET("subconcepts/")
    Call<List<String>> getSubConcepts(@Query("concept") int subconceptId);

    @GET("keypoints/")
    Call<List<KeyPoint>> getKeypoints(@Query("concept") int conceptId);

    @GET("keypoints/{id}")
    Call<KeyPoint> getKeypoint(@Path("id") int keyPointId);

    @GET("keywords/")
    Call<List<Keyword>> getKeywords();

    @GET("keywords/{id}")
    Call<List<Keyword>> getKeywords(@Path("id") int keywordId);

    @GET("questions")
    Call<List<Question>> getQuestions(@Query("concept") int conceptId, @Query("subconcept") int
            subconceptId);

    @GET("questions/{id}")
    Call<Question> getQuestion(@Path("id") int questionId);

    @GET("solutions/")
    Call<List<String>> getSolutions();

    @GET("solutions/{id}")
    Call<String> getSolution(@Path("id") int solutionId);

    @POST("dsearch/")
    Call<String> searchDatabase(@Body String query);

    @POST("fsearch/")
    Call<String> searchFromula(@Body String formula);
}
