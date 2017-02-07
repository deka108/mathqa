package dekauliya.fyp.mathqa.DataServices;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dekauliya.fyp.mathqa.ListViews.Items.ConceptHeaderItem;
import dekauliya.fyp.mathqa.ListViews.Items.ConceptSubItem;
import dekauliya.fyp.mathqa.ListViews.Items.KeyPointHeaderItem;
import dekauliya.fyp.mathqa.ListViews.Items.KeyPointSubItem;
import dekauliya.fyp.mathqa.ListViews.Items.QuestionSubItem;
import dekauliya.fyp.mathqa.ListViews.Items.SubConceptHeaderItem;
import dekauliya.fyp.mathqa.ListViews.Items.TopicHeaderItem;
import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.KeyPoint;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.SubConcept;
import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestService;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import retrofit2.Call;

/**
 * Created by dekauliya on 4/2/17.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataService {
    MathQaRestApi client = MathQaRestService.createService(MathQaRestApi.class);

    List<AbstractFlexibleItem> mItems = new ArrayList<AbstractFlexibleItem>();
    int mCurrentSubjectId = -1;

    public void getTopicConceptData(int subjectId, IDataListener mListener){
        mItems.clear();
        Call<List<Topic>> callTopic = client.getTopics(subjectId);
        try {
            // Topics
            List<Topic> topics = callTopic.execute().body();
            for(Topic topic: topics){
                TopicHeaderItem topicHeaderItem = new TopicHeaderItem(topic);
                Call<List<Concept>> callConcept = client.getConcepts(topic.getId());
                List<Concept> concepts = callConcept.execute().body();

                // Concepts
                for(Concept concept: concepts){
                    ConceptSubItem conceptSubItem = new ConceptSubItem(topicHeaderItem, concept);
                    topicHeaderItem.addSubItem(conceptSubItem);
                }

                Collections.sort(topicHeaderItem.getSubItems(), mComparator);
                addItem(topicHeaderItem, mComparator);
//                mListener.onDataRetrieved();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the TopicConcept data items: " + mItems.size());
    }

    public void getConceptKeypointData(Concept concept, IDataListener mListener){
        mItems.clear();
        Call<List<KeyPoint>> callKeypoint = client.getKeypointConcepts(concept.getId());
        try {
            List<KeyPoint> keypoints = callKeypoint.execute().body();
            // Keypoints
            for(KeyPoint keypoint: keypoints) {
                KeyPointHeaderItem keyPointHeaderItem = new KeyPointHeaderItem(keypoint);
                KeyPointSubItem keyPointSubItem = new KeyPointSubItem(keyPointHeaderItem);
                keyPointHeaderItem.addSubItem(keyPointSubItem);
                addItem(keyPointHeaderItem, mComparator);
            }
//            mListener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the ConceptItems: " + mItems.size());
    }


    public void getConceptFormulaData(Concept concept, IDataListener mListener){
        mItems.clear();
        Call<List<KeyPoint>> callKeypoint = client.getKeypointFormulas(concept.getId());
        try {
            // Keypoints
            List<KeyPoint> keypoints = callKeypoint.execute().body();
            for(KeyPoint keypoint: keypoints) {
                KeyPointHeaderItem keyPointHeaderItem = new KeyPointHeaderItem(keypoint);
                KeyPointSubItem keyPointSubItem = new KeyPointSubItem(keyPointHeaderItem);
                keyPointHeaderItem.addSubItem(keyPointSubItem);
                addItem(keyPointHeaderItem, mComparator);
            }
//            mListener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the formula items: " + mItems.size());
    }


    public void getQuestionConceptData(Concept concept, IDataListener mListener){
        mItems.clear();
        Call<List<SubConcept>> callSubConcept = client.getSubConcepts(null);

        try {
            // Subconcepts
            List<SubConcept> subConcepts = callSubConcept.execute().body();
            for(SubConcept subConcept: subConcepts){
                Call<List<Question>> callQuestion = client.getQuestions(null, subConcept.getId());
                List<Question> questions = callQuestion.execute().body();
                SubConceptHeaderItem subConceptHeaderItem = new SubConceptHeaderItem(subConcept);

                // Questions
                for(Question question: questions) {
                    QuestionSubItem questionSubItem = new QuestionSubItem(subConceptHeaderItem,
                            question);
                    subConceptHeaderItem.addSubItem(questionSubItem);
                }
                addItem(subConceptHeaderItem, mComparator);
            }
//            mListener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the formula items: " + mItems.size());
    }


    public void getQuestionTopicData(int subjectId, IDataListener mListener){
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
//            mListener.onDataRetrieved();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the QuestionTopic items: " + mItems.size());
    }


    public List<AbstractFlexibleItem> getData(){
        return new ArrayList<>(mItems);
    }

    public void addItem(AbstractFlexibleItem item, Comparator<AbstractFlexibleItem> mComparator) {
        mItems.add(item);
        Collections.sort(mItems, mComparator);
    }

    public void addItem(AbstractFlexibleItem item) {
        mItems.add(item);
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
}
