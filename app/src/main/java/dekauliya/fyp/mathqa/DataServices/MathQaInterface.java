package dekauliya.fyp.mathqa.DataServices;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.Formula;
import dekauliya.fyp.mathqa.Models.KeyPoint;
import dekauliya.fyp.mathqa.Models.Keyword;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.SearchResult;
import dekauliya.fyp.mathqa.Models.Solution;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.Models.Subject;
import dekauliya.fyp.mathqa.Models.Topic;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dekauliya on 29/1/17.
 */

public interface MathQaInterface {
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
    Observable<List<Solution>> getSolutionsByQuestion(@Query("question") String questionId);
    @GET("solutions/{id}")
    Observable<Solution> getSolution(@Path("id") Integer solutionId);

    @GET("search/?type=d")
    Observable<List<SearchResult>> searchDatabase(@Query(value = "query") String query);
    @GET("search/?type=f")
    Observable<List<SearchResult>> searchFormula(@Query(value = "query") String formula);
    @GET("search/?type=t")
    Observable<List<SearchResult>> searchText(@Query(value = "query") String text);

    @Multipart
    @POST("upload/")
    Observable<ResponseBody> uploadImage(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("post_text/")
    Observable<ResponseBody> postText(@Field("text") String text);


    @GET("test_questions/search/?type=d")
    Observable<List<Question>> searchTestDatabase(@Query(value = "query") String query);
    @GET("search_test_formula/")
    Observable<List<Formula>> searchTestFormula(@Query(value = "query") String formula);
}
