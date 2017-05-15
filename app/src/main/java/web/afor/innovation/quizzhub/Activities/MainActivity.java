package web.afor.innovation.quizzhub.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.Activities.preferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
      public static MediaPlayer mpSplash;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpSplash= MediaPlayer.create(this,R.raw.noise);
        mpSplash.start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView nameView = (TextView) header.findViewById(R.id.name_txt);
//  nameView.setText(preferences.loadString(getApplicationContext(),"username",""));


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        replaceFragment(1);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       // getMenuInflater().inflate(R.menu.sub_menu, .getSubMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

            finish();


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
            replaceFragment(0);

        } else if (id == R.id.nav_share) {
            replaceFragment(2);
        }
        else if (id==R.id.nav_config){
            replaceFragment(3);

        }
        else if (id==R.id.nav_sound){
            replaceFragment(3);

        }else if(id==R.id.nav_difficulty){
            replaceFragment(4);
        }else if (id==R.id.nav_Multiplayer){
            replaceFragment(6);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(int position){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = Fragment.instantiate(getApplicationContext(),Constant.ArrayFragment[position]);
        fragmentTransaction.replace(R.id.content_layout,fragment);
        fragmentTransaction.commit();

    }

    public void replaceFragmentWithBundle (String key,String value){
               FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
               Fragment fragment =Fragment.instantiate(getApplicationContext(),Constant.ArrayFragment[0]);

               Bundle bundle=new Bundle();
               bundle.putString(key,value);
               fragment.setArguments(bundle);
              fragmentTransaction.replace(R.id.content_layout,fragment);
              fragmentTransaction.commit();

    }

    public void replaceFragmentWithBundleandTimer (String key,String value,int timer,int position){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment =Fragment.instantiate(getApplicationContext(),Constant.ArrayFragment[position]);

        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_layout,fragment);
        fragmentTransaction.commit();

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    protected  void OnDestroy (){
     super.onDestroy();
        mpSplash.release();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mpSplash.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mpSplash.start();
    }
}
