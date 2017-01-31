package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Topic {
    private int id;
    private String name;
    private int subject;

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Topic(int id, String name, int subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public Topic(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
