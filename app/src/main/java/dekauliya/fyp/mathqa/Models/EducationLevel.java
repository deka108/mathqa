package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 *
 * EducationLevel Java object model equivalent with EducationLevel table in MathQA database.
 */
@Parcel
public class EducationLevel {
    int id;
    String name;
    String description;

    public EducationLevel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @ParcelConstructor
    public EducationLevel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
