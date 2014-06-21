package glass.such.classfeed;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.radiusnetworks.ibeacon.Region;
import com.radiusnetworks.proximity.ibeacon.startup.BootstrapNotifier;
import com.radiusnetworks.proximity.ibeacon.startup.RegionBootstrap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import glass.such.classfeed.Data.FeedService;
import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Quiz;
import glass.such.classfeed.Util.AutoBahnConnection;
import glass.such.classfeed.Util.AutoPuller;
import glass.such.classfeed.Util.Constants;
import glass.such.classfeed.Util.SocketConnection;

/**
 * Created by vincente on 6/20/14.
 */
public class GCApplication extends Application implements BootstrapNotifier {

    private static final String TAG = "GCApplication";
    private static final String beaconUUID = "B0702880-A295-A8AB-F734-031A98A512DE";
    private RegionBootstrap regionBootstrap;
    private static Context mContext = null;
    private static OnItemReceived onItemReceived = null;
    private static AutoPuller autoPuller;
    private static boolean running = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//        Region region = new Region("com.radiusnetworks.androidproximityreference.backgroundRegion",beaconUUID, null, null);
//        regionBootstrap = new RegionBootstrap(this, region);
    }

    public static AutoPuller.OnAsyncFinish onAsyncFinish = new AutoPuller.OnAsyncFinish() {
        @Override
        public void onAsyncFinish() {
            try {
                JSONObject jsonObject = (JSONObject) autoPuller.get();
                handlePayload(jsonObject);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        autoPuller = new AutoPuller(Constants.WSURI, Constants.Debug.BEACON_ID, onAsyncFinish);
                        autoPuller.execute();
                    }
                };
                if(running) {
                    timer.schedule(timerTask, Constants.REFRESH_TIME);
                    Log.d(TAG, "Ran a new one!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    };

    public static void startAutoPuller(){
        running = true;
        autoPuller = new AutoPuller(Constants.WSURI, Constants.Debug.BEACON_ID, onAsyncFinish);
        autoPuller.execute();
    }

    public static void stopAutoPuller(){
        FeedService.getLiveCard().unpublish();
        running = false;
    }



    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "did enter region.");
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
    }

    public static Context getContext(){
        return mContext;
    }

    private static JSONObject previousJSON = null;

    protected static void handlePayload(JSONObject object){

        if(previousJSON == null){
            previousJSON = object;
        }
        else if(!TextUtils.equals(previousJSON.toString(), object.toString()))
            previousJSON = object;
        else
            return;
        try {
            if(object != null ){
                if(object.length() == 0)
                    return;
                else if(object.has("status") && TextUtils.equals(object.getString("status"), "fail")) {
                    GCApplication.stopAutoPuller();
                    Log.d(TAG, "Stopping Auto Puller as Slides are not in session");
                    return;
                }
            }
            if(onItemReceived != null) {
                try {
                    String type = object.getString(Constants.JSON.TYPE);
                    Log.d(TAG, "Type: " + type);
                    //Send a message to the callbacks that something has happened
                    if (TextUtils.equals(type, Note.NOTE)) {
                        Log.d(TAG, "We have a note!");
                        onItemReceived.onNotesReceived(
                                new Note(object.getJSONObject(Constants.JSON.DATA)));
                    }
                    else if (TextUtils.equals(type, Quiz.QUIZ))
                        onItemReceived.onQuizReceived(
                                new Quiz(object.getJSONObject(Constants.JSON.DATA)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                Log.d(TAG, "onItemReceived not set");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setOnItemReceived(OnItemReceived onItemReceived){
        GCApplication.onItemReceived = onItemReceived;
    }

    public interface OnItemReceived{
        void onQuizReceived(Quiz receivedQuiz) throws Exception;
        void onNotesReceived(Note receivedNote) throws Exception;
    }
}
