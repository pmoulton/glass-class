package glass.such.classfeed.Data;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import glass.such.classfeed.Models.Quiz;
import glass.such.classfeed.QuizScrollAdapter;
import glass.such.classfeed.R;
import glass.such.classfeed.Util.Constants;

public class QuizActivity extends Activity {

    private CardScrollView cardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        cardScrollView = (CardScrollView) findViewById(R.id.quizscroller);
        try {
            QuizScrollAdapter quizScrollAdapter = new QuizScrollAdapter(new Quiz(new JSONObject(Constants.Test.QUIZPAYLOAD).getJSONObject("data")));
            cardScrollView.setAdapter(quizScrollAdapter);


            quizScrollAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
