package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class SubConcept {
    private int id;
    private String name;
    private int concept;

    public SubConcept(int id, String name, int concept) {
        this.id = id;
        this.name = name;
        this.concept = concept;
    }

    public SubConcept(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubConcept(String name) {
        this.name = name;
    }

    public int getConcept() {
        return concept;
    }

    public void setConcept(int concept) {
        this.concept = concept;
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
