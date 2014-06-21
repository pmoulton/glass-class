package glass.such.classfeed.Data;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public static final String TAG = "FeedView";
    private ChangeListener mChangeListener;
    private static int count = 0;
    private static TextView h1;
    private static TextView h2;
    private static TextView h3;
    private static TextView h4;
    private static TextView d1;
    private static TextView d2;
    private static TextView d3;
    private static TextView d4;


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

//        mListView = (ListView) findViewById(R.id.feed);
        RelativeLayout item1 = (RelativeLayout) findViewById(R.id.cell1);
        RelativeLayout item2 = (RelativeLayout) findViewById(R.id.cell2);
        RelativeLayout item3 = (RelativeLayout) findViewById(R.id.cell3);
        RelativeLayout item4 = (RelativeLayout) findViewById(R.id.cell4);
        h1 = (TextView) item1.findViewById(R.id.item_heading_text);
        h2 = (TextView) item2.findViewById(R.id.item_heading_text);
        h3 = (TextView) item3.findViewById(R.id.item_heading_text);
        h4 = (TextView) item4.findViewById(R.id.item_heading_text);
        d1 = (TextView) item1.findViewById(R.id.item_desc_text);
        d2 = (TextView) item2.findViewById(R.id.item_desc_text);
        d3 = (TextView) item3.findViewById(R.id.item_desc_text);
        d4 = (TextView) item4.findViewById(R.id.item_desc_text);



//        mFeedListAdapter = new FeedListAdapter(getContext());

//        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(mFeedListAdapter);
//        swingRightInAnimationAdapter.setAbsListView(mListView);
//        mListView.setAdapter(swingRightInAnimationAdapter);
//        mListView.setAdapter(mFeedListAdapter);

        AddItem addItem = new AddItem();
        Timer itemTimer = new Timer();
        itemTimer.schedule(addItem, 1000, 1000);

    }

    class AddItem extends TimerTask {
        public void run() {
            try {
                JSONObject json = new JSONObject();
                json.put(Image.DESC, count+ " Placeholder description text");
                json.put(Image.TITLE, "Placeholder title text");
                json.put(Image.URL, "url");
                Image newImage = new Image(json);
//                mListView.invalidateViews();
//                mFeedListAdapter.add(newImage);
//                Log.d("", String.valueOf(mFeedListAdapter.getCount()));
//                mListView.smoothScrollToPosition(0);
                mChangeListener.onChange();
                count++;
                Log.d(TAG, count + "" + count %4);

                switch (count %4) {
                    case 0:
                        Log.d(TAG, "1");
                        d1.setText(count + " Placeholder text");
                        h1.setText(count + " Placeholder desc");
                        break;
                    case 1:
                        Log.d(TAG, "2");
                        d2.setText(count + " Placeholder text");
                        h2.setText(count + " Placeholder desc");
                        break;
                    case 2:
                        Log.d(TAG, "3");
                        d3.setText(count + " Placeholder text");
                        h3.setText(count + " Placeholder desc");
                        break;
                    case 3:
                        Log.d(TAG, "4");
                        d4.setText(count + " Placeholder text");
                        h4.setText(count + " Placeholder desc");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
