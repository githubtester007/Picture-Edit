package com.example.rajasaboor.pictureedit.util;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by rajaSaboor on 8/12/2017.
 */

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = BackgroundTask.class.getSimpleName();


    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: start");
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: end");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: start");
        Log.d(TAG, "doInBackground: end");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(TAG, "onPostExecute: start");
        super.onPostExecute(aVoid);
        Log.d(TAG, "onPostExecute: end");
    }

}
