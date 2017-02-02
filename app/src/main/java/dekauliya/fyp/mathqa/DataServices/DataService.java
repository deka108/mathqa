package dekauliya.fyp.mathqa.DataServices;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.KeyPoint;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestRxJavaService;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestService;
import dekauliya.fyp.mathqa.UI.TopicList.ConceptHeaderItem;
import dekauliya.fyp.mathqa.UI.TopicList.ConceptSubItem;
import dekauliya.fyp.mathqa.UI.TopicList.FlexibleAdapter.AbstractItem;
import dekauliya.fyp.mathqa.UI.TopicList.IDataListener;
import dekauliya.fyp.mathqa.UI.TopicList.KeyPointHeaderItem;
import dekauliya.fyp.mathqa.UI.TopicList.KeyPointSubItem;
import dekauliya.fyp.mathqa.UI.TopicList.QuestionSubItem;
import dekauliya.fyp.mathqa.UI.TopicList.SubConceptHeaderItem;
import dekauliya.fyp.mathqa.UI.TopicList.TopicHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IExpandable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.flexibleadapter.items.ISectionable;
import retrofit2.Call;

/**
 * Created by dekauliya on 31/1/17.
 */


public class DataService {
    private static DataService mInstance;
    private List<AbstractFlexibleItem> mItems = new ArrayList<>();
    MathQaRestRxJavaApi clientObservable = MathQaRestRxJavaService.createService(MathQaRestRxJavaApi
            .class);
    MathQaRestApi client = MathQaRestService.createService(MathQaRestApi.class);

