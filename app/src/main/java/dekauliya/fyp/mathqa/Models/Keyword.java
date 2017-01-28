package dekauliya.fyp.mathqa.Models;

/**
 * Created by dekauliya on 29/1/17.
 */

public class Keyword {
    private String name;
    private String content;

    public Keyword(String name, String content) {
        this.name = name;
        this.content = content;
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
