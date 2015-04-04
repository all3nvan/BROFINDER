package com.example.allen.brofinder.activity;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.LocationSession;
import com.example.allen.brofinder.support.LocationSessionItemArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocationSessionFragment extends Fragment {
    private final static String TAG = "LocationSessionFragment";
    private ListView locationSessionView;

    //TODO Location Session Factory

    public static LocationSessionFragment newInstance(){
        LocationSessionFragment locationSessionFragment = new LocationSessionFragment();
        return locationSessionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_location_session, container, false);
        locationSessionView = (ListView) view.findViewById(R.id.location_session_listview);
        List<LocationSession> locationSessions = testRetrieveLocationSession();
        LocationSessionItemArrayAdapter adapter = new LocationSessionItemArrayAdapter(getActivity(), R.layout.listview_location_session_row, locationSessions);
        return view;

    }

    private List<LocationSession> testRetrieveLocationSession(){
        List<LocationSession> locationSessionList = new ArrayList<LocationSession>();

        for(int i = 0; i < 100; i++)
            locationSessionList.add(new LocationSession(1.40f, 1.23f, "bryant"));

        return locationSessionList;

    }
}


