package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class SubConcept {
    int id;
    String name;
    int concept;

    @ParcelConstructor
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
