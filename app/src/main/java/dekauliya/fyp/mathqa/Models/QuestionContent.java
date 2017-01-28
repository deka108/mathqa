package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 21/9/16.
 */

public class QuestionContent {
    static int counter;
    public final int questionNo;
    public final String content;
    public final int difficulty;

    public QuestionContent(String content, int difficulty) {
        this.content = content;
        this.difficulty = difficulty;
        counter++;
        this.questionNo = counter;
    }
}
