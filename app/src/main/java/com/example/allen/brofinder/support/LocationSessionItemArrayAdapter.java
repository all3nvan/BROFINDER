package com.example.allen.brofinder.support;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.LocationSession;

import org.w3c.dom.Text;

import java.util.List;

public class LocationSessionItemArrayAdapter extends ArrayAdapter<LocationSession>{

    Context mContext;
    int layoutResourceId;
    List<LocationSession> data = null;

    public LocationSessionItemArrayAdapter(Context context, int layoutResourceId, List<LocationSession> objects){
        super(context, layoutResourceId, objects);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

        if (convertView == null){
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView textViewName = (TextView) convertView.findViewById(R.id.nav_drawer_item_text);
        LocationSession locationSessionObj = data.get(position);
        textViewName.setText(locationSessionObj.getSenderName());

        return convertView;
    }


}
