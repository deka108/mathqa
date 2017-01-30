package dekauliya.fyp.mathqa.dummy;

/**
 * Created by dekauliya on 29/1/17.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestExpandableData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

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

        expandableListDetail.put("asia", asia);
        expandableListDetail.put("europe", europe);
        expandableListDetail.put("america", america);

        return expandableListDetail;
    }
}