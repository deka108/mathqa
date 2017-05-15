package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 * 
 * Topic Java object model equivalent with Topic table in MathQA database.
 */
@Parcel
public class Topic {
    int id;
    String name;
    int subject;

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Topic(int id, String name, int subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    @ParcelConstructor
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
