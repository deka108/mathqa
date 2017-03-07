package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 6/3/17.
 */
@Parcel
public class SearchResult {
    Question question;
    Formula rel_formula;

    @ParcelConstructor
    public SearchResult(Question question, Formula rel_formula) {
        this.question = question;
        this.rel_formula = rel_formula;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Formula getRel_formula() {
        return rel_formula;
    }

    public void setRel_formula(Formula rel_formula) {
        this.rel_formula = rel_formula;
    }
}
