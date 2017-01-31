package dekauliya.fyp.mathqa.Services;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaService;
import dekauliya.fyp.mathqa.UI.TopicList.ConceptItem;
import dekauliya.fyp.mathqa.UI.TopicList.TopicHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dekauliya on 31/1/17.
 */

public class DataService {
    private DataType mDataType = DataType.NONE;
    private static DataService mInstance;
    private List<AbstractFlexibleItem> mItems = new ArrayList<AbstractFlexibleItem>();
    MathQaRestRxJavaApi client = MathQaRestRxJavaService.createService(MathQaRestRxJavaApi.class);
    HashMap<Topic, ArrayList<Concept>> topicConcepts = new HashMap();

    private DataService(){

    }

    public static DataService getInstance(){
        if (mInstance == null){
            mInstance = new DataService();
        }
        return mInstance;
    }

    public void createExpandableData(){
        mItems.clear();
        String[] family = {"Akbar", "Yusro"};
        String[] address = {"Lived in Bogor", "Lived in Purwokerto"};
        String[][] names = {{"Deka", "Refo", "Amanah", "Akbar"}, {"Abram", "Adel", "Fawwaz",
                "Maharani", "Yusro"}};
        for(int i=0; i<family.length; i++){
            Topic topic = new Topic(i, family[i]);
            TopicHeaderItem topicHeader = new TopicHeaderItem(topic);
            for(int j=0; j<names[i].length; j++) {
                Concept concept = new Concept(names[i][j]);
                ConceptItem conceptItem = new ConceptItem(concept, topicHeader);
                topicHeader.addSubItem(conceptItem);
            }
            mItems.add(topicHeader);
        }
    }

    public void getTopicConceptData(int subjectId){
        topicConcepts.clear();

        client.getTopics(subjectId)
                .flatMap(new Function<List<Topic>, ObservableSource<Topic>>() {
                    @Override
                    public ObservableSource<Topic> apply(List<Topic> topics) throws Exception {
                        return Observable.fromIterable(topics);
                    }
                })
                .flatMap(new Function<Topic, Observable<List<Concept>>>() {
                    @Override
                    public Observable<List<Concept>> apply(Topic topic) throws Exception {
                        if (!topicConcepts.containsKey(topic)){
                            topicConcepts.put(topic, new ArrayList<Concept>());
                        }
                        return client.getConcepts(topic.getId());
                    }
                })
                .flatMap(new Function<List<Concept>, ObservableSource<Concept>>() {
                    @Override
                    public ObservableSource<Concept> apply(List<Concept> concepts) throws
                            Exception {
                        return Observable.fromIterable(concepts);
                    }
                })
                .flatMap(new Function<Concept, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Concept concept) throws Exception {
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * @return Always a copy of the original list.
     */
    public List<AbstractFlexibleItem> getData() {
        Logger.i("Database Type: " + mDataType);
        //Return a copy of the DB: we will perform some tricky code on this list.
        return new ArrayList<>(mItems);
    }

    public static void onDestroy() {
        mInstance = null;
    }
}
