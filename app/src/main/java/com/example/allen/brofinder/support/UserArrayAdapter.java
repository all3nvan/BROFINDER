package com.example.allen.brofinder.support;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.User;

import java.util.List;

public class UserArrayAdapter extends ArrayAdapter<User> {
    private Context context;
    private int layoutResourceId;
    private List<User> data;

    public UserArrayAdapter(Context context, int layoutResourceId, List<User> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView textViewName = (TextView) convertView.findViewById(R.id.user_item_text);
        User view = data.get(position);
        textViewName.setText(view.getDisplayName());

        return convertView;
    }
}
