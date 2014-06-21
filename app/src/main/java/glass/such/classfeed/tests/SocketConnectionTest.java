package glass.such.classfeed.tests;

import android.text.TextUtils;

import junit.framework.TestCase;

import glass.such.classfeed.Models.Image;
import glass.such.classfeed.Models.Note;
import glass.such.classfeed.Models.Question;
import glass.such.classfeed.Models.Quiz;
import glass.such.classfeed.Util.SocketConnection;

public class SocketConnectionTest extends TestCase {

    SocketConnection connection = null;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        connection = SocketConnection.getInstance();
    }

    public void testSetOnItemReceived() throws Exception {
        assert(connection != null);
        SocketConnection.OnItemReceived onItemReceived = new SocketConnection.OnItemReceived() {
            @Override
            public void onQuizReceived(Quiz receivedQuiz) throws Exception {
                testQuiz(receivedQuiz);
            }

            @Override
            public void onNotesReceived(Note receivedNote) throws Exception {
                testNote(receivedNote);
            }
        };
    }

    private void testQuiz(Quiz receivedQuiz) throws Exception{
        assertNotNull(receivedQuiz);
        assertNotNull(receivedQuiz.getTitle());
        assertNotNull(receivedQuiz.getUuid());
        assertNotNull(receivedQuiz.getQuestions());
        assert(receivedQuiz.getQuestionCount() > 0);
        assert(!TextUtils.isEmpty(receivedQuiz.getTitle()));
        assert(!TextUtils.isEmpty(receivedQuiz.getUuid()));

        for(Question question : receivedQuiz.getQuestions())
            testQuestion(question);
    }

    private void testQuestion(Question question) throws Exception{
        assertNotNull(question);
        assertNotNull(question.getPossible());
        assertNotNull(question.getQuestion());
        assert(question.getAnswerIndex() >= 0);
        assert(question.getPossibleCount() > 0);
        assert(!TextUtils.isEmpty(question.getQuestion()));
        for(Question.Answer answer : question.getPossible()){
            testAnswer(answer);
        }
    }

    private void testAnswer(Question.Answer answer) {
        assertNotNull(answer);
        assert(!TextUtils.isEmpty(answer.getAnswer()));
    }

    private void testNote(Note note) throws Exception{
        assertNotNull(note);
        assertNotNull(note.getText());
        assertNotNull(note.getUrls());
        assertNotNull(note.getImages());
        assert(note.getImageCount() > 0);
        assert(note.getUrlCount() > 0);
        for(String url : note.getUrls()){
            assertNotNull(url);
            assert(!TextUtils.isEmpty(url));
        }
        for(Image image : note.getImages()){
            testImage(image);
        }
    }

    private void testImage(Image image) {
        assertNotNull(image);
        assertNotNull(image.getUrl());
        assertNotNull(image.getTitle());
        assert(!TextUtils.isEmpty(image.getUrl()));
        assert(!TextUtils.isEmpty(image.getTitle()));
    }
}
