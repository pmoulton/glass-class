package glass.such.classfeed.Data;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glass.such.classfeed.Models.Image;
import glass.such.classfeed.R;

/**
 * Created by david.carr on 6/21/14.
 */
public class FeedListAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private List<Image> contentList;
    public FeedListAdapter(Context c) {
        mContext = c;
        contentList = new ArrayList<Image>();
    }

    public void add(Image img) {
        Log.d("Adapter", "add");
        contentList.add(0, img);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int i) {
        return contentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.feed_list_item, null);
        TextView headingText = (TextView) view.findViewById(R.id.item_heading_text);
        TextView descText = (TextView) view.findViewById(R.id.item_desc_text);
        ImageView itemImage = (ImageView) view.findViewById(R.id.item_image);
        headingText.setText(contentList.get(i).getTitle());
        descText.setText(contentList.get(i).getDescription());
//        Picasso.with(mContext).load(currentSong.albumArtUrl).fit().into(albumImage);
        return view;
    }

}
