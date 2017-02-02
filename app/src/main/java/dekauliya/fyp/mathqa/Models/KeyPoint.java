package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class KeyPoint {
    int id;
    String name;
    String type;
    String content;
    int concept;

    public KeyPoint(int id, String name, String type, String content, int concept) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
        this.concept = concept;
    }

    public KeyPoint(int id, String name, String type, String content) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
    }

    @ParcelConstructor
    public KeyPoint(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public int getConcept() {
        return concept;
    }

    public void setConcept(int concept) {
        this.concept = concept;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
