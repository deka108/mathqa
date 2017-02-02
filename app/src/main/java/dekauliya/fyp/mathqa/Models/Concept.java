package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 29/1/17.
 */
@Parcel
public class Concept {
    int id;
    String name;
    int topic;

    public Concept(int id, String name, int topic) {
        this.id = id;
        this.name = name;
        this.topic = topic;
    }

    public Concept(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @ParcelConstructor
    public Concept(String name) {
        this.name = name;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
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
}
