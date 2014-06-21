package glass.such.classfeed.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincente on 6/21/14.
 */
public class Quiz {
    public static final String QUESTIONS   = "questions";
    public static final String UUID        = "uuid";
    public static final String TITLE       = "text";
    public static final String QUIZ        ="quiz";

    private Question[] questions= null;
    private String title        = null;
    private String uuid         = null;

    public Quiz (String uuid, String title, int questionCount, Question[] questions){
        this.uuid = uuid;
        this.questions      = questions;
        this.title          = title;
    }

    public Quiz (JSONObject jsonObject) throws JSONException{
        Log.d("quiz", jsonObject.toString());
        JSONArray questionJSONArray = jsonObject.getJSONArray(QUESTIONS);
        this.questions  = new Question[questionJSONArray.length()];
        this.title      = jsonObject.getString(TITLE);
        this.uuid       = jsonObject.getString(UUID);

        for(int i=0; i<questionJSONArray.length(); i++){
            questions[i] = new Question(questionJSONArray.getJSONObject(i));
        }
    }

    public int getQuestionCount() {
        return questions.length;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public Question[] getQuestions() {
        return questions;
    }
}

