package com.mytcc.appuser.ModoPassageiro.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytcc.appuser.ModoPassageiro.Passagem;
import com.mytcc.appuser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyTicketsAdapter extends BaseAdapter {
    private ArrayList<Passagem> tickets;
    private int viewHolder;

    private Activity context;
    private LayoutInflater inflater;

    public MyTicketsAdapter(Activity context, ArrayList<Passagem> passagens, int viewHolder) {
        super();

        this.context = context;
        this.tickets = passagens;
        this.viewHolder = viewHolder;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        ImageView imgViewLogo;
        TextView listItemOrigem;
        TextView listItemDestino;
        TextView listItemPartida;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(viewHolder, null);

            holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.imgViewLogo);
            holder.listItemOrigem = (TextView) convertView.findViewById(R.id.listItemOrigem);
            holder.listItemDestino = (TextView) convertView.findViewById(R.id.listItemDestino);
            holder.listItemPartida = (TextView) convertView.findViewById(R.id.listItemPartida);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        holder.imgViewLogo.setImageResource(R.drawable.icon_ticket);
        holder.listItemOrigem.setText(tickets.get(position).getOrigem());
        holder.listItemDestino.setText(tickets.get(position).getDestino());
        holder.listItemPartida.setText(tickets.get(position).getPartidaString(new SimpleDateFormat("MM/dd/yyyy HH:mm")));

        return convertView;
    }
}
