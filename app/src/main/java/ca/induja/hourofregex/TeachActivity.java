package ca.induja.hourofregex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca.induja.hourofregex.Constants;


/**
 * Created by induj on 2016-10-11.
 */

public class TeachActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnIndexChangedListener, ExerciseFragment.OnExerciseCompleteListener{

    private final String TAG = "TeachActivity";
    public static final String KEY_CURRENT_INDEX = "activity_index";
    private int mCurrentIndex;
    private Button mNextButton;

    private LessonFragment mLessonFragment;
    private ExerciseFragment mExerciseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        } else {
            mCurrentIndex = 0;
        }

        setContentView(R.layout.activity_teach);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Populate Menu
        navigationView.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        int numMenuItems = Constants.NUM_LESSONS;
        String[] menu_text = getResources().getStringArray(R.array.lesson_titles);
        for(int i = 0; i < numMenuItems; ++i) {
            menu.add(Menu.NONE, i, Menu.NONE, menu_text[i]);
        }

        mNextButton = (Button)findViewById(R.id.button_next);
        mLessonFragment = (LessonFragment)getSupportFragmentManager()
                .findFragmentById(R.id.LessonFragment);
        mExerciseFragment = (ExerciseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.ExerciseFragment);

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
        getMenuInflater().inflate(R.menu.teach, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.return_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mCurrentIndex = id;
        setLessonAndExercise(mCurrentIndex);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
    }

    @Override
    public int getCurrentIndex(){
        return mCurrentIndex;
    }

    @Override
    public void showNextButton() {
        mNextButton.setVisibility(View.VISIBLE);
    }

    public void onNextPressed(View view) {
        mCurrentIndex++;
        setLessonAndExercise(mCurrentIndex);
    }

    public void setLessonAndExercise(int index){

        if(index == Constants.NUM_LESSONS) {
            Intent intent = new Intent(this, CompletionActivity.class);
            startActivity(intent);
        } else {
            mLessonFragment.setLesson(mCurrentIndex);
            mExerciseFragment.setExercise(mCurrentIndex);
            mNextButton.setVisibility(View.GONE);
        }
    }

}
