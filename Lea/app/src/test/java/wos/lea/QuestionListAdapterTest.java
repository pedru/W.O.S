package wos.lea;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import wos.lea.networking.Question;

public class QuestionListAdapterTest {

    @Test
    public void adapterNullPointerTest() {
        QuestionListAdapter adapter = new QuestionListAdapter(null, 1);
        Assert.assertNotNull(adapter);
    }

    @Test
    public  void adapterEmptyListTest() {
        QuestionListAdapter adapter = new QuestionListAdapter(new ArrayList<Question>(), 1);
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListNullElementTest(){
        List<Question> questionList = new ArrayList<>();
        questionList.add(null);

        QuestionListAdapter adapter = new QuestionListAdapter(questionList, 1);
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListElementNullFieldsTest(){
        List<Question> questionList = new ArrayList<>();
        Question question = new Question();
        questionList.add(question);

        QuestionListAdapter adapter = new QuestionListAdapter(questionList, 1);
        Assert.assertNotNull(adapter);
    }
}
