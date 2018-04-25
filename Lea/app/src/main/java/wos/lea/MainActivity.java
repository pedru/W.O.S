package wos.lea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Exam;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.TokenResponse;
import wos.lea.networking.UserDetail;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    private ArrayList<Exam> exams;

    private ListView examList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        examList = findViewById(R.id.examList);

        authenticate();

        Call<UserDetail> call = NetworkManager.getInstance().leaRestService.getMyUser();

        


        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                UserDetail userDetail = response.body();
                Log.d("EXAMS", "RESPONSE: " + response.body().getExams());
                exams = new ArrayList<>(userDetail.getExams());
             //   exams = new ArrayList<>();
                if(exams.isEmpty())
                {
                    findViewById(R.id.exams_layout).setVisibility(View.GONE);
                    findViewById(R.id.empty_exams_layout).setVisibility(View.VISIBLE);

                }
                else {

                    findViewById(R.id.exams_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.empty_exams_layout).setVisibility(View.GONE);
                    ExamListAdapter adapter = new ExamListAdapter(MainActivity.this, exams);
                    examList.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });


        examList = findViewById(R.id.examList);




        ImageButton btn = findViewById(R.id.searchButton);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search_exam) {
            startActivity(new Intent(MainActivity.this, SearchExamActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void authenticate() {

        //TODO Dummy USER for testing
            saveAuthFile("026214c37ffba700e0b0389e9c0db7522200bfff");

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String authtoken = sharedPref.getString("Token","");


        if(authtoken.length() < 4) { // No token set

            Call<TokenResponse> call = NetworkManager.getInstance().getLeaRestService().getAuthToken();
            call.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                    TokenResponse tr;
                    tr  = response.body();
                    Log.d("AUTH", "Successful user: " + tr.getUser()+ " TOKEN:" + tr.getToken());

                    saveAuthFile(tr.getToken());
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.d("AUTH", "FAIL");
                }
            });
        }

        Log.d("AUTH", "already logged in token: " + authtoken) ;


        NetworkManager.getInstance().setAuthtoken(authtoken);
    }

    public void saveAuthFile(String token) {

        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Token", token);
        editor.apply();

    }


}
