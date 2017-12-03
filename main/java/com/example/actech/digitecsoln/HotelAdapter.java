package com.example.actech.digitecsoln;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.LinearSystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACTECH on 12/3/2017.
 */

public class HotelAdapter extends ArrayAdapter{

    List list=new ArrayList();

    public HotelAdapter(Context context, int resource) {
        super(context, resource);

    }


    public void add(Hotels object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position ,View convertView,ViewGroup parent) {

        View row;
        row=convertView;
        hotelHolder holder;
        if(row==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);

            holder=new hotelHolder();
            holder.txname=(TextView)row.findViewById(R.id.nm);
            holder.txrating=(TextView)row.findViewById(R.id.rt);
            holder.txtypes=(TextView)row.findViewById(R.id.typ);
            holder.txvicinity=(TextView)row.findViewById(R.id.vcn);
            row.setTag(holder);

        }
        else {
            holder= (hotelHolder) row.getTag();
        }

        Hotels hotels= (Hotels) this.getItem(position);
        holder.txname.setText(hotels.getName());
        holder.txrating.setText(hotels.getRating());
        holder.txtypes.setText(hotels.getTypes());
        holder.txvicinity.setText(hotels.getVicinity());

        return super.getView(position,convertView,parent);
    }

    static class hotelHolder{
        TextView txname,txrating,txtypes,txvicinity;
    }
}
