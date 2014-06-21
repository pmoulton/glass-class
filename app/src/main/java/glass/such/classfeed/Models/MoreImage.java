package glass.such.classfeed.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Paul Moulton on 6/21/14.
 */
public class MoreImage{
    public static final String TITLE= "title";
    public static final String URL  = "url";
    public static final String TEXT = "text";

    private String url  = null;
    private String title= null;
    private String text = null;

    public MoreImage(JSONObject jsonObject) throws JSONException {
        url     = jsonObject.getString(URL);
        title   = jsonObject.getString(TITLE);
        text = jsonObject.getString(TEXT);

    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
