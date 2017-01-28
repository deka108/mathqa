package dekauliya.fyp.mathqa.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by dekauliya on 20/9/16.
 */

@Parcel
public class QuestionTest {
//    private static int counter = 0;
    @SerializedName("id")
    int id;
    String title;
//    private int difficulty;
//    private String subtopic;
//    private String topic;
//    private QuestionContent qContent;
    @SerializedName("question_text")
    public String content;

    public QuestionTest(){

    }

    public QuestionTest(int id, String content){
        this.content = content;

        this.id = id;
//        this.id = counter++;
    }

//    public QuestionTest(String title, String content){
//        this.content = content;
//        this.title = title;
//        this.id = counter++;
//    }
//
//    public void setTitle(String title){
//        this.title = title;
//    }

    @Override
    public String toString() {
        return this.getTitle() + this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
//        return title;
        return (this.title == null) ? ("QuestionTest " + this.id) : this.title;
    }
}
