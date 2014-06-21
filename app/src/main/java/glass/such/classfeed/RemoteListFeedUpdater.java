package glass.such.classfeed;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by david.carr on 6/21/14.
 */
public class RemoteListFeedUpdater extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("onGetViewFactory", "called");
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new FeedRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}