package glass.such.classfeed.Data;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import glass.such.classfeed.Models.Image;
import glass.such.classfeed.R;

/**
 * View used to draw a running timer.
 */
public class FeedView extends FrameLayout {

    private ChangeListener mChangeListener;
    private static int count = 0;

    /**
     * Interface to listen for changes on the view layout.
     */
    public interface ChangeListener {
        /** Notified of a change in the view. */
        public void onChange();
    }

    public void setListener(ChangeListener listener) {
        mChangeListener = listener;
    }

    private ListView mListView;
    private FeedListAdapter mFeedListAdapter;


    public FeedView(Context context) {
        this(context, null, 0);
    }

    public FeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedView(Context context, AttributeSet attrs, int style) {
        this(context, attrs, style, new Timer());
    }

    public FeedView(Context context, AttributeSet attrs, int style, Timer timer) {
        super(context, attrs, style);

        LayoutInflater.from(context).inflate(R.layout.feed_view, this);

        mListView = (ListView) findViewById(R.id.feed);
        mFeedListAdapter = new FeedListAdapter(getContext());

//        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(mFeedListAdapter);
//        swingRightInAnimationAdapter.setAbsListView(mListView);
//        mListView.setAdapter(swingRightInAnimationAdapter);
        mListView.setAdapter(mFeedListAdapter);

        AddItem addItem = new AddItem();
        Timer itemTimer = new Timer();
        itemTimer.schedule(addItem, 3000, 3000);

    }

    class AddItem extends TimerTask {
        public void run() {
            try {
                JSONObject json = new JSONObject();
                json.put(Image.DESC, count+ " Placeholder description text");
                json.put(Image.TITLE, "Placeholder title text");
                json.put(Image.URL, "url");
                Image newImage = new Image(json);
                mListView.invalidateViews();
                mFeedListAdapter.add(newImage);
//                Log.d("", String.valueOf(mFeedListAdapter.getCount()));
                mListView.smoothScrollToPosition(0);
                count++;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
