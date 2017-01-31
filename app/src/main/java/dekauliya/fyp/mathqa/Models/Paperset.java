package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Paperset {
    private int id;
    private String name;
    private int subject;

    public Paperset(int id, String name, int subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public Paperset(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Paperset(String name) {
        this.name = name;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
