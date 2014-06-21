package glass.such.classfeed.Util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Quiz;

/**
 * Created by vincente on 6/20/14.
 */
public class AutoBahnConnection{
    private final String TAG = "AutoBahnConnection";

    private final WebSocketConnection mConnection   = new WebSocketConnection();
    private OnItemReceived onItemReceived           = null;

    public AutoBahnConnection(){
    }

    public void startConnection(String wsuri){
        try {
            mConnection.connect(wsuri, socketHandler);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    WebSocketHandler socketHandler = new WebSocketHandler() {
        @Override
        public void onOpen() {
            Log.d(TAG, "Status: Connected to " + Constants.Debug.WSURI);
        }

        @Override
        public void onTextMessage(String payload) {
            Log.d(TAG, "Got echo: " + payload);
            if(onItemReceived != null) {
                handlePayload(payload);
            }
        }

        @Override
        public void onClose(int code, String reason) {
            Log.d(TAG, "Connection lost.");
        }
    };

    protected void handlePayload(String payload){
        try {
            JSONObject object = new JSONObject(payload);
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

    public AutoBahnConnection(OnItemReceived onItemReceived){
        this.onItemReceived = onItemReceived;
    }

    public void setOnItemReceived(OnItemReceived onItemReceived){
        this.onItemReceived = onItemReceived;
    }

    public interface OnItemReceived{
        void onQuizReceived(Quiz receivedQuiz) throws Exception;
        void onNotesReceived(Note receivedNote) throws Exception;
    }
}
