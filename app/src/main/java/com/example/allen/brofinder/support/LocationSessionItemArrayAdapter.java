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

    private static class ViewHolder{
        TextView name;
        TextView latitude;
        TextView longitude;
    }

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
        ViewHolder viewHolder;
        LocationSession locationSessionObj = data.get(position);

        if (convertView == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.location_session_item_name);
            viewHolder.latitude = (TextView) convertView.findViewById(R.id.location_session_item_latitude);
            viewHolder.longitude = (TextView) convertView.findViewById(R.id.location_session_item_longitude);

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(locationSessionObj.getSenderName());
        viewHolder.latitude.setText(((Float)locationSessionObj.getLatitude()).toString());
        viewHolder.longitude.setText(((Float)locationSessionObj.getLongitude()).toString());



        return convertView;
    }




}
