package com.mytcc.appuser.ModoOperador.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytcc.appuser.ModoOperador.Viagem;
import com.mytcc.appuser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListaViagensAdapter extends BaseAdapter {
    private ArrayList<Viagem> viagens;
    private int viewHolder;

    private Activity context;
    private LayoutInflater inflater;

    public ListaViagensAdapter(Activity context, ArrayList<Viagem> viagens, int viewHolder) {
        super();

        this.context = context;
        this.viagens = viagens;
        this.viewHolder = viewHolder;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viagens.size();
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
        TextView listItemCodigo;
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
            holder.listItemCodigo = (TextView) convertView.findViewById(R.id.listItemCodigo);
            holder.listItemOrigem = (TextView) convertView.findViewById(R.id.listItemOrigem);
            holder.listItemDestino = (TextView) convertView.findViewById(R.id.listItemDestino);
            holder.listItemPartida = (TextView) convertView.findViewById(R.id.listItemPartida);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        holder.imgViewLogo.setImageResource(R.drawable.icon_ticket);
        holder.listItemCodigo.setText(viagens.get(position).getId());
        holder.listItemOrigem.setText(viagens.get(position).getOrigem());
        holder.listItemDestino.setText(viagens.get(position).getDestino());
        holder.listItemPartida.setText(viagens.get(position).getPartidaString(new SimpleDateFormat("MM/dd/yyyy HH:mm")));

        return convertView;
    }
}
