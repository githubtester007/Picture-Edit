package com.example.rajasaboor.pictureedit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rajasaboor.pictureedit.consts.Consts;
import com.example.rajasaboor.pictureedit.fragments.MainActivityFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityFragment mainActivityFragment = null;
    private Uri selectedImageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        iniViews();

        Log.d(TAG, "onCreate: end");
    }

    private void iniViews() {
        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
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
        switch (item.getItemId()) {
            case R.id.gallery_menu_item:
                mainActivityFragment.openUpGallery();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: start");
        Log.d(TAG, "onActivityResult: Request code ===> " + requestCode);
        switch (requestCode) {
            case Consts.IMAGES_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Log.d(TAG, "onActivityResult: inside the IF");
                    try {
                        mainActivityFragment.getLoadImageOptionsDialog().dismiss();
                        setSelectedImageURI(data.getData());
                        mainActivityFragment.setImageToTheImageView(getSelectedImageURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onActivityResult: inside the ELSE");
                }
                break;
        }
        Log.d(TAG, "onActivityResult: end");
    }

    public Uri getSelectedImageURI() {
        return selectedImageURI;
    }

    public void setSelectedImageURI(Uri selectedImageURI) {
        this.selectedImageURI = selectedImageURI;
    }
}
