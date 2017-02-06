package dekauliya.fyp.mathqa.dummy.dummy;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import dekauliya.fyp.mathqa.ListViews.Items.ConceptHeaderItem;
import dekauliya.fyp.mathqa.ListViews.Items.SubConceptHeaderItem;
import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.viewholders.FlexibleViewHolder;
import io.github.kexanie.library.MathView;

/**
 * Created by dekauliya on 5/2/17.
 */

public class QuestionSubItem extends AbstractSectionableItem<QuestionSubItem.QuestionItemViewHolder,
        IHeader> {

    private Question question;
    private Concept concept;
    private SubConcept subConcept;

    public QuestionSubItem(IHeader header, Question question) {
        super(header);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public SubConcept getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(SubConcept subConcept) {
        this.subConcept = subConcept;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.question_item;
    }

    @Override
    public QuestionItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new QuestionItemViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, QuestionItemViewHolder holder, int position, List payloads) {
        holder.mQuestionTitle.setText("Question #" + question.getId() + ": ");
        holder.mQuestionDifficulty.setMax(10);
        holder.mQuestionDifficulty.setNumStars(5);
        if (question.getDifficulty_level() != null) {
            holder.mQuestionDifficulty.setRating(Float.parseFloat(question.getDifficulty_level()));
        }
        if (question.getContent() != null) {
            holder.mQuestionContent.setText(question.getContent());
        }

        IHeader header = getHeader();
        if (header != null){
            if (header instanceof ConceptHeaderItem) {
                holder.mQuestionConcept.setText(((ConceptHeaderItem) header).getConcept().getName
                        ());
            }else if (header instanceof SubConceptHeaderItem){
                holder.mQuestionConcept.setText(((SubConceptHeaderItem) header).getSubConcept()
                        .getName());
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof QuestionSubItem){
            QuestionSubItem q = (QuestionSubItem) o;
            return q.question.equals(q.getQuestion());
        }
        return false;
    }

    static class QuestionItemViewHolder extends FlexibleViewHolder {

        TextView mQuestionTitle;
        MathView mQuestionContent;
        RatingBar mQuestionDifficulty;
        TextView mQuestionConcept;

        public QuestionItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);

            mQuestionTitle = (TextView) view.findViewById(R.id.question_title);
            mQuestionDifficulty = (RatingBar) view.findViewById(R.id.question_ratingbar);
            mQuestionContent = (MathView) view.findViewById(R.id.latex_item_latex);
            mQuestionConcept = (TextView) view.findViewById(R.id.question_concept);
        }

        @Override
        public float getActivationElevation() {
            return GraphicUtils.dpToPx(itemView.getContext(), 4f);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }
    }
}
