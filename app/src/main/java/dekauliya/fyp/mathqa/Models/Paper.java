package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class Paper {
    String id;
    int year;
    String month;
    int number;
    int noOfQuestion;
    int paperset;
    int subject;

    public Paper(String id, int year, String month, int number, int noOfQuestion, int paperset, int subject) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.number = number;
        this.noOfQuestion = noOfQuestion;
        this.paperset = paperset;
        this.subject = subject;
    }

    public Paper(String id, int year, String month, int number, int noOfQuestion) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.number = number;
        this.noOfQuestion = noOfQuestion;
    }

    @ParcelConstructor
    public Paper(int year, String month, int number, int noOfQuestion) {
        this.year = year;
        this.month = month;
        this.number = number;
        this.noOfQuestion = noOfQuestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(int noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public int getPaperset() {
        return paperset;
    }

    public void setPaperset(int paperset) {
        this.paperset = paperset;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
