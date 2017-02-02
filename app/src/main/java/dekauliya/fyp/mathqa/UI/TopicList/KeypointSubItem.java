package dekauliya.fyp.mathqa.UI.TopicList;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dekauliya.fyp.mathqa.Models.KeyPoint;
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

public class KeyPointSubItem extends AbstractSectionableItem<KeyPointSubItem
        .KeyPointSubItemViewHolder, IHeader> {

    private KeyPoint keyPoint;

    public KeyPointSubItem(IHeader header, KeyPoint keyPoint) {
        super(header);
        this.keyPoint = keyPoint;
    }


    public KeyPoint getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(KeyPoint keyPoint) {
        this.keyPoint = keyPoint;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_mathview_subitem;
    }

    @Override
    public KeyPointSubItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new KeyPointSubItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, KeyPointSubItemViewHolder holder, int position, List payloads) {
        holder.mMathView.setText(getKeyPoint().getContent());
    }

    static class KeyPointSubItemViewHolder extends FlexibleViewHolder{

        MathView mMathView;

        public KeyPointSubItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mMathView = (MathView) mMathView.findViewById(R.id.rv_subitem_latex);
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
        return "SubItem[Keypoint: " + keyPoint.getName() + "]";
    }
}
