package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class Question {
    String id;
    String question_type;
    String usedFor;
    int marks;
    String difficulty_level;
    String response_type;
    String source;
    String content;
    Boolean is_sample;
    int concept;
    int subconcept;
    String paper;
    int[] keypoints;
    int[] keywords;

    public Question(String id, String question_type, String usedFor, int marks,
                    String difficulty_level, String response_type, String source,
                    String content, Boolean is_sample, int concept, int subconcept, String paper,
                    int[] keypoints, int[] keywords) {
        this.id = id;
        this.question_type = question_type;
        this.usedFor = usedFor;
        this.marks = marks;
        this.difficulty_level = difficulty_level;
        this.response_type = response_type;
        this.source = source;
        this.content = content;
        this.is_sample = is_sample;
        this.concept = concept;
        this.subconcept = subconcept;
        this.paper = paper;
        this.keypoints = keypoints;
        this.keywords = keywords;
    }

    @ParcelConstructor
    public Question(String id, String question_type, String usedFor, int marks,
                    String difficulty_level, String response_type, String source, String content,
                    Boolean is_sample) {
        this.id = id;
        this.question_type = question_type;
        this.usedFor = usedFor;
        this.marks = marks;
        this.difficulty_level = difficulty_level;
        this.response_type = response_type;
        this.source = source;
        this.content = content;
        this.is_sample = is_sample;
    }

    public Question(String question_type, String usedFor, int marks, String difficulty_level,
                    String response_type, String source, String content, Boolean is_sample) {
        this.question_type = question_type;
        this.usedFor = usedFor;
        this.marks = marks;
        this.difficulty_level = difficulty_level;
        this.response_type = response_type;
        this.source = source;
        this.content = content;
        this.is_sample = is_sample;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSample() {
        return is_sample;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
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

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
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
        return is_sample;
    }

    public void setSample(Boolean sample) {
        is_sample = sample;
    }

    public int getConcept() {
        return concept;
    }

    public void setConcept(int concept) {
        this.concept = concept;
    }

    public int getSubconcept() {
        return subconcept;
    }

    public void setSubconcept(int subconcept) {
        this.subconcept = subconcept;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public Boolean getIs_sample() {
        return is_sample;
    }

    public void setIs_sample(Boolean is_sample) {
        this.is_sample = is_sample;
    }

    public String getPaper() {
        return paper;
    }

    public int[] getKeypoints() {
        return keypoints;
    }

    public void setKeypoints(int[] keypoints) {
        this.keypoints = keypoints;
    }

    public int[] getKeywords() {
        return keywords;
    }

    public void setKeywords(int[] keywords) {
        this.keywords = keywords;
    }
}
