package dekauliya.fyp.mathqa.dummy.UI.AAR.Data;

/**
 * Created by dekauliya on 2/2/17.
 */

import android.support.v4.util.Pair;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.Models.Question;
import dekauliya.fyp.mathqa.Models.Topic;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestApi;
import dekauliya.fyp.mathqa.RetrofitRestApi.MathQaRestService;
import retrofit2.Call;

@EBean
public class ExpandableDataProvider extends AbstractExpandableDataProvider {
    private List<Pair<GroupData, List<ChildData>>> mData;

    // for undo group item
    private Pair<GroupData, List<ChildData>> mLastRemovedGroup;
    private int mLastRemovedGroupPosition = -1;

    // for undo child item
    private ChildData mLastRemovedChild;
    private long mLastRemovedChildParentGroupId = -1;
    private int mLastRemovedChildPosition = -1;

//    public ExpandableDataProvider() {
//        final String groupItems = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        final String childItems = "abc";
//
//        mData = new LinkedList<>();
//
//        for (int i = 0; i < groupItems.length(); i++) {
//            //noinspection UnnecessaryLocalVariable
//            final long groupId = i;
//            final String groupText = Character.toString(groupItems.charAt(i));
//            final ConcreteGroupData group = new ConcreteGroupData(groupId, groupText);
//            final List<ChildData> children = new ArrayList<>();
//
//            for (int j = 0; j < childItems.length(); j++) {
//                final long childId = group.generateNewChildId();
//                final String childText = Character.toString(childItems.charAt(j));
//
//                children.add(new ConcreteChildData(childId, childText));
//            }
//
//            mData.add(new Pair<GroupData, List<ChildData>>(group, children));
//        }
//    }

//    Context context;
//    public ExpandableDataProvider(Context context) {
//        this.context = context;
//    }
    public ExpandableDataProvider(){
//        loadData();
        getQuestionsBySubject(1, new IListener(){

            @Override
            public void onComplete(List<Question> questions) {
                updateData(questions);
            }
        });
    }
//
//    @Background(serial="download")
//    void loadData(){
//
//    }


    @Background(serial="download")
    public void getQuestionsBySubject(int subjectId, IListener listener){
        MathQaRestApi client = MathQaRestService.createService(MathQaRestApi.class);
        List<Question> results = new ArrayList<>();
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

                    // for each question
                    for (Question question : questions) {
                        results.add(question);
                    }
                }
            }
            listener.onComplete(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("Data size of the QuestionTopic items: " + results.size());
    }

    @UiThread
    void updateData(List<Question> questions) {
        final String groupItems = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final List<Question> childItems = questions;

        mData = new LinkedList<>();

        for (int i = 0; i < groupItems.length(); i++) {
            //noinspection UnnecessaryLocalVariable
            final long groupId = i;
            final String groupText = Character.toString(groupItems.charAt(i));
            final ConcreteGroupData group = new ConcreteGroupData(groupId, groupText);
            final List<ChildData> children = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                final long childId = group.generateNewChildId();
//                final String childText = Character.toString(childItems.charAt(j));
                final Question childQuestion = childItems.get(i*3 + j);

                children.add(new ConcreteChildData(childId, childQuestion));
            }

            mData.add(new Pair<GroupData, List<ChildData>>(group, children));
        }
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    @Override
    public GroupData getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).first;
    }

    @Override
    public ChildData getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<ChildData> children = mData.get(groupPosition).second;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

    @Override
    public void moveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition == toGroupPosition) {
            return;
        }

        final Pair<GroupData, List<ChildData>> item = mData.remove(fromGroupPosition);
        mData.add(toGroupPosition, item);
    }

    @Override
    public void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if ((fromGroupPosition == toGroupPosition) && (fromChildPosition == toChildPosition)) {
            return;
        }

        final Pair<GroupData, List<ChildData>> fromGroup = mData.get(fromGroupPosition);
        final Pair<GroupData, List<ChildData>> toGroup = mData.get(toGroupPosition);

        final ConcreteChildData item = (ConcreteChildData) fromGroup.second.remove(fromChildPosition);

        if (toGroupPosition != fromGroupPosition) {
            // assign a new ID
            final long newId = ((ConcreteGroupData) toGroup.first).generateNewChildId();
            item.setChildId(newId);
        }

        toGroup.second.add(toChildPosition, item);
    }

    @Override
    public void removeGroupItem(int groupPosition) {
        mLastRemovedGroup = mData.remove(groupPosition);
        mLastRemovedGroupPosition = groupPosition;

        mLastRemovedChild = null;
        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
    }

    @Override
    public void removeChildItem(int groupPosition, int childPosition) {
        mLastRemovedChild = mData.get(groupPosition).second.remove(childPosition);
        mLastRemovedChildParentGroupId = mData.get(groupPosition).first.getGroupId();
        mLastRemovedChildPosition = childPosition;

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;
    }


    @Override
    public long undoLastRemoval() {
        if (mLastRemovedGroup != null) {
            return undoGroupRemoval();
        } else if (mLastRemovedChild != null) {
            return undoChildRemoval();
        } else {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }
    }

    private long undoGroupRemoval() {
        int insertedPosition;
        if (mLastRemovedGroupPosition >= 0 && mLastRemovedGroupPosition < mData.size()) {
            insertedPosition = mLastRemovedGroupPosition;
        } else {
            insertedPosition = mData.size();
        }

        mData.add(insertedPosition, mLastRemovedGroup);

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;

        return RecyclerViewExpandableItemManager.getPackedPositionForGroup(insertedPosition);
    }

    private long undoChildRemoval() {
        Pair<GroupData, List<ChildData>> group = null;
        int groupPosition = -1;

        // find the group
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).first.getGroupId() == mLastRemovedChildParentGroupId) {
                group = mData.get(i);
                groupPosition = i;
                break;
            }
        }

        if (group == null) {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }

        int insertedPosition;
        if (mLastRemovedChildPosition >= 0 && mLastRemovedChildPosition < group.second.size()) {
            insertedPosition = mLastRemovedChildPosition;
        } else {
            insertedPosition = group.second.size();
        }

        group.second.add(insertedPosition, mLastRemovedChild);

        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
        mLastRemovedChild = null;

        return RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, insertedPosition);
    }

    public static final class ConcreteGroupData extends GroupData {

        private final long mId;
        private final String mText;
        private boolean mPinned;
        private long mNextChildId;

        ConcreteGroupData(long id, String text) {
            mId = id;
            mText = text;
            mNextChildId = 0;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public void setPinned(boolean pinnedToSwipeLeft) {
            mPinned = pinnedToSwipeLeft;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public long generateNewChildId() {
            final long id = mNextChildId;
            mNextChildId += 1;
            return id;
        }
    }

    public static final class ConcreteChildData extends ChildData {

        private long mId;
//        private final String mText;
        private final Question mQuestion;
        private boolean mPinned;

//        ConcreteChildData(long id, String text) {
//            mId = id;
//            mText = text;
//        }

        ConcreteChildData(long id, Question question) {
            super(question.getId(), question.getQuestion_type(), question.getUsedFor(),
                    question.getMarks(), question.getDifficulty_level(), question.getResponse_type(),
                    question.getSource(), question.getContent(), question.isSample(), question
                            .getConcept(), question.getSubconcept(), question.getPaper(),
                    question.getKeypoints(), question.getKeywords());
            mId = id;
            mQuestion = question;
        }

        @Override
        public long getChildId() {
            return mId;
        }

//        @Override
//        public String getText() {
//            return mText;
//        }

        public void setChildId(long id) {
            this.mId = id;
        }
    }

}
