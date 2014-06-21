package glass.such.classfeed;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 *
 */
public class FeedRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<String> listItemList = new ArrayList<String>();
    private Context context = null;
    private int appWidgetId;

    public FeedRemoteViewsFactory(Context context, Intent intent) {
        Log.d("Factory", "constructor");
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        for (int i = 0; i<10; i++) {
            String listItem = "Hello "+i;
            listItemList.add(listItem);
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("Factory", "getViewAt");
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_item);
        String listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.list_item_text, listItem);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }
}