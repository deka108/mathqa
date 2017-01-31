package dekauliya.fyp.mathqa.UI.TopicList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.R;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.viewholders.ExpandableViewHolder;
/**
 * Created by dekauliya on 30/1/17.
 */

public class TopicHeaderItem extends AbstractExpandableHeaderItem<
        TopicHeaderItem.TopicHeaderItemViewHolder, ConceptItem> {

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
    public int getLayoutRes() {
        return R.layout.recycler_topic_header;
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
        holder.mTopicSubtitle.setText("Test subtitle");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TopicHeaderItem){
            TopicHeaderItem item = (TopicHeaderItem) o;
            return this.topic.equals(item.getTopic());
        }
        return false;
    }

    static class TopicHeaderItemViewHolder extends ExpandableViewHolder {
        TextView mTopicTitle;
        TextView mTopicSubtitle;

        public TopicHeaderItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, true); // sticky
            mTopicTitle = (TextView) view.findViewById(R.id.topic_title);
            mTopicSubtitle = (TextView) view.findViewById(R.id.topic_subtitle);
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