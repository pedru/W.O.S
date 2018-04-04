package wos.lea;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wos.lea.networking.Exam;
import wos.lea.networking.LeaRestService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExamUnitTest {

    private MockWebServer server;

    private String mockResponseString = "[{ \"lectureName\":\"something\", \"study\":\"study\" ,\"created\":\"Mar 27, 2018 3:47:41 PM\"} ]";

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

    }

    @Test
    public void testRestService() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "applicationc/json; charset=utf-8")
                .setBody(mockResponseString));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LeaRestService restService = retrofit.create(LeaRestService.class);

        List<Exam> examList = restService.listAllExams().execute().body();
        assertEquals(examList.size(), 1);
        assertEquals("something", examList.get(0).getLectureName());
        assertEquals("study", examList.get(0).getStudy());
        assertNotNull(examList.get(0).getCreated());
    }
}