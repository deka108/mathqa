package dekauliya.fyp.mathqa.dummy.UI.AAR.Data;

import dekauliya.fyp.mathqa.Models.Question;

/**
 * Created by dekauliya on 2/2/17.
 */

/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

public abstract class AbstractExpandableDataProvider {
    public static abstract class BaseData {

        public abstract String getText();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public static abstract class GroupData extends BaseData {
        public abstract boolean isSectionHeader();
        public abstract long getGroupId();
    }

//    public static abstract class ChildData extends BaseData {
//        public abstract long getChildId();
//    }

    public static abstract class ChildData extends Question {
        public ChildData(String id, String questionType, String usedFor,
                         int marks, String difficultyLevel, String responseType, String source,
                         String content, Boolean isSample, int concept, int subconcept, String
                                 paper,
                         int[] keypoints, int[] keywords) {
            super(id, questionType, usedFor, marks, difficultyLevel, responseType, source, content,
                    isSample, concept, subconcept, paper, keypoints, keywords);
        }

        public abstract long getChildId();
    }

    public abstract int getGroupCount();
    public abstract int getChildCount(int groupPosition);

    public abstract GroupData getGroupItem(int groupPosition);
    public abstract ChildData getChildItem(int groupPosition, int childPosition);


    public abstract void moveGroupItem(int fromGroupPosition, int toGroupPosition);
    public abstract void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition);

    public abstract void removeGroupItem(int groupPosition);
    public abstract void removeChildItem(int groupPosition, int childPosition);

    public abstract long undoLastRemoval();
}
