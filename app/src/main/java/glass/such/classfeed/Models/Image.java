package glass.such.classfeed.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincente on 6/21/14.
 */
public class Image {
    public static final String TITLE= "title";
    public static final String URL  = "url";

    private String url  = null;
    private String title= null;

    public Image(JSONObject jsonObject) throws JSONException {
        url     = jsonObject.getString(URL);
        title   = jsonObject.getString(TITLE);
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
