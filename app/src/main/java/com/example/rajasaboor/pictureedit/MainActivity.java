package com.example.rajasaboor.pictureedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.rajasaboor.pictureedit.consts.Consts;
import com.example.rajasaboor.pictureedit.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Util.IOnSelectImageOptionsDialog {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int IMAGES_REQUEST_CODE = 101;
    private ConstraintLayout parentLayout;
    private ImageView selectedImageView;

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
        Log.d(TAG, "iniViews: start");
        parentLayout = (ConstraintLayout) findViewById(R.id.parent_layout);
        selectedImageView = (ImageView) findViewById(R.id.selected_image_view);

        parentLayout.setOnClickListener(this);
        Log.d(TAG, "iniViews: end");
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
                openUpGallery();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void openUpGallery() {
        Log.d(TAG, "openUpGallery: start");
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, IMAGES_REQUEST_CODE);
        Log.d(TAG, "openUpGallery: end");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parent_layout:
                openUpDialogForImageSelect();
                break;
        }
    }

    private void openUpDialogForImageSelect() {
        Util.createAlertDialog(this, Consts.SELECT_IMAGES_DIALOG_TITLE, true, MainActivity.this).show();
    }

    @Override
    public void onOptionSelected(int position) {
        Log.d(TAG, "onOptionSelected: start");
        Log.d(TAG, "onOptionSelected: Tapped option is ===> " + Consts.SELECT_IMAGES_DIALOG_OPTIONS[position]);
        switch (position){
            case 0:
                openUpGallery();
                break;
        }
        Log.d(TAG, "onOptionSelected: end");
    }
}
