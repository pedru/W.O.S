package wos.lea;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import wos.lea.networking.Exam;

public class ExamListAdapterTest {



    @Test
    public void adapterNullPointerTest() {
       ExamListAdapter adapter = new ExamListAdapter(Mockito.mock(Context.class), null);
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterEmptyListTest() {
        ExamListAdapter adapter = new ExamListAdapter(Mockito.mock(Context.class), new ArrayList<Exam>());
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListNullElementTest() {
        List<Exam> examList = new ArrayList<>();
        examList.add(null);

        ExamListAdapter adapter = new ExamListAdapter(Mockito.mock(Context.class), examList);
        Assert.assertNotNull(adapter);
    }

    @Test
    public void adapterListElementNullFieldsTest() {
        List<Exam> examList = new ArrayList<>();
        Exam exam = new Exam();
        examList.add(exam);

        ExamListAdapter adapter = new ExamListAdapter(Mockito.mock(Context.class), examList);
        Assert.assertNotNull(adapter);
    }
}
