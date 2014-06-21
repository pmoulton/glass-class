package glass.such.classfeed.Util;

import android.os.AsyncTask;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by vincente on 6/21/14.
 */
public class AutoPuller extends AsyncTask{
    private static final String TAG = "AutoPuller";
    private String url = null;
    private OnAsyncFinish onAsyncFinish;
    public AutoPuller(String url, String beaconId, OnAsyncFinish onAsyncFinish){
        this.url = url+"?beaconId="+beaconId;
        this.onAsyncFinish = onAsyncFinish;
    }

    @Override
    protected JSONObject doInBackground(Object[] objects) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try{
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            if(responseString.length() > 50) {
                responseString = responseString.replace("\\", "");
                responseString = responseString.substring(1, responseString.length() - 1);
            }
            Log.d(TAG, "ResponseString: " + responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            Log.i("AutoPuller", "jsonResponse: " +jsonObject.toString());
            return jsonObject;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        onAsyncFinish.onAsyncFinish();

    }

    public interface OnAsyncFinish{
        void onAsyncFinish();
    }
}
