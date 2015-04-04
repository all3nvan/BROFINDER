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
        View rowView = convertView;
        ViewHolder viewHolder;

        if(rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(layoutResourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(R.id.display_name_textview);
            viewHolder.accountName = (TextView) rowView.findViewById(R.id.account_name_textview);
            rowView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) rowView.getTag();
        User user = data.get(position);
        viewHolder.name.setText(user.getDisplayName());
        viewHolder.accountName.setText(user.getEmail());

        return rowView;
    }

    static class ViewHolder {
        public TextView name;
        public TextView accountName;
    }
}
