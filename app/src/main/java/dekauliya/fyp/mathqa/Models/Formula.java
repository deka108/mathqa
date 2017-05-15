package dekauliya.fyp.mathqa.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dekauliya on 6/3/17.
 *
 * Formula Java object model equivalent with Formula table in MathQA database.
 */
@Parcel
public class Formula {
    int id;
    String content;
    String[] categories;
    boolean status;
    String inorder_term;
    String structure_term;
    String constant_term;
    String variable_term;
    String[] questions;
    int concept;

    @ParcelConstructor
    public Formula(int id, String content, String[] categories, boolean status, String inorder_term,
                   String structure_term, String constant_term, String variable_term,
                   String[] questions, int concept) {
        this.id = id;
        this.content = content;
        this.categories = categories;
        this.status = status;
        this.inorder_term = inorder_term;
        this.structure_term = structure_term;
        this.constant_term = constant_term;
        this.variable_term = variable_term;
        this.questions = questions;
        this.concept = concept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInorder_term() {
        return inorder_term;
    }

    public void setInorder_term(String inorder_term) {
        this.inorder_term = inorder_term;
    }

    public String getStructure_term() {
        return structure_term;
    }

    public void setStructure_term(String structure_term) {
        this.structure_term = structure_term;
    }

    public String getConstant_term() {
        return constant_term;
    }

    public void setConstant_term(String constant_term) {
        this.constant_term = constant_term;
    }

    public String getVariable_term() {
        return variable_term;
    }

    public void setVariable_term(String variable_term) {
        this.variable_term = variable_term;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public int getConcept() {
        return concept;
    }

    public void setConcept(int concept) {
        this.concept = concept;
    }
}
