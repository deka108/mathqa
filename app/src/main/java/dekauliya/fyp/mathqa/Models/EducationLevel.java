package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class EducationLevel {
    private String name;
    private String description;

    public EducationLevel(String name, String description) {
        this.name = name;
        this.description = description;
    }

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
