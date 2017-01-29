package dekauliya.fyp.mathqa.RetrofitRestApi;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.KeyPoint;
import dekauliya.fyp.mathqa.Models.Keyword;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.Solution;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.Models.Subject;
import dekauliya.fyp.mathqa.Models.Topic;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dekauliya on 29/1/17.
 */

public interface MathQaRestApi {
    @GET("subjects/")
    Call<List<Subject>> getSubjects();
    @GET("subjects/{id}")
    Call<Subject> getSubject(@Path ("id") int subjectId);

    @GET("topics/")
    Call<List<Topic>> getTopics(@Query("subject") int subjectId);
    @GET("topics/{id}")
    Call<Topic> getTopic(@Path("id") int topicId);

    @GET("concepts/")
    Call<List<Concept>> getConcepts(@Query("topic") int topicId);
    @GET("concepts/{id}")
    Call<Concept> getConcept(@Path("id") int conceptId);

    @GET("subconcepts/")
    Call<List<SubConcept>> getSubConcepts(@Query("concept") int subconceptId);
    @GET("subconcepts/{id}")
    Call<SubConcept> getSubconcept(@Path("id") int subConceptId);

    @GET("keypoints/")
    Call<List<KeyPoint>> getKeypoints(@Query("concept") int conceptId);
    @GET("keypoints/{id}")
    Call<KeyPoint> getKeypoint(@Path ("id") int keyPointId);

    @GET("keywords/")
    Call<List<Keyword>> getKeywords();
    @GET("keywords/{id}")
    Call<List<Keyword>> getKeywords(@Path ("id") int keywordId);

    @GET("questions")
    Call<List<Question>> getQuestions(@Query("concept") int conceptId, @Query("subconcept") int
            subconceptId);
    @GET("questions/{id}")
    Call<Question> getQuestion(@Path ("id") int questionId);

    @GET("solutions/")
    Call<List<Solution>> getSolutions();
    @GET("solutions/{id}")
    Call<Solution> getSolution(@Path ("id") int solutionId);

    @POST("dsearch/")
    Call<String> searchDatabase(@Body String query);
    @POST("fsearch/")
    Call<String> searchFromula(@Body String formula);
}