    private int mCurrentConceptId;
    HashMap<Integer, SubConcept> mapSubConcept = new HashMap<>();

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
                ConceptSubItem conceptSubItem = new ConceptSubItem((IHeader) topicHeader, concept);
                topicHeader.addSubItem(conceptSubItem);
            }
            mItems.add(topicHeader);
        }
    }


    public void getTopicConceptData(int subjectId, IDataListener listener){
        mItems.clear();
        Call<List<Topic>> callTopic = client.getTopics(subjectId);
        try {
            List<Topic> topics = callTopic.execute().body();
            for(Topic topic: topics){
                TopicHeaderItem topicHeaderItem = new TopicHeaderItem(topic);
                Call<List<Concept>> callConcept = client.getConcepts(topic.getId());
                List<Concept> concepts = callConcept.execute().body();
                for(Concept concept: concepts){
                    ConceptSubItem conceptSubItem = new ConceptSubItem(
                            (IHeader) topicHeaderItem, concept);
                    topicHeaderItem.addSubItem(conceptSubItem);
                }
                Collections.sort(topicHeaderItem.getSubItems(), conceptItemComparator);
                addItem(topicHeaderItem, mComparator);
                listener.onDataRetrieved();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the topic items: " + mItems.size());
    }

    public void getConceptKeypointData(Concept concept, IDataListener listener){
        mItems.clear();
        Call<List<KeyPoint>> callKeypoint = client.getKeypointConcepts(concept.getId());
        try {
            List<KeyPoint> keypoints = callKeypoint.execute().body();
            for(KeyPoint keypoint: keypoints) {
                KeyPointHeaderItem keyPointHeaderItem = new KeyPointHeaderItem(keypoint);
                KeyPointSubItem keyPointSubItem = new KeyPointSubItem((IHeader) keyPointHeaderItem,
                        keypoint);
                keyPointHeaderItem.addSubItem(keyPointSubItem);
                addItem(keyPointHeaderItem, mComparator);
            }
            listener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the keypoint items: " + mItems.size());
    }


    public void getConceptFormulaData(Concept concept, IDataListener listener){
        mItems.clear();
        Call<List<KeyPoint>> callKeypoint = client.getKeypointFormulas(concept.getId());
        try {
            List<KeyPoint> keypoints = callKeypoint.execute().body();
            for(KeyPoint keypoint: keypoints) {
                KeyPointHeaderItem keyPointHeaderItem = new KeyPointHeaderItem(keypoint);
                KeyPointSubItem keyPointSubItem = new KeyPointSubItem((IHeader) keyPointHeaderItem,
                        keypoint);
                keyPointHeaderItem.addSubItem(keyPointSubItem);
                addItem(keyPointHeaderItem, mComparator);
            }
            listener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the formula items: " + mItems.size());
    }


    public void getQuestionConceptData(Concept concept, IDataListener listener){
        mItems.clear();
        Call<List<SubConcept>> callSubConcept = client.getSubConcepts(null);

        try {
            List<SubConcept> subConcepts = callSubConcept.execute().body();
            for(SubConcept subConcept: subConcepts){
                Call<List<Question>> callQuestion = client.getQuestions(null, subConcept.getId());
                List<Question> questions = callQuestion.execute().body();
                SubConceptHeaderItem subConceptHeaderItem = new SubConceptHeaderItem(subConcept);

                for(Question question: questions) {
                    QuestionSubItem questionSubItem = new QuestionSubItem(subConceptHeaderItem,
                            question);
                    subConceptHeaderItem.addSubItem(questionSubItem);
                }

                addItem(subConceptHeaderItem, mComparator);
            }
            listener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the formula items: " + mItems.size());
    }


    public void getQuestionTopicData(int subjectId, IDataListener listener){
        mItems.clear();
        Call<List<Topic>> callTopic = client.getTopics(subjectId);

        try {
            List<Topic> topics = callTopic.execute().body();
            // for each topic
            for(Topic topic: topics){
                Call<List<Concept>> callConcept = client.getConcepts(topic.getId());
                List<Concept> concepts = callConcept.execute().body();

                // for each concept
                for(Concept concept: concepts) {
                    Call<List<Question>> callQuestion = client.getQuestions(concept.getId(), null);
                    List<Question> questions = callQuestion.execute().body();
                    ConceptHeaderItem conceptHeaderItem= new ConceptHeaderItem(concept);

                    // for each question
                    for (Question question : questions) {
                        QuestionSubItem questionSubItem = new QuestionSubItem(conceptHeaderItem,
                                question);
                        conceptHeaderItem.addSubItem(questionSubItem);
                    }
                    addItem(conceptHeaderItem, mComparator);
                }
            }
            listener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the formula items: " + mItems.size());
    }


    private void updateSubConcept(Concept concept) {
        if (mapSubConcept.isEmpty() || concept.getId() != mCurrentConceptId){
            Call<List<SubConcept>> callSubConcept = client.getSubConcepts(null);
            List<SubConcept> subConcepts = null;
            try {
                subConcepts = callSubConcept.execute().body();
                for(SubConcept subConcept: subConcepts){
                    mapSubConcept.put(subConcept.getId(), subConcept);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCurrentConceptId = concept.getId();
        }
    }

    /**
     * @return Always a copy of the original list.
     */
    public List<AbstractFlexibleItem> getData() {
        //Return a copy of the DB: we will perform some tricky code on this list.
        return new ArrayList<>(mItems);
    }

    public void swapItem(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
    }

    public void removeItem(IFlexible item) {
        mItems.remove(item);
    }

    public void removeSubItem(IExpandable parent, ISectionable child) {
        //This split is for my examples
        if (parent instanceof TopicHeaderItem)
            ((TopicHeaderItem) parent).removeSubItem((ConceptSubItem) child);
    }

    public void removeAll() {
        mItems.clear();
    }

    public void addAll(List<AbstractFlexibleItem> newItems) {
        mItems.addAll(newItems);
    }

    public void addItem(int position, AbstractItem item) {
        if (position < mItems.size())
            mItems.add(position, item);
        else
            addItem(item);
    }


    public void addItem(AbstractFlexibleItem item, Comparator comparator) {
        addItem(item);
        Collections.sort(mItems, comparator);
    }

    public void addItem(AbstractFlexibleItem item) {
        mItems.add(item);
    }

    public void addSubItem(int position, IExpandable parent, ISectionable subItem) {
        //This split is for my examples
        if (parent instanceof TopicHeaderItem)
            ((TopicHeaderItem) parent).addSubItem((ConceptSubItem) subItem);
    }

    /**
     * This demonstrates that new content of existing items are really rebound and
     * notified with CHANGE Payload in the Adapter list when refreshed.
     */
    public void updateNewItems() {
        for (IFlexible iFlexible : mItems) {
            if (iFlexible instanceof AbstractItem) {
                AbstractItem item = (AbstractItem) iFlexible;
                item.increaseUpdates();
            }
        }
    }

    public static void onDestroy() {
        mInstance = null;
    }

    Comparator<AbstractFlexibleItem> mComparator = new Comparator<AbstractFlexibleItem>() {

        @Override
        public int compare(AbstractFlexibleItem o1, AbstractFlexibleItem o2) {
            if (o1 instanceof ConceptHeaderItem && o2 instanceof ConceptHeaderItem){
                return ((ConceptHeaderItem) o1).getConcept().getName()
                        .compareTo(((ConceptHeaderItem) o2).getConcept().getName());
            } else if (o1 instanceof ConceptSubItem && o2 instanceof ConceptSubItem){
                return ((ConceptSubItem) o1).getConcept().getName()
                        .compareTo(((ConceptSubItem) o2).getConcept().getName());
            } else if (o1 instanceof KeyPointHeaderItem && o2 instanceof KeyPointHeaderItem){
                return ((KeyPointHeaderItem) o1).getKeyPoint().getName()
                        .compareTo(((KeyPointHeaderItem) o2).getKeyPoint().getName());
            } else if (o1 instanceof KeyPointSubItem && o2 instanceof KeyPointSubItem){
                return ((KeyPointSubItem) o1).getKeyPoint().getName()
                        .compareTo(((KeyPointSubItem) o2).getKeyPoint().getName());
            } else if (o1 instanceof QuestionSubItem && o2 instanceof QuestionSubItem) {
                return ((QuestionSubItem) o1).getQuestion().getId()
                        .compareTo(((QuestionSubItem) o2).getQuestion().getId());
            } else if (o1 instanceof TopicHeaderItem && o2 instanceof TopicHeaderItem) {
                return ((TopicHeaderItem) o1).getTopic().getName()
                        .compareTo(((TopicHeaderItem) o2).getTopic().getName());
            } else if (o1 instanceof SubConceptHeaderItem && o2 instanceof SubConceptHeaderItem) {
                return ((SubConceptHeaderItem) o1).getSubConcept().getName()
                        .compareTo(((SubConceptHeaderItem) o2).getSubConcept().getName());
            }
            return 0;
        }
    };

    Comparator<ConceptSubItem> conceptItemComparator = new Comparator<ConceptSubItem>() {

        @Override
        public int compare(ConceptSubItem c1, ConceptSubItem c2) {
            return c1.getConcept().getName().compareTo(c2.getConcept().getName());
        }
    };
}
