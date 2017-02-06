package dekauliya.fyp.mathqa.dummy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.R;

public class TestExpandableActivity extends AppCompatActivity {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> expandableListTitle;
//    HashMap<String, List<String>> expandableListDetail;
    HashMap<String, List<Question>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expandable);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = TestExpandableData.getData();
//        expandableListTitle =  new ArrayList<String>(expandableListDetail.keySet());
        expandableListTitle =  new ArrayList<>(expandableListDetail.keySet());
//        expandableListAdapter = new TestCustomExpandableListAdapter(this, expandableListTitle,
//                expandableListDetail);
        expandableListAdapter = new TestCustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) +
                        " list expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener(){

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) +
                        " list collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parentView, View view, int groupPosition,
                                        int childPosition,
                                        long id) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) +
                        " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition))
                        .get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}
