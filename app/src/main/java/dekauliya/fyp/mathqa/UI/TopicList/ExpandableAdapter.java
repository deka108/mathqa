package dekauliya.fyp.mathqa.UI.TopicList;

import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by dekauliya on 31/1/17.
 */

public class ExpandableAdapter extends FlexibleAdapter<AbstractFlexibleItem> {
    public ExpandableAdapter(@Nullable List items, @Nullable Object listeners) {
        super(items, listeners, true);

    }
}
