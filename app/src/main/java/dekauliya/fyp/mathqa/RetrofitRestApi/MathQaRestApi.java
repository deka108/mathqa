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
    Call<Subject> getSubject(@Path ("id") Integer subjectId);

    @GET("topics/")
    Call<List<Topic>> getTopics(@Query("subject") Integer subjectId);
    @GET("topics/{id}")
    Call<Topic> getTopic(@Path("id") Integer topicId);

    @GET("concepts/")
    Call<List<Concept>> getConcepts(@Query("topic") Integer topicId);
    @GET("concepts/{id}")
    Call<Concept> getConcept(@Path("id") Integer conceptId);

    @GET("subconcepts/")
    Call<List<SubConcept>> getSubConcepts(@Query("concept") Integer conceptId);
    @GET("subconcepts/{id}")
    Call<SubConcept> getSubconcept(@Path("id") Integer subConceptId);


    @GET("keypoints/{id}")
    Call<KeyPoint> getKeypoints(@Path ("id") Integer keyPointId);
    @GET("keypoints/?type=C")
    Call<List<KeyPoint>> getKeypointConcepts(@Query("concept") Integer conceptId);
    @GET("keypoints/?type=F")
    Call<List<KeyPoint>> getKeypointFormulas(@Query("concept") Integer conceptId);


    @GET("keywords/")
    Call<List<Keyword>> getKeywords();
    @GET("keywords/{id}")
    Call<List<Keyword>> getKeywords(@Path ("id") Integer keywordId);

    @GET("questions")
    Call<List<Question>> getQuestions(@Query("concept") Integer conceptId, @Query("subconcept")
            Integer subconceptId);
    @GET("questions/{id}")
    Call<Question> getQuestion(@Path ("id") Integer questionId);

    @GET("solutions/")
    Call<List<Solution>> getSolutions(@Query ("question") Integer questionId);
    @GET("solutions/{id}")
    Call<Solution> getSolution(@Path ("id") Integer solutionId);

    @POST("dsearch/")
    Call<String> searchDatabase(@Body String query);
    @POST("fsearch/")
    Call<String> searchFromula(@Body String formula);

}
