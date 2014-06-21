package glass.such.classfeed.Util;

import android.text.TextUtils;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.DisconnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.JSONCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Quiz;

/**
 * Created by vincente on 6/21/14.
 */
public class SocketConnection {
    private static final String TAG = "SocketConnection";
    private int retryTimer = 0;
    private static SocketConnection instance;
    private OnItemReceived onItemReceived;
    private SocketIOClient client;
    private String wsuri;
    private boolean disconnect = false;

    public static SocketConnection getInstance(){
        if(instance == null)
            instance = new SocketConnection();
        return instance;
    }

    public SocketConnection(){
    }
    public SocketConnection(OnItemReceived onItemReceived){
        this.onItemReceived = onItemReceived;
    }

    private ConnectCallback connectCallback = new ConnectCallback() {
        @Override
        public void onConnectCompleted(Exception ex, final SocketIOClient client) {
            SocketConnection.getInstance().client = client;
            Log.d(TAG, "Connected!");
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            client.emitEvent("WOW");
            client.on("WOW", new EventCallback() {
                @Override
                public void onEvent(JSONArray json, Acknowledge acknowledge) {
                    Log.d(TAG, "json: " + json.toString());
                   // handlePayload(json);
                }
            });
            client.setStringCallback(new StringCallback() {
                @Override
                public void onString(String s, Acknowledge acknowledge) {
                    Log.d(TAG, "string: " + s);
                }
            });
            client.setJSONCallback(new JSONCallback() {
                @Override
                public void onJSON(JSONObject jsonObject, Acknowledge acknowledge) {
                    Log.d(TAG, "jsonObject: " + jsonObject.toString());
                }
            });

            client.setDisconnectCallback(new DisconnectCallback() {
                @Override
                public void onDisconnect(Exception e) {
                    Log.d(TAG, "Connection lost.");
                    if(!disconnect) {
                        retryTimer = (retryTimer == 0) ? retryTimer + 1000 : retryTimer * 5;

                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {

                            @Override
                            public void run() {
                                client.reconnect();
                            }
                        };
                        timer.schedule(timerTask, retryTimer);
                    }
                    else{
                        retryTimer = 0;
                    }
                }
            });
        }
    };


    public void connect(String wsuri) {
        this.wsuri = wsuri;
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), wsuri, connectCallback);
    }

    public void disconnect(){
        this.disconnect = true;
        client.disconnect();
    }

    protected void handlePayload(JSONObject object){
        if(onItemReceived != null) {
            try {
                String type = object.getString(Constants.JSON.TYPE);
                //Send a message to the callbacks that something has happened
                if (TextUtils.equals(type, Note.NOTE))
                    onItemReceived.onNotesReceived(
                            new Note(object.getJSONObject(Constants.JSON.DATA)));
                else if (TextUtils.equals(type, Quiz.QUIZ))
                    onItemReceived.onQuizReceived(
                            new Quiz(object.getJSONObject(Constants.JSON.DATA)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnItemReceived(OnItemReceived onItemReceived){
        this.onItemReceived = onItemReceived;
    }

    public interface OnItemReceived{
        void onQuizReceived(Quiz receivedQuiz) throws Exception;
        void onNotesReceived(Note receivedNote) throws Exception;
    }
}
