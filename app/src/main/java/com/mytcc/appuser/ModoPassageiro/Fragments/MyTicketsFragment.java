package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mytcc.appuser.ModoPassageiro.MainActivity;
import com.mytcc.appuser.ModoPassageiro.ListViewAdapter.MyTicketsAdapter;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;

public class MyTicketsFragment extends Fragment {
    public static final String TAG = "MyTicketsFragment";

    private MyApplication myApp;

    private MyTicketsAdapter adapter;

    private ListView listView;

    public MyTicketsFragment() {
        Log.d(TAG, "MyTicketsFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MyTicketsFragment.onCreateView()");

        myApp = (MyApplication)getActivity().getApplication();

        View rootView;

        if(myApp.getMyUser().getnTickets() > 0) {
            rootView = inflater.inflate(R.layout.fragment_mytickets, container, false);

            listView = (ListView) rootView.findViewById(R.id.listView_mytickets);

            adapter = new MyTicketsAdapter(getActivity(),
                    myApp.getMyUser().getTickets(), R.layout.list_item_mytickets);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Todo: DialogBox to change User Data

                    Activity act = getActivity();
                    if (act instanceof MainActivity) {
                        ((MainActivity) act).setNewTicketViagem(myApp.getMyUser().getTickets().get(position));
                        ((MainActivity) act).switchFragments(TicketFragment.TAG);
                    }
                }
            });
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_mytickets_empty, container, false);
        }

        return rootView;
    }
}
