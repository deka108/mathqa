package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Question {
    private String id;
    private String questionType;
    private String usedFor;
    private int marks;
    private String difficultyLevel;
    private String responseType;
    private String source;
    private String content;
    private Boolean isSample;

    public Question(String id, String questionType, String usedFor, int marks,
                    String difficultyLevel, String responseType, String source, String content,
                    Boolean isSample) {
        this.id = id;
        this.questionType = questionType;
        this.usedFor = usedFor;
        this.marks = marks;
        this.difficultyLevel = difficultyLevel;
        this.responseType = responseType;
        this.source = source;
        this.content = content;
        this.isSample = isSample;
    }

    public Question(String questionType, String usedFor, int marks, String difficultyLevel, String responseType, String source, String content, Boolean isSample) {
        this.questionType = questionType;
        this.usedFor = usedFor;
        this.marks = marks;
        this.difficultyLevel = difficultyLevel;
        this.responseType = responseType;
        this.source = source;
        this.content = content;
        this.isSample = isSample;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSample() {
        return isSample;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getUsedFor() {
        return usedFor;
    }

    public void setUsedFor(String usedFor) {
        this.usedFor = usedFor;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isSample() {
        return isSample;
    }

    public void setSample(Boolean sample) {
        isSample = sample;
    }
}
