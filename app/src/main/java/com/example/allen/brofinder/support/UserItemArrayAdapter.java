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

public class UserItemArrayAdapter extends ArrayAdapter<User>{

    private static class ViewHolder{
        TextView name;
    }

    Context mContext;
    int layoutResourceId;
    List<User> data = null;

    public UserItemArrayAdapter(Context context, int layoutResourceId, List<User> objects){
        super(context, layoutResourceId, objects);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User userObj = data.get(position);

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.nav_drawer_item_text);
            convertView.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(userObj.getDisplayName());

        return convertView;
    }
}
