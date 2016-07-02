package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mytcc.appuser.ModoPassageiro.ListViewAdapter.MyAccountListAdapter;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;

public class MyAccountFragment extends Fragment {
    public static final String TAG = "MyAccountFragment";

    private MyApplication myApp;

    private ListView listView;

    private MyAccountListAdapter adapter;

    private static int[] title = {R.string.myaccount_list_nome,
            R.string.myaccount_list_sobrenome,
            R.string.myaccount_list_email,
            R.string.myaccount_list_data,
            R.string.myaccount_list_phone,
            R.string.myaccount_list_cpf };

    private static String conteudo[] = new String[6];
    private Drawable icons[] = new Drawable[6];

    public MyAccountFragment() {
        Log.d(TAG, "MyAccountFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MyAccountFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_myaccount, container, false);

        myApp = (MyApplication)getActivity().getApplication();

        listView = (ListView)rootView.findViewById(R.id.listView_myaccount);

        conteudo[0] = myApp.getMyUser().getNome();
        conteudo[1] = myApp.getMyUser().getSobrenome();
        conteudo[2] = myApp.getMyUser().getEmail();
        conteudo[3] = myApp.getMyUser().getData_nascimento();
        conteudo[4] = myApp.getMyUser().getPhone();
        conteudo[5] = myApp.getMyUser().getCpf();

        icons[0] = getResources().getDrawable(R.mipmap.icon_user);
        icons[1] = getResources().getDrawable(R.mipmap.icon_user);
        icons[2] = getResources().getDrawable(R.mipmap.icon_email);
        icons[3] = getResources().getDrawable(R.mipmap.icon_calendar);
        icons[4] = getResources().getDrawable(R.mipmap.icon_phone);
        icons[5] = getResources().getDrawable(R.mipmap.icon_key);

        adapter = new MyAccountListAdapter(getActivity(),
                title, conteudo, icons, R.layout.list_item_myaccount);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Title => " + title[position] + " n Conteudo => " + conteudo[position], Toast.LENGTH_SHORT).show();
                //Todo: DialogBox to change User Data
            }
        });

        return rootView;
    }
}
