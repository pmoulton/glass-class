package glass.such.classfeed.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincente on 6/21/14.
 */
public class Note {
    public static final String IMAGES       = "images";
    public static final String NOTE         = "Notes";
    public static final String TEXT         = "text";
    public static final String URLS         = "urls";

    private Image[] images      = null;
    private String[] urls       = null;
    private String text         = null;

    public Note(JSONObject jsonObject) throws JSONException {
        JSONArray imageJSONArray= jsonObject.getJSONArray(IMAGES);
        JSONArray urlJSONArray  = jsonObject.getJSONArray(URLS);
        images      = new Image[imageJSONArray.length()];
        urls        = new String[urlJSONArray.length()];
        text        = jsonObject.getString(TEXT);
        for(int i=0; i<images.length; i++){
            images[i] = new Image(imageJSONArray.getJSONObject(i));
        }
        for(int i=0; i<urls.length; i++){
            urls[i] = urlJSONArray.getString(i);
        }
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(String[] imageUrl) {
        this.images = images;
    }

    public boolean hasImages(){
        return this.images != null;
    }

    public int getImageCount(){
        return this.images.length;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] url) {
        this.urls = url;
    }

    public int getUrlCount(){
        return this.urls.length;
    }

    public boolean hasUrl(){
        return this.urls != null;
    }

    public String getText() {
        return text;
    }
}
