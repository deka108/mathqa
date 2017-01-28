package dekauliya.fyp.mathqa;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import dekauliya.fyp.mathqa.Models.IFinder;
import dekauliya.fyp.mathqa.Models.QuestionContent;

/**
 * Created by dekauliya on 23/1/17.
 */
@EBean
public class QuestionFinder implements IFinder {

    List<QuestionContent> questionContents;
    String[] questions = new String[]{
            "$$\\int 5e^{2x-1} {d}x$$",
            "This come from string. You can insert inline formula:" +
                    " \\(ax^2 + bx + c = 0\\) " +
                    "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$",
            "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\)" +
                    "and they are $$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.$$",
            "$$\\int 5e^{2x-1} {d}x$$",
            "This come from string. You can insert inline formula:" +
                    " \\(ax^2 + bx + c = 0\\) " +
                    "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$",
            "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\)" +
                    "and they are $$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.$$",
            "$$\\int 5e^{2x-1} {d}x$$",
            "This come from string. You can insert inline formula:" +
                    " \\(ax^2 + bx + c = 0\\) " +
                    "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$",
            "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\)" +
                    "and they are $$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.$$"
    };
    int[] difficulty = new int[]{2, 3, 1, 2, 3, 1, 2, 3, 1};

    public QuestionFinder(){
        questionContents = new ArrayList();
        initQuestions();
    }

    public void initQuestions(){
        for(int i=0; i<questions.length; i++){
            questionContents.add(new QuestionContent(questions[i], difficulty[i]));
        }
    }

    @Override
    public List<QuestionContent> findAllQuestions() {
        if (questionContents.isEmpty()){
            initQuestions();;
        }
        return questionContents;
    }
}
