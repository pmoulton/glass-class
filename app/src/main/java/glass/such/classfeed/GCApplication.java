package glass.such.classfeed;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.radiusnetworks.proximity.ibeacon.startup.BootstrapNotifier;
import com.radiusnetworks.proximity.ibeacon.startup.RegionBootstrap;
import com.radiusnetworks.ibeacon.Region;

/**
 * Created by vincente on 6/20/14.
 */
public class GCApplication extends Application implements BootstrapNotifier {

    private static final String TAG = "GCApplication";
    private static final String beaconUUID = "B0702880-A295-A8AB-F734-031A98A512DE";
    private RegionBootstrap regionBootstrap;
    private static Context mContext = null;
    private static AutoBahnConnection webSocketConnection = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Region region = new Region("com.radiusnetworks.androidproximityreference.backgroundRegion",beaconUUID, null, null);
        regionBootstrap = new RegionBootstrap(this, region);

        //Throw OnStart Testing Stuff Here
        if(Constants.Debug.ENABLED){
            getWebSocketConnection().startConnection(Constants.Debug.WSURI);
        }
    }

    public static AutoBahnConnection getWebSocketConnection(){
        if(webSocketConnection== null)
            webSocketConnection = new AutoBahnConnection();
        return webSocketConnection;
    }

    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "did enter region.");
        Intent intent = new Intent(this, MainActivity.class);
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
}
