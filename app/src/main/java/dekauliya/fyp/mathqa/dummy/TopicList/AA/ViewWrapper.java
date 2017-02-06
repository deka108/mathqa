package dekauliya.fyp.mathqa.dummy.TopicList.AA;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dekauliya on 30/1/17.
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
