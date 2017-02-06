package dekauliya.fyp.mathqa.ListViews.Items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.ExpandableViewHolder;

/**
 * Created by dekauliya on 2/2/17.
 */

public class ConceptHeaderItem extends ExpandableHeaderItem<ConceptHeaderItem
        .ConceptHeaderItemViewHolder, QuestionSubItem> {

    private Concept concept;

    public ConceptHeaderItem(Concept concept) {
        super();
        this.concept = concept;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ConceptHeaderItem){
            ConceptHeaderItem c = (ConceptHeaderItem) o;
            return this.concept.equals(c.getConcept());
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.header_text_content;
    }

    @Override
    public ConceptHeaderItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ConceptHeaderItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ConceptHeaderItemViewHolder holder, int position, List payloads) {
        if (payloads.size() > 0) {
            Logger.d("ExpandableHeaderItem Payload " + payloads);
        } else {
            holder.mConceptTitle.setText(concept.getName());
        }
        holder.mConceptSubtitle.setText(getSubItemsCount() + " questions available.");
    }

    static class ConceptHeaderItemViewHolder extends ExpandableViewHolder{
        TextView mConceptTitle;
        TextView mConceptSubtitle;

        public ConceptHeaderItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true);
            mConceptTitle = (TextView) view.findViewById(R.id.header_title_text);
            mConceptSubtitle = (TextView) view.findViewById(R.id.header_subtitle_text);
        }

        @Override
        public float getActivationElevation() {
            return GraphicUtils.dpToPx(itemView.getContext(), 4f);
        }

        @Override
        protected boolean shouldNotifyParentOnClick() {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Concept[" + concept.getName() + "//SubItems" + super.getSubItems() + "]";
    }
}
