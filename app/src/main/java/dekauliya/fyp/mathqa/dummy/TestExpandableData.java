package dekauliya.fyp.mathqa.dummy;

/**
 * Created by dekauliya on 29/1/17.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.dummy.RecyclerView.Data;

public class TestExpandableData {
//    public static HashMap<String, List<String>> getData() {
    public static HashMap<String, List<Question>> getData() {
//        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        HashMap<String, List<Question>> expandableListDetail = new HashMap<>();


        List<String> asia = new ArrayList<String>();
        asia.add("Japan");
        asia.add("China");
        asia.add("India");
        asia.add("Singapore");
        asia.add("Indonesia");

        List<String> europe = new ArrayList<String>();
        europe.add("France");
        europe.add("Spain");
        europe.add("Germany");
        europe.add("Netherlands");
        europe.add("Italy");

        List<String> america = new ArrayList<String>();
        america.add("United States");
        america.add("Canada");
        america.add("Argentina");
        america.add("Brazil");
        america.add("Mexico");

//        expandableListDetail.put("asia", asia);
//        expandableListDetail.put("europe", europe);
//        expandableListDetail.put("america", america);
        int count = 0;
        for(int i=0; i<3; i++) {
            List<Question> questions = new ArrayList<>();
            for (Question q : Data.qs) {
                questions.add(q);
            }
            expandableListDetail.put("Question " + ++count, questions);
        }

        return expandableListDetail;
    }
}