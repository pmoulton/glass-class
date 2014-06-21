package glass.such.classfeed;

import android.app.Application;
import android.content.Context;

/**
 * Created by vincente on 6/20/14.
 */
public class GCApplication extends Application {

    private static Context mContext = null;
    private static AutoBahnConnection webSocketConnection = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    public static AutoBahnConnection getWebSocketConnection(){
        if(webSocketConnection== null)
            webSocketConnection = new AutoBahnConnection();
        return webSocketConnection;
    }

    public static Context getContext(){
        return mContext;
    }
}
