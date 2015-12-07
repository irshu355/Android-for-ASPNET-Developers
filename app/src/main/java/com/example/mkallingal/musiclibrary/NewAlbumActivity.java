package com.example.mkallingal.musiclibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewAlbumActivity extends AppCompatActivity {
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*
       * findViewById() is used to access any control that you want to access from your view.
       * It's the same as $('#control_id'); in jQuery. Once you have access to the object, you will have access to several -
       * getter/setter for that control including event handlers
       *
       */

        final TextView txtAlbumName= (TextView)findViewById(R.id.txtAlbumTitle); // same as $('#txtAlbumName') in jQuery
        final Spinner ddlAlbumGenre= (Spinner) findViewById(R.id.ddlAlbumGenre);
        final TextView txtAlbumPrice= (TextView) findViewById(R.id.txtAlbumPrice);
        final SwitchCompat chkAvailability= (SwitchCompat) findViewById(R.id.chkAvailability);
        Button _btnSave= (Button) findViewById(R.id.btnSave);

        /*
        * add an event handler to _btnSave
        */
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album _Album=new Album();
                _Album.AlbumTitle = txtAlbumName.getText().toString();
                _Album.AlbumGenre= ddlAlbumGenre.getSelectedItem().toString();
                _Album.AlbumPrice= Double.valueOf(txtAlbumPrice.getText().toString());
                _Album.Availability= chkAvailability.isChecked();

               /*
               * Now we need to store this information in our device.There are several storage options available in android, for more-
               * detials, please visit http://developer.android.com/intl/ja/guide/topics/data/data-storage.html
               * For our case, we want to store our model as a key-value pair. SO we'll go with "SharedPreferences"
               * In order to store as string, we need to serialize our model to string, for this, we need to add the library to our -
               * build.gradle (packages.config in ASP.NET). open Gradle Scripts -> build.gradle(Module:app) and add this line:
               * compile 'com.google.code.gson:gson:2.3.1'
               * It will download the .jar file and add it to our library, it's that easy!!!
               * So now you can instantiate gson by new Gson();
               * */

                gson=new Gson();
                String SerializedString = gson.toJson(_Album);

                /*
                * Let's nsert it to our storage, SharedPreferences
                * Think of it as the LocalStorage API in HTML5
                * For our tutorial, we need to make sure if we have inserted any albums before, we need to handle this case
                */


                try {
                    SharedPreferences _Preferences= getSharedPreferences("MusicLibrary", 0);
                    final String _ALBUMS_Key="Albums";
                    String _AlbumsString=_Preferences.getString(_ALBUMS_Key, ""); //signature: Preferences.getString(Key (string),Default_value(string))
                    SharedPreferences.Editor editor = _Preferences.edit();
                    if(_AlbumsString.length()!=0){

                         /*
                        * So we need to retrieve the serialized list, deserialize to List<Album>, add the new album into it, serialize it again and save it
                        * to the SharedPreferences
                        */
                        Type _TypeofAlbum = new TypeToken<List<Album>>() {
                        }.getType();
                        List<Album> _Albums = gson.fromJson(_AlbumsString, _TypeofAlbum);
                        _Albums.add(_Album);
                        String _SerializedAlbums = gson.toJson(_Albums);
                        editor.putString(_ALBUMS_Key,_SerializedAlbums);
                        editor.apply();
                    }else{
                         /*
                        * Instantiate a new List<Album>, add our new album into it, serialize it and save it to SharedPreferences
                        */
                        List<Album> _Albums=new ArrayList<Album>();
                        _Albums.add(_Album);
                        String _SerializedAlbums = gson.toJson(_Albums);
                        editor.putString(_ALBUMS_Key, _SerializedAlbums);
                        editor.apply();
                    }
                    Snackbar.make(v, "Album inserted successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
