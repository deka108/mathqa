package dekauliya.fyp.mathqa.UI.TopicList;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.viewholders.FlexibleViewHolder;
import io.github.kexanie.library.MathView;

/**
 * Created by dekauliya on 2/2/17.
 */

public class QuestionSubItem extends AbstractSectionableItem<
        QuestionSubItem.QuestionSubItemViewHolder,
        IHeader> {

    private Question question;

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

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_question_subitem;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  QuestionSubItem){
            QuestionSubItem q = (QuestionSubItem) o;
            return q.question.equals(q.getQuestion());
        }
        return false;
    }

    @Override
    public QuestionSubItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return super.createViewHolder(adapter, inflater, parent);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, QuestionSubItemViewHolder holder, int position, List payloads) {
        holder.mQuestionTitle.setText("Question #" + question.getId() + ": ");
        holder.mQuestionDifficulty.setMax(10);
        holder.mQuestionDifficulty.setNumStars(5);
        holder.mQuestionDifficulty.setRating(Float.parseFloat(question.getDifficultyLevel()));
        holder.mQuestionContent.setText(question.getContent());

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

    static class QuestionSubItemViewHolder extends FlexibleViewHolder{

        TextView mQuestionTitle;
        MathView mQuestionContent;
        RatingBar mQuestionDifficulty;
        TextView mQuestionConcept;

        public QuestionSubItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);

            mQuestionTitle = (TextView) view.findViewById(R.id.rv_question_title);
            mQuestionDifficulty = (RatingBar) view.findViewById(R.id.rv_question_ratingbar);
            mQuestionContent = (MathView) view.findViewById(R.id.rv_subitem_latex);
            mQuestionConcept = (TextView) view.findViewById(R.id.rv_question_concept);
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

    @Override
    public String toString() {
        return "SubItem[ Question: " + question.getId() + "]";
    }
}
