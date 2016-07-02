package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mytcc.appuser.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment{
    public static final String TAG = "SettingsFragment";

    public SettingsFragment() {
        Log.d(TAG, "SettingsFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "SettingsFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.statsfragment, container, false);

        //lview_infos = (ListView) rootView.findViewById(R.id.listView_infos);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> content = new ArrayList<>();
        content.add("SettingsFragment");
        content.add("SettingsFragment");
        content.add("SettingsFragment");
        content.add("SettingsFragment");
        ListView listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, content));
    }
}
