package com.example.mkallingal.musiclibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StaggeredGridLayoutManager _staggeredGridLayoutManager;
    private Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Always MUST make sure setContentView is declared on onCreate method for Activity classes, android will inflate that Layout and display it as the view
        * for the activity
        * */

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAlbumActivity.class);
                startActivity(intent);
            }
        });


        List<Album> _Albums= getAlbums();

        if(_Albums!=null&&_Albums.size()>0){
            RecyclerView rvGrid= (RecyclerView) findViewById(R.id.rvAlbums);
            rvGrid.setHasFixedSize(true);

            /*
            * set grid layout (masonry style) for our recyclerview.
            */

            _staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            rvGrid.setLayoutManager(_staggeredGridLayoutManager);

          /*
           * Context in android is similar to HttpContext in ASP.NET. It has all the details about the state, the resources and other details
           * of our app
           * We need to set an Adapter for our RecyclerView. I created AlbumsRecyclerViewAdapter.java in our app, so i'm gonna feed that
           * adapter to RecycerView
           * */

            AlbumsRecyclerViewAdapter rcAdapter = new AlbumsRecyclerViewAdapter(getApplicationContext(), _Albums);
            rvGrid.setAdapter(rcAdapter);
        }



    }

    private List<Album> getAlbums() {
        SharedPreferences _Preferences= getSharedPreferences("MusicLibrary", 0);
        final String _ALBUMS_Key="Albums";
        String _AlbumsString=_Preferences.getString(_ALBUMS_Key, "");
        Type _TypeofAlbum = new TypeToken<List<Album>>() {
        }.getType();
        List<Album> _Albums = gson.fromJson(_AlbumsString, _TypeofAlbum);
        return  _Albums;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
