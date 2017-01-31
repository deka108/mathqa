package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Solution {
    private int id;
    private String content;
    private int Question;

    public Solution(int id, String content, int question) {
        this.id = id;
        this.content = content;
        Question = question;
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

    public int getQuestion() {
        return Question;
    }

    public void setQuestion(int question) {
        Question = question;
    }
}
