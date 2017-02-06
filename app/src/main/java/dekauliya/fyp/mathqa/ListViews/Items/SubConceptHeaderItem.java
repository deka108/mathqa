package dekauliya.fyp.mathqa.ListViews.Items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.ExpandableViewHolder;

/**
 * Created by dekauliya on 2/2/17.
 */

public class SubConceptHeaderItem extends ExpandableHeaderItem<
        SubConceptHeaderItem.SubConceptHeaderItemViewHolder, QuestionSubItem>{
    private SubConcept subConcept;

    public SubConceptHeaderItem(SubConcept subConcept) {
        super();
        this.subConcept = subConcept;
    }

    public SubConcept getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(SubConcept subConcept) {
        this.subConcept = subConcept;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.header_text_content;
    }

    @Override
    public SubConceptHeaderItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SubConceptHeaderItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SubConceptHeaderItemViewHolder holder, int position, List payloads) {
        if (payloads.size() > 0) {
            Logger.d("ExpandableHeaderItem Payload " + payloads);
        } else {
            holder.mSubConceptTitle.setText(subConcept.getName());
        }
        holder.mSubConceptSubtitle.setText(getSubItemsCount() + " questions available.");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubConceptHeaderItem){
            SubConceptHeaderItem s = (SubConceptHeaderItem) o;
            return this.subConcept.equals(s.getSubConcept());
        }
        return false;
    }

    static class SubConceptHeaderItemViewHolder extends ExpandableViewHolder{
        TextView mSubConceptTitle;
        TextView mSubConceptSubtitle;

        public SubConceptHeaderItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true);
            mSubConceptTitle = (TextView) view.findViewById(R.id.header_title_text);
            mSubConceptSubtitle = (TextView) view.findViewById(R.id.header_subtitle_text);
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
        return "SubConcept[" + subConcept.getName() + "//Questions" + super.getSubItems() + "]";
    }
}
