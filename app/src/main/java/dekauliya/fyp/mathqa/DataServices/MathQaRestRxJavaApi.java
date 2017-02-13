package dekauliya.fyp.mathqa.DataServices;

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
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dekauliya on 29/1/17.
 */

public interface MathQaRestRxJavaApi {
    @GET("subjects/")
    Observable<List<Subject>> getSubjects();
    @GET("subjects/{id}")
    Observable<Subject> getSubject(@Path("id") Integer subjectId);

    @GET("topics/")
    Observable<List<Topic>> getTopics(@Query("subject") Integer subjectId);
    @GET("topics/{id}")
    Observable<Topic> getTopic(@Path("id") Integer topicId);

    @GET("concepts/")
    Observable<List<Concept>> getConcepts(@Query("topic") Integer topicId);
    @GET("concepts/{id}")
    Observable<Concept> getConcept(@Path("id") Integer conceptId);

    @GET("subconcepts/")
    Observable<List<SubConcept>> getSubConcepts(@Query("concept") Integer conceptId);
    @GET("subconcepts/{id}")
    Observable<SubConcept> getSubconcept(@Path("id") Integer subConceptId);

    @GET("keypoints/{id}")
    Observable<KeyPoint> getKeypoints(@Path ("id") Integer keypointId);
    @GET("keypoints/?type=C")
    Observable<List<KeyPoint>> getKeypointConcepts(@Query("concept") Integer conceptId);
    @GET("keypoints/?type=F")
    Observable<List<KeyPoint>> getKeypointFormulas(@Query("concept") Integer conceptId);

    @GET("keywords/")
    Observable<List<Keyword>> getKeywords();
    @GET("keywords/{id}")
    Observable<List<Keyword>> getKeywords(@Path("id") Integer keywordId);

    @GET("questions")
    Observable<List<Question>> getQuestions(@Query("concept") Integer conceptId, @Query("subconcept") Integer
            subconceptId);
    @GET("questions/{id}")
    Observable<Question> getQuestion(@Path("id") String questionId);

    @GET("solutions/")
    Observable<List<Solution>> getSolutions(@Query("question") String questionId);
    @GET("solutions/{id}")
    Observable<Solution> getSolution(@Path("id") Integer solutionId);

    @GET("questions/search/?type=d")
    Observable<List<Question>> searchDatabase(@Query(value = "query", encoded = true) String query);
    @GET("questions/search/?type=f")
    Observable<List<Question>> searchFormula(@Query(value = "query", encoded = true) String formula);
}
