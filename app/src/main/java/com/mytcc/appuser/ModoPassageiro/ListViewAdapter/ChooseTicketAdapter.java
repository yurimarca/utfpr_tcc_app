package com.mytcc.appuser.ModoPassageiro.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytcc.appuser.R;

public class ChooseTicketAdapter extends BaseAdapter {
    private String partida[];
    private String chegada[];
    private String classe[];
    private String preco[];
    private int viewHolder;

    private Activity context;
    private LayoutInflater inflater;

    public ChooseTicketAdapter(Activity context, String[] partida, String[] chegada, String[] classe, String[] preco, int viewHolder) {
        super();

        this.context = context;
        this.partida = partida;
        this.chegada = chegada;
        this.classe = classe;
        this.preco = preco;
        this.viewHolder = viewHolder;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return partida.length;
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
        TextView listItemPartida;
        TextView listItemChegada;
        TextView listItemClasse;
        TextView listItemPreco;
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
            holder.listItemPartida = (TextView) convertView.findViewById(R.id.listItemPartida);
            holder.listItemChegada = (TextView) convertView.findViewById(R.id.listItemChegada);
            holder.listItemClasse = (TextView) convertView.findViewById(R.id.listItemClasse);
            holder.listItemPreco = (TextView) convertView.findViewById(R.id.listItemPreco);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        holder.imgViewLogo.setImageResource(R.drawable.icon_ticket);
        holder.listItemPartida.setText(partida[position]);
        holder.listItemChegada.setText(chegada[position]);
        holder.listItemClasse.setText(classe[position]);
        holder.listItemPreco.setText(preco[position]);

        return convertView;
    }
}
