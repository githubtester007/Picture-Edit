package com.example.rajasaboor.pictureedit.util;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rajasaboor.pictureedit.R;
import com.example.rajasaboor.pictureedit.consts.Consts;

import java.util.Arrays;

/**
 * Created by rajaSaboor on 8/10/2017.
 */

public class CustomDialog extends DialogFragment {
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_image_list_view, container, false);
        listView = view.findViewById(R.id.select_image_options_list_view);
        listView.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, Arrays.asList(Consts.SELECT_IMAGES_DIALOG_OPTIONS)));

        return view;
    }
}
