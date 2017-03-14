package dekauliya.fyp.mathqa.Views.ListViews.Items;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dekauliya.fyp.mathqa.Models.KeyPoint;
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

public class KeyPointSubItem extends AbstractSectionableItem<KeyPointSubItem
        .KeyPointSubItemViewHolder, IHeader> {

    public KeyPointSubItem(IHeader header) {
        super(header);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof KeyPointSubItem){
            KeyPointSubItem k = (KeyPointSubItem) o;
            return getHeader().equals(k.getHeader());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((KeyPointHeaderItem) getHeader()).hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.latex_item;
    }

    @Override
    public KeyPointSubItemViewHolder createViewHolder(FlexibleAdapter adapter,
                                                      LayoutInflater inflater, ViewGroup parent) {
        return new KeyPointSubItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, KeyPointSubItemViewHolder holder, int position, List payloads) {
        IHeader header = getHeader();
        if (header != null && header instanceof KeyPointHeaderItem) {
            KeyPoint keypoint = ((KeyPointHeaderItem) getHeader()).getKeyPoint();
//            holder.mMathView.setText(ViewUtils.convertLatex(keypoint.getContent()));
            ViewUtils.displayLatex(holder.mMathView, holder.mMathViewAlt, keypoint.getContent(),
                    false);
        }
    }

    static class KeyPointSubItemViewHolder extends FlexibleViewHolder{
        Context mContext;
        MathView mMathView;
        TextView mMathViewAlt;

        public KeyPointSubItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mContext = view.getContext();
            mMathView = (MathView) view.findViewById(R.id.latex_item_latex);
            mMathView.setFocusable(false);
            mMathView.setFocusableInTouchMode(false);
            mMathView.setClickable(false);
            mMathViewAlt = (TextView) view.findViewById(R.id.latex_item_latex_alt);
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
        return "SubItem[Keypoint: " + getHeader().toString() + "]";
    }
}
