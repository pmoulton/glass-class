package glass.such.classfeed.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vincente on 6/21/14.
 */
public class QuizCard {
    private List<Question.Answer> answers;
    private String title;
    private String uuid;
    private String imageUrl;

    public QuizCard(Quiz quiz){
        this.title = quiz.getTitle();
        this.uuid = quiz.getUuid();
        answers = new ArrayList<Question.Answer>();
    }

    public QuizCard(Question question){
        this.title = question.getQuestion();
        this.answers = Arrays.asList(question.getPossible());
    }

    public String getTitle() {
        return title;
    }

    public String getUuid() {
        return uuid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getAnswers() {
        List<String> returnList = new ArrayList<String>();
        for(int i=0; i< answers.size(); i++) {
            returnList.add(answers.get(i).getAnswer());
        }
        return returnList;
    }

    public List<Question.Answer> getAnswersAsAnswers() {
        return answers;
    }
}
