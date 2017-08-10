package com.example.rajasaboor.pictureedit.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rajasaboor.pictureedit.R;
import com.example.rajasaboor.pictureedit.consts.Consts;

import java.util.Arrays;

/**
 * Created by rajaSaboor on 8/10/2017.
 */

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    public static AlertDialog.Builder createAlertDialog(Context context, String title, boolean isCancelAble, final IOnSelectImageOptionsDialog onSelectImageOptionsDialog) {
        Log.d(TAG, "createAlertDialog: start");
        View view = LayoutInflater.from(context).inflate(R.layout.select_image_list_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setCancelable(isCancelAble);

        ListView listView = view.findViewById(R.id.select_image_options_list_view);
        listView.setAdapter(getSelectImagesOptionsAdapter(context));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onSelectImageOptionsDialog != null){
                    onSelectImageOptionsDialog.onOptionSelected(i);
                }else{
                    Log.e(TAG, "onItemClick: onSelectImageOptionsDialog is NULL");
                }
            }
        });

        Log.d(TAG, "createAlertDialog: end");
        return builder;
    }

    static ArrayAdapter<String> getSelectImagesOptionsAdapter(Context context) {
        return new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, Arrays.asList(Consts.SELECT_IMAGES_DIALOG_OPTIONS));
    }

    public interface IOnSelectImageOptionsDialog {
        void onOptionSelected(int position);
    }
}
