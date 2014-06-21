package glass.such.classfeed.Data;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import glass.such.classfeed.GCApplication;
import glass.such.classfeed.Models.Image;
import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Quiz;
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
    private static ImageView img1;
    private static ImageView img2;
    private static ImageView img3;
    private static ImageView img4;


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
   // private FeedListAdapter mFeedListAdapter;


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
        img1 = (ImageView) item1.findViewById(R.id.item_image);
        img2 = (ImageView) item2.findViewById(R.id.item_image);
        img3 = (ImageView) item3.findViewById(R.id.item_image);
        img4 = (ImageView) item4.findViewById(R.id.item_image);

//        mFeedListAdapter = new FeedListAdapter(getContext());

//        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(mFeedListAdapter);
//        swingRightInAnimationAdapter.setAbsListView(mListView);
//        mListView.setAdapter(swingRightInAnimationAdapter);
//        mListView.setAdapter(mFeedListAdapter);

//        AddItem addItem = new AddItem();
//        Timer itemTimer = new Timer();
//        itemTimer.schedule(addItem, 4000, 4000);
        GCApplication.OnItemReceived onItemReceived = new GCApplication.OnItemReceived() {
            @Override
            public void onQuizReceived(Quiz receivedQuiz) throws Exception {
            }

            @Override
            public void onNotesReceived(Note receivedNote) throws Exception {

                addNote(receivedNote);
            }
        };
        GCApplication.setOnItemReceived(onItemReceived);
        GCApplication.startAutoPuller();
    }

//    class AddItem extends TimerTask {
//        public void run() {
//            try {
//                JSONObject json = new JSONObject();
//                json.put(Image.DESC, count+ " Placeholder description text");
//                json.put(Image.TITLE, count+ " Placeholder title text");
//                json.put(Image.URL, "url");
//                Image newImage = new Image(json);
//                count++;
//                addImage(newImage);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    private void addNote(Note note) {
        Log.d(TAG, "addnote");
        h4.setText(h3.getText());
        img4.setImageDrawable(img3.getDrawable());
        h3.setText(h2.getText());
        img3.setImageDrawable(img2.getDrawable());
        h2.setText(h1.getText());
        img2.setImageDrawable(img1.getDrawable());
        h1.setText(note.getText());
        for (int i=0; i<note.getImageCount(); i++) {
            Picasso.with(getContext()).load(note.getImages()[i].getUrl()).into(img1);
        }

        final SoundPool mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        final int mAlertReceived = mSoundPool.load(getContext(), R.raw.countdown_bip, 1);

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                mSoundPool.play(mAlertReceived, 1, 1, 1, 0, 1);
//                FeedService.getLiveCard().navigate();
            }
        });

    }

}