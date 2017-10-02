package com.mr.rohmani.diadiasempurna;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mr.rohmani.diadiasempurna.fragment.BookmarkFragment;
import com.mr.rohmani.diadiasempurna.fragment.HomeFragment;

import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BookmarkFragment.OnDataPass {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment fragment = null;
    private List<Integer> myList;
    private int last;
    private boolean itemIsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (last==0){
                    Snackbar.make(view, "Tidak ada bacaan terakhir", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    Intent i = new Intent(Home.this, Pages.class);
                    i.putExtra("part", last);
                    startActivity(i);
                }
            }
        });

        manager = getFragmentManager();

        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            callFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences("com.mr.rohmani.ddds",MODE_PRIVATE);
        last = settings.getInt("last", 0);
        boolean mScreen = settings.getBoolean("screen", false);
        if(mScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
//            new AlertDialog.Builder(this)
//                    .setMessage("Keluar Dari Aplikasi?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_delete);
        if (itemIsVisible){
            item.setVisible(true);
        }else{
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            if (myList==null){
                Toast.makeText(Home.this, "Belum ada item yang di pilih, pilih item dengan klik dan tahan item", Toast.LENGTH_SHORT).show();
            }else {
                if (myList.size() > 0) {
                    new AlertDialog.Builder(this)
                            .setMessage("Hapus item yang ditandai?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteBookmark();
                                    myList=null;
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            setTitle(R.string.action_home);
            callFragment(fragment);
            itemIsVisible=false;
            ActivityCompat.invalidateOptionsMenu(Home.this);
        } else if (id == R.id.nav_bookmark) {
            fragment = new BookmarkFragment();
            setTitle(R.string.action_bookmark);
            callFragment(fragment);
            itemIsVisible=true;
            ActivityCompat.invalidateOptionsMenu(Home.this);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(Home.this, Settings.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_writter) {
            int dialog1 = R.layout.dialog_penulis;
            openDialogPenulis(dialog1);
        } else if (id == R.id.nav_about) {
            int dialog2 = R.layout.dialog_about;
            openDialogAbout(dialog2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callFragment(Fragment fragment) {
        transaction = manager.beginTransaction();

        transaction.remove(fragment);
        transaction.replace(R.id.my_layout, fragment);
        transaction.commit();
    }


    private void openDialogAbout(int id){
        LayoutInflater inflater= Home.this.getLayoutInflater();
        View v = inflater.inflate(id ,null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setView(v);

        builder.setPositiveButton("OK", null); //Set to null. We override the onclick
        AlertDialog d = builder.create();
        d.show();
    }

    private void openDialogPenulis(int id){
        LayoutInflater inflater= Home.this.getLayoutInflater();
        View v = inflater.inflate(id ,null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setView(v);

        builder.setPositiveButton("Lihat Tulisan Asli", null); //Set to null. We override the onclick
        AlertDialog d = builder.create();
        d.show();

        d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url="https://www.kaskus.co.id/thread/5704f364947868d61e8b4569/tamat-dia-dia-dia-sempurna-reborn/";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }

    private void deleteBookmark(){
        dbHelper dbhelper = new dbHelper(Home.this);
        int i;
        for (i=0;i<myList.size();i++){
            dbhelper.deleteBookmark(myList.get(i));
        }

        fragment = new BookmarkFragment();
        setTitle(R.string.action_bookmark);
        callFragment(fragment);
        itemIsVisible=true;
        ActivityCompat.invalidateOptionsMenu(Home.this);
    }

    @Override
    public void onDataPass(List<Integer> bookList) {
        myList = bookList;
    }
}
