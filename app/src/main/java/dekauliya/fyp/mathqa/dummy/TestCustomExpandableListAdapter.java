package dekauliya.fyp.mathqa.dummy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 29/1/17.
 */

public class TestCustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
//    private HashMap<String, List<String>> expandableListDetail;
    private HashMap<String, List<Question>> expandableListDetail;

//    public TestCustomExpandableListAdapter(Context context, List<String> expandableListTitle,
//                                           HashMap<String, List<String>> expandableListDetail) {
//        this.context = context;
//        this.expandableListTitle = expandableListTitle;
//        this.expandableListDetail = expandableListDetail;
//    }

    public TestCustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                           HashMap<String, List<Question>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup
            viewGroup) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.list_item, null);
            convertView = layoutInflater.inflate(R.layout.question_item, null);
        }

//        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
//        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
