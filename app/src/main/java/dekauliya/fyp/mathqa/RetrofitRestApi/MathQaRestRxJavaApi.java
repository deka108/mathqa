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
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dekauliya on 29/1/17.
 */

public interface MathQaRestRxJavaApi {
    @GET("subjects/")
    Observable<List<Subject>> getSubjects();
    @GET("subjects/{id}")
    Observable<Subject> getSubject(@Path("id") int subjectId);

    @GET("topics/")
    Observable<List<Topic>> getTopics(@Query("subject") int subjectId);
    @GET("topics/{id}")
    Observable<Topic> getTopic(@Path("id") int topicId);

    @GET("concepts/")
    Observable<List<Concept>> getConcepts(@Query("topic") int topicId);
    @GET("concepts/{id}")
    Observable<Concept> getConcept(@Path("id") int conceptId);

    @GET("subconcepts/")
    Observable<List<SubConcept>> getSubConcepts(@Query("concept") int subconceptId);
    @GET("subconcepts/{id}")
    Observable<SubConcept> getSubconcept(@Path("id") int subConceptId);

    @GET("keypoints/{id}")
    Observable<KeyPoint> getKeypoints(@Path ("id") int keyPointId);
    @GET("keypoints/?type=C")
    Observable<List<KeyPoint>> getKeypointConcepts(@Query("concept") int conceptId);
    @GET("keypoints/?type=F")
    Observable<List<KeyPoint>> getKeypointFormulas(@Query("concept") int conceptId);

    @GET("keywords/")
    Observable<List<Keyword>> getKeywords();
    @GET("keywords/{id}")
    Observable<List<Keyword>> getKeywords(@Path("id") int keywordId);

    @GET("questions")
    Observable<List<Question>> getQuestions(@Query("concept") int conceptId, @Query("subconcept") int
            subconceptId);
    @GET("questions/{id}")
    Observable<Question> getQuestion(@Path("id") int questionId);

    @GET("solutions/")
    Observable<List<Solution>> getSolutions();
    @GET("solutions/{id}")
    Observable<Solution> getSolution(@Path("id") int solutionId);

    @POST("dsearch/")
    Observable<String> searchDatabase(@Body String query);
    @POST("fsearch/")
    Observable<String> searchFromula(@Body String formula);
}
