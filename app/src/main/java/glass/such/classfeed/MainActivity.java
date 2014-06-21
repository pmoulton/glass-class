package glass.such.classfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconData;
import com.radiusnetworks.ibeacon.IBeaconDataNotifier;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;
import com.radiusnetworks.ibeacon.client.DataProviderException;
import com.radiusnetworks.proximity.ibeacon.IBeaconManager;

import java.util.Collection;

public class MainActivity extends Activity implements IBeaconConsumer, RangeNotifier, IBeaconDataNotifier {

    private static final String TAG = "MainActivity";
    IBeaconManager iBeaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

//        iBeaconManager = IBeaconManager.getInstanceForApplication(this.getApplicationContext());
//        iBeaconManager.bind(this);

        startService(new Intent(this, LiveCardService.class));
    }

    @Override
    public void onIBeaconServiceConnect() {
        Region region = new Region("MainActivityRanging", null, null, null);
        try {
            iBeaconManager.startRangingBeaconsInRegion(region);
            iBeaconManager.setRangeNotifier(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
        for (IBeacon iBeacon: iBeacons) {
            iBeacon.requestData(this);
            Log.d(TAG, "iBeacon: "+iBeacon.getProximityUuid()+","+iBeacon.getMajor()+","+iBeacon.getMinor());
            String displayString = iBeacon.getProximityUuid()+" "+iBeacon.getMajor()+" "+iBeacon.getMinor()+"\n";
            Log.d(TAG, displayString);
        }
    }

    @Override
    public void iBeaconDataUpdate(IBeacon iBeacon, IBeaconData iBeaconData, DataProviderException e) {
        if (e != null) {
            Log.d(TAG, "data fetch error:"+e);
        }
        if (iBeaconData != null) {
            Log.d(TAG, "iBeacon with data: uuid="+iBeacon.getProximityUuid()+" major="+iBeacon.getMajor()+" minor="+iBeacon.getMinor()+" welcomeMessage="+iBeaconData.get("welcomeMessage"));
            String displayString = iBeacon.getProximityUuid()+" "+iBeacon.getMajor()+" "+iBeacon.getMinor()+"\n"+"Welcome message:"+iBeaconData.get("welcomeMessage");
            Log.d(TAG, displayString);
        }
    }
}
