package glass.such.classfeed;

import android.util.Log;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by vincente on 6/20/14.
 */
public class AutoBahnConnection {
    private final String TAG = "AutoBahnConnection";
    private final WebSocketConnection mConnection = new WebSocketConnection();

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
            Log.d(TAG, "Status: Connected!");
            mConnection.sendTextMessage("Hello, world!");
        }

        @Override
        public void onTextMessage(String payload) {
            Log.d(TAG, "Got echo: " + payload);
        }

        @Override
        public void onClose(int code, String reason) {
            Log.d(TAG, "Connection lost.");
        }
    };
}
