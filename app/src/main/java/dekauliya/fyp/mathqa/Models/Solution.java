package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class Solution {
    int id;
    String content;
    String question;

    @ParcelConstructor
    public Solution(int id, String content, String question) {
        this.id = id;
        this.content = content;
        this.question = question;
    }

    public Solution(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Solution(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
