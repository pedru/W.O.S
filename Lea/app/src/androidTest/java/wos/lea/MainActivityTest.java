package wos.lea;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by u11s65 on 27.03.2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mainActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {

        super.setUp();
        injectInsrumentation(InstrumentationRegistry.getInstrumentation());
        mainActivity = getActivity();
    }

    @Test
    public void getExamsTest () throws InterruptedException {
       assertNotNull(mainActivity.getExams());
    }

    @Test
    public void examListTest() {
       View listView = mainActivity.findViewById(R.id.examList);
       assertNotNull(listView);
    }

    @Test
    public void examListAdapterExistsTest() {
        ListView listView = mainActivity.findViewById(R.id.examList);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

}