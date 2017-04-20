package flashvideo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dalafiari Samuel on 12/2/2016.
 */

public class MainActivity extends AppCompatActivity {
    protected static long backPressed;
    List<VideoObject> videoObjectList;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.menu_home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_settings:
                        Intent settingsIntent = new Intent(MainActivity.this, Preference_Activity.class);
                        startActivity(settingsIntent);
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        HomepageFragmentAdapter adapter = new HomepageFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        videoObjectList = new ArrayList<VideoObject>();

        ListView videoList = (ListView) findViewById(R.id.video_list_view);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShareSelection = new Intent(MainActivity.this, SendActivity.class);
                startActivity(openShareSelection);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_videos).getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (backPressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else {
            Toast.makeText(getBaseContext(), "press once again to exit!", Toast.LENGTH_SHORT).show();

        }

        backPressed = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
