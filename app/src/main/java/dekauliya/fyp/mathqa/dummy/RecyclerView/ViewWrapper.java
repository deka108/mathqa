package dekauliya.fyp.mathqa.dummy.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dekauliya on 4/2/17.
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

