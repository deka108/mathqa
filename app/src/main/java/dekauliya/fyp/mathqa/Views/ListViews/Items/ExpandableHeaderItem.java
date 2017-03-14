package dekauliya.fyp.mathqa.Views.ListViews.Items;

import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

/**
 * Created by dekauliya on 5/2/17.
 */

public abstract class ExpandableHeaderItem<VH extends ExpandableViewHolder, S extends ISectionable>
        extends AbstractExpandableHeaderItem<VH, S> {

    public ExpandableHeaderItem(){
        super.setHidden(false);
        super.setExpanded(false);
        super.setSelectable(false);
    }
}
