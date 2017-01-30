package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Topic {
    private int id;
    private String name;

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return this.name;
    }
}
