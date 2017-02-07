package dekauliya.fyp.mathqa.ListViews.Items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.ExpandableViewHolder;
/**
 * Created by dekauliya on 30/1/17.
 */

public class TopicHeaderItem extends ExpandableHeaderItem<
        TopicHeaderItem.TopicHeaderItemViewHolder, ConceptSubItem> {

    private Topic topic;

    public TopicHeaderItem(Topic topic){
        super();
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TopicHeaderItem){
            TopicHeaderItem item = (TopicHeaderItem) o;
            return this.topic.equals(item.getTopic());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.topic.hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.header_text_content;
    }

    @Override
    public TopicHeaderItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new TopicHeaderItemViewHolder(inflater.inflate(getLayoutRes(), parent, false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, TopicHeaderItemViewHolder holder, int position, List payloads) {
        if (payloads.size() > 0) {
            Logger.d("ExpandableHeaderItem Payload " + payloads);
        } else {
            holder.mTopicTitle.setText(topic.getName());
        }
        holder.mTopicSubtitle.setText(getSubItemsCount() + " Concepts available");
    }

    static class TopicHeaderItemViewHolder extends ExpandableViewHolder {
        TextView mTopicTitle;
        TextView mTopicSubtitle;

        public TopicHeaderItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true); // sticky
            mTopicTitle = (TextView) view.findViewById(R.id.header_title_text);
            mTopicSubtitle = (TextView) view.findViewById(R.id.header_subtitle_text);
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
        return "TopicHeader[" + topic.getName() + "//Concepts" + super.getSubItems() + "]";
    }
}