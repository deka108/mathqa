package dekauliya.fyp.mathqa.ListViews.Items;

import android.animation.Animator;
import android.content.Context;
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
import dekauliya.fyp.mathqa.Utils.ViewUtils;
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

    public Question getQuestion() { return question; }

    public void setQuestion(Question question) {
        this.question = question;
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
    public int hashCode() {
        return this.question.hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.question_item;
    }

    @Override
    public QuestionSubItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new QuestionSubItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, QuestionSubItemViewHolder holder, int position, List payloads) {
        holder.mQuestionTitle.setText("Question #" + question.getId() + ": ");
        holder.mQuestionDifficulty.setNumStars(5);

        if (question.getDifficulty_level() != null) {
            holder.mQuestionDifficulty.setRating(
                    ViewUtils.getDifficultyLevelFloat(question.getDifficulty_level() ));
        }
//            holder.mQuestionContent.setText(ViewUtils.convertLatex(question.getContent()));
        ViewUtils.displayLatex(holder.mQuestionContent, holder.mQuestionContentAlt, question
                    .getContent());

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
        Context mContext;
        TextView mQuestionTitle;
        MathView mQuestionContent;
        TextView mQuestionContentAlt;
        RatingBar mQuestionDifficulty;
        TextView mQuestionConcept;

        public QuestionSubItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);

            mContext = view.getContext();
            mQuestionTitle = (TextView) view.findViewById(R.id.question_title);
            mQuestionDifficulty = (RatingBar) view.findViewById(R.id.question_ratingbar);
            mQuestionContent = (MathView) view.findViewById(R.id.question_latex);
            mQuestionContent.setFocusable(false);
            mQuestionContent.setFocusableInTouchMode(false);
            mQuestionContent.setClickable(false);
            mQuestionContentAlt = (TextView) view.findViewById(R.id.question_latex_alt);
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

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (mAdapter.mItemClickListener != null){
                mAdapter.mItemClickListener.onItemClick(getFlexibleAdapterPosition());
            }
        }
    }

    @Override
    public String toString() {
        return "SubItem[ Question: " + question.getId() + "]";
    }
}
