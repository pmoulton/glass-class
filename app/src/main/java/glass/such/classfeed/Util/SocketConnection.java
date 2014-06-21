package glass.such.classfeed.Util;

import android.text.TextUtils;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.JSONCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONObject;

import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Quiz;

/**
 * Created by vincente on 6/21/14.
 */
public class SocketConnection {

    private static SocketConnection instance;
    private OnItemReceived onItemReceived;

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
        public void onConnectCompleted(Exception ex, SocketIOClient client) {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            client.setJSONCallback(new JSONCallback() {
                @Override
                public void onJSON(JSONObject json, Acknowledge acknowledge) {
                    System.out.println("json: " + json.toString());
                    handlePayload(json);
                }
            });
        }
    };


    public void connect(String wsuri) {
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), wsuri, connectCallback);
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
