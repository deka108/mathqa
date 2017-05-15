package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 * 
 * Keyword Java object model equivalent with Keyword table in MathQA database.
 */
@Parcel
public class Keyword {
     int id;
     String name;
     String content;

    @ParcelConstructor
    public Keyword(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public Keyword(String name, String content) {
        this.name = name;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
