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
        QuestionListAdapter adapter = new QuestionListAdapter(Mockito.mock(Context.class), null);
        Assert.assertNotNull(adapter);
    }

    @Test
    public  void adapterEmptyListTest() {
        QuestionListAdapter adapter = new QuestionListAdapter(Mockito.mock(Context.class), new ArrayList<Question>());
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListNullElementTest(){
        List<Question> questionList = new ArrayList<>();
        questionList.add(null);

        QuestionListAdapter adapter = new QuestionListAdapter(Mockito.mock(Context.class), questionList);
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListElementNullFieldsTest(){
        List<Question> questionList = new ArrayList<>();
        Question question = new Question();
        questionList.add(question);

        QuestionListAdapter adapter = new QuestionListAdapter(Mockito.mock(Context.class), questionList);
        Assert.assertNotNull(adapter);
    }
}
