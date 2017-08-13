package com.example.rajasaboor.pictureedit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rajasaboor.pictureedit.R;
import com.example.rajasaboor.pictureedit.consts.Consts;
import com.example.rajasaboor.pictureedit.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by rajaSaboor on 8/12/2017.
 */

public class MainActivityFragment extends Fragment implements View.OnClickListener, Util.IOnSelectImageOptionsDialog {
    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private ConstraintLayout parentLayout;
    private ImageView selectedImageView;
    private Dialog loadingDialog;
    private Dialog loadImageOptionsDialog;


    public MainActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        iniViews(view);
        parentLayout.setOnClickListener(this);
        selectedImageView.setOnClickListener(this);
        Util.initImageLoader(getContext());

        Log.d(TAG, "onCreateView: end");
        return view;
    }

    private void iniViews(View view) {
        Log.d(TAG, "iniViews: start");
        parentLayout = view.findViewById(R.id.parent_layout);
        selectedImageView = view.findViewById(R.id.selected_image_view);
        Log.d(TAG, "iniViews: end");
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: start");
        switch (view.getId()) {
            case R.id.parent_layout:
            case R.id.selected_image_view:
                Log.d(TAG, "onClick: inside the CASES");
                openUpDialogForImageSelect();
                break;
        }
        Log.d(TAG, "onClick: end");
    }

    public void openUpDialogForImageSelect() {
        loadImageOptionsDialog = Util.createAlertDialog(getContext(), Consts.SELECT_IMAGES_DIALOG_TITLE, true, this).show();
    }

    @Override
    public void onOptionSelected(int position) {
        Log.d(TAG, "onOptionSelected: start");
        switch (position) {
            case 0:
                openUpGallery();
                break;
        }
        Log.d(TAG, "onOptionSelected: end");
    }

    public void openUpGallery() {
        Log.d(TAG, "openUpGallery: start");
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(galleryIntent, Consts.IMAGES_REQUEST_CODE);
        Log.d(TAG, "openUpGallery: end");
    }

    public Dialog getLoadImageOptionsDialog() {
        return loadImageOptionsDialog;
    }

    private AlertDialog.Builder createProgressDialog() {
        Log.d(TAG, "createProgressDialog: start");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setView(R.layout.progress_dialog);


        Log.d(TAG, "createProgressDialog: end");
        return builder;
    }

    public void setImageToTheImageView(Uri selectedImageURI) {
        Log.d(TAG, "setImageToTheImageView: start");


        String temp = selectedImageURI.getLastPathSegment();
        temp = temp.substring(temp.indexOf(':') + 1);
        String selection = MediaStore.Images.ImageColumns._ID + " = " + temp;

        DoInBackground doInBackground = new DoInBackground(new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.WIDTH, MediaStore.Images.ImageColumns.HEIGHT},
                selection, selectedImageURI);
        doInBackground.execute();

        Log.d(TAG, "setImageToTheImageView: URI ===> " + selectedImageURI);
        Log.d(TAG, "setImageToTheImageView: Temp ===> " + temp);
        Log.d(TAG, "setImageToTheImageView: Path ==> " + temp.substring(temp.indexOf(':') + 1));
        Log.d(TAG, "setImageToTheImageView: end");
    }


    private class DoInBackground extends AsyncTask<Void, Void, Void> {
        private String[] projection;
        private String selection;
        private Uri selectedImageURI;
        private boolean isRotateRequire = false;

        DoInBackground(String[] projection, final String selection, Uri selectedImageURI) {
            this.projection = projection;
            this.selection = selection;
            this.selectedImageURI = selectedImageURI;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: start");
            super.onPreExecute();
            loadingDialog = createProgressDialog().create();
            loadingDialog.show();

            Log.d(TAG, "onPreExecute: end");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: start");
            try {
                Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null, null);
                cursor.moveToFirst();

                int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH));
                Log.d(TAG, "setImageToTheImageView: ID ===> " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));
                Log.d(TAG, "setImageToTheImageView: Title ===> " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.TITLE)));
                Log.d(TAG, "setImageToTheImageView: Width ===> " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)));
                Log.d(TAG, "setImageToTheImageView: Height ===> " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT)));
                Log.d(TAG, "doInBackground: ***********************");

                if (width >= 6000) {
                    setRotateRequire(true);
                    Log.d(TAG, "doInBackground: rotate the image");
                } else {
                    setRotateRequire(false);
                    Log.d(TAG, "doInBackground: No need to rotate");
                }

                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: start");
//            super.onPostExecute(aVoid);
            ImageLoader.getInstance().displayImage(selectedImageURI.toString(), selectedImageView);


//            if (isRotateRequire()) {
//                selectedImageView.setRotation(40);
//            }
            Log.d(TAG, "onPostExecute: end");
            loadingDialog.dismiss();
        }

        public boolean isRotateRequire() {
            return isRotateRequire;
        }

        public void setRotateRequire(boolean rotateRequire) {
            isRotateRequire = rotateRequire;
        }
    }

    public void createImageEditMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: start");
        menu.clear();
        inflater.inflate(R.menu.image_edit_menu, menu);
        Log.d(TAG, "onCreateOptionsMenu: end");
    }
}
