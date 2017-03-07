package dekauliya.fyp.mathqa.ListViews.Items;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Formula;
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
 * Created by dekauliya on 5/3/17.
 */

public class SearchResultSubItem extends AbstractSectionableItem<
        SearchResultSubItem.SearchResultSubItemViewHolder,
        IHeader> {

    private boolean showFormula;
    private int index;
    private Formula rel_formula;
    private Question question;

    public SearchResultSubItem(IHeader header, int index, boolean showFormula, Formula rel_formula, Question question) {
        super(header);
        this.index = index;
        this.showFormula = showFormula;
        this.rel_formula = rel_formula;
        this.question = question;
    }

    public boolean isShowFormula() {
        return showFormula;
    }

    public void setShowFormula(boolean showFormula) {
        this.showFormula = showFormula;
    }

    public Question getQuestion() { return question; }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Formula getRel_formula() {
        return rel_formula;
    }

    public void setRel_formula(Formula rel_formula) {
        this.rel_formula = rel_formula;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SearchResultSubItem){
            SearchResultSubItem q = (SearchResultSubItem) o;
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
        return R.layout.question_result_item;
    }

    @Override
    public SearchResultSubItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SearchResultSubItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SearchResultSubItemViewHolder holder, int position, List payloads) {
        if (showFormula) {
            holder.mFormulaResultContainer.setVisibility(View.VISIBLE);
            holder.mFormulaTitle.setText(
                    String.format(holder.mContext.getString(R.string.formula_result_title), index));
            if (rel_formula != null) {
                ViewUtils.displayLatex(holder.mFormulaLatex, holder.mFormulaLatexAlt,
                        rel_formula.getContent(), true);
            } else{
                ViewUtils.toggleDisplay(holder.mFormulaResultUnavailable, holder.mFormulaResultAvailable);
            }
        }else{
            holder.mFormulaResultContainer.setVisibility(View.GONE);
        }

        if (question != null) {
            holder.mQuestionTitle.setText("Question #" + question.getId() + ": ");
            holder.mQuestionDifficulty.setNumStars(5);

            if (question.getDifficulty_level() != null) {
                holder.mQuestionDifficulty.setRating(
                        ViewUtils.getDifficultyLevelFloat(question.getDifficulty_level()));
            }
            ViewUtils.displayLatex(holder.mQuestionContent, holder.mQuestionContentAlt, question
                    .getContent(), false);

            IHeader header = getHeader();
            if (header != null) {
                if (header instanceof ConceptHeaderItem) {
                    holder.mQuestionConcept.setText(((ConceptHeaderItem) header).getConcept().getName
                            ());
                } else if (header instanceof SubConceptHeaderItem) {
                    holder.mQuestionConcept.setText(((SubConceptHeaderItem) header).getSubConcept()
                            .getName());
                }
            }
        }else{
            ViewUtils.toggleDisplay(holder.mQuestionResultUnavailable, holder.mQuestionResultAvailable);
        }
    }

    static class SearchResultSubItemViewHolder extends FlexibleViewHolder {
        Context mContext;

        // containers
        LinearLayout mFormulaResultContainer;
        LinearLayout mFormulaResultAvailable;
        LinearLayout mFormulaResultUnavailable;
        LinearLayout mQuestionResultContainer;
        LinearLayout mQuestionResultAvailable;
        LinearLayout mQuestionResultUnavailable;

        // Elements
        TextView mFormulaTitle;
        MathView mFormulaLatex;
        TextView mFormulaLatexAlt;

        TextView mQuestionTitle;
        MathView mQuestionContent;
        TextView mQuestionContentAlt;

        RatingBar mQuestionDifficulty;
        TextView mQuestionConcept;

        public SearchResultSubItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);

            mContext = view.getContext();
            mFormulaResultContainer = (LinearLayout) view.findViewById(R.id.formula_result_container);
            mFormulaResultAvailable = (LinearLayout) view.findViewById(R.id.formula_result_available);
            mFormulaResultUnavailable = (LinearLayout) view.findViewById(R.id.formula_result_unavailable);

            mFormulaTitle = (TextView) view.findViewById(R.id.formula_title);
            mFormulaLatex = (MathView) view.findViewById(R.id.formula_latex);
            mFormulaLatex.setFocusable(false);
            mFormulaLatex.setFocusableInTouchMode(false);
            mFormulaLatex.setClickable(false);
            mFormulaLatexAlt = (TextView) view.findViewById(R.id.formula_latex_alt);

            mQuestionResultContainer = (LinearLayout) view.findViewById(R.id.question_result_container);
            mQuestionResultAvailable = (LinearLayout) view.findViewById(R.id.question_result_available);
            mQuestionResultUnavailable = (LinearLayout) view.findViewById(R.id.question_result_unavailable);

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
