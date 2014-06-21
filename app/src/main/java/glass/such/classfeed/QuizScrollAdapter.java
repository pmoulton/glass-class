package glass.such.classfeed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import glass.such.classfeed.Data.QuizActivity;
import glass.such.classfeed.Models.Question;
import glass.such.classfeed.Models.Quiz;
import glass.such.classfeed.Models.QuizCard;

/**
 * Created by vincente on 6/21/14.
 */
public class QuizScrollAdapter extends com.google.android.glass.widget.CardScrollAdapter {
    Context context;
    Quiz quiz;
    Question[] questions;
    ArrayList<QuizCard> quizCards;

    public QuizScrollAdapter(Quiz quiz){
        this.context    = GCApplication.getContext();
        this.questions  = quiz.getQuestions();
        this.quiz       = quiz;

        quizCards.add(new QuizCard(quiz));
        for(int i=0; i<questions.length; i++){
            quizCards.add(new QuizCard(questions[i]));
        }
    }

    @Override
    public int getCount() {
        return quizCards.size();
    }

    @Override
    public  QuizCard getItem(int i) {
        return quizCards.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.quiz_card, null);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView= (TextView)  convertView.findViewById(R.id.quiz_title);
            viewHolder.imageView    = (ImageView) convertView.findViewById(R.id.quiz_image);
            viewHolder.possibleListView = (ListView) convertView.findViewById(R.id.quiz_list);
            convertView.setTag(viewHolder);

            String[] data = new String[5];
            data[0] = quizCards.get(i).getTitle();
            data[1] = quizCards.get(i).getUuid();
            data[2] = quizCards.get(i).getAnswers().get(0).getAnswer();
            data[3] = quizCards.get(i).getAnswers().get(1).getAnswer();
            data[4] = quizCards.get(i).getAnswers().get(2).getAnswer();
            viewHolder.possibleListView.setAdapter(new ArrayAdapter<String>(GCApplication.getContext(), R.layout.list_item, data));

        }
        else {}
        viewHolder = (ViewHolder) convertView.getTag();
        QuizCard quizCard = getItem(i);
        viewHolder.titleTextView.setText(quizCard.getTitle());
        viewHolder.answers = quizCard.getAnswers();
        return convertView;
    }

    @Override
    public int getPosition(Object o) {
        return 0;
    }


    static class ViewHolder{
        public ImageView imageView;
        public TextView titleTextView;
        public ListView possibleListView;
        public List<Question.Answer> answers;
    }
}