package glass.such.classfeed.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincente on 6/21/14.
 */
public class Question {
    private static final String QUESTION        = "question";
    private static final String POSSIBLE        = "possible";
    private static final String ANSWER          = "answer";

    private String question     = null;
    private Answer[] possible   = null;
    private int answerIndex     = 0;

    public Question(String question, Answer[] possible, int answerIndex){
        this.question   = question;
        this.possible   = possible;
        this.answerIndex= answerIndex;
    }

    public Question(JSONObject jsonObject) throws JSONException {
        JSONArray possibleJSONArray = jsonObject.getJSONArray(POSSIBLE);
        this.possible   = new Answer[possibleJSONArray.length()];
        this.question   = jsonObject.getString(QUESTION);
        this.answerIndex= jsonObject.getInt(ANSWER);
        for(int i=0; i<possibleJSONArray.length(); i++){
            possible[i] = new Answer(possibleJSONArray.getJSONObject(i));
        }
    }

    public String getQuestion() {
        return question;
    }

    public Answer[] getPossible() {
        return possible;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public int getPossibleCount(){
        return possible.length;
    }

    public class Answer{
        public static final String TEXT = "text";
        private String answerText;

        public Answer(String answer){
            this.answerText = answer;
        }

        public Answer(JSONObject jsonObject) throws JSONException {
            this.answerText = jsonObject.getString(TEXT);
        }

        public String getAnswer() {
            return answerText;
        }
    }
}
