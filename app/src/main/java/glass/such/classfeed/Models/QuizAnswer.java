package glass.such.classfeed.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincente on 6/21/14.
 */
public class QuizAnswer {
    public static final String ANSWERED = "answered";
    public static final String UUID     = "uuid";
    public static final String TITLE    = "text";

    private String uuid     = null;
    private String title    = null;
    private int[] answered  = null;

    public QuizAnswer(String uuid, String title, int[] answered){
        this.uuid       = uuid;
        this.title      = title;
        this.answered   = answered;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public int[] getAnswered() {
        return answered;
    }

    public String toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UUID, uuid);
        jsonObject.put(TITLE, title);
        jsonObject.put(ANSWERED, answered);
        return jsonObject.toString();
    }
}
