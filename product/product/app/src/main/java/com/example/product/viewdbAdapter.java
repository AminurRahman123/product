package com.example.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class viewdbAdapter  extends ArrayAdapter {
    List list = new ArrayList();

    public viewdbAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView name,reference,type,date,time,location,description;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.rowlayout,parent,false);

            layoutHandler = new LayoutHandler();
            layoutHandler.name = (TextView) row.findViewById(R.id.textViewname);
            layoutHandler.reference = (TextView)row.findViewById(R.id.textViewreference);
            layoutHandler.type = (TextView) row.findViewById(R.id.textViewtype);
            layoutHandler.date = (TextView) row.findViewById(R.id.textViewdate);
            layoutHandler.time = (TextView) row.findViewById(R.id.textViewtime);
            layoutHandler.location = (TextView) row.findViewById(R.id.textViewlocation);
            layoutHandler.description = (TextView) row.findViewById(R.id.textViewdesc);
            row.setTag(layoutHandler);


        }
        else{
            layoutHandler =(LayoutHandler) row.getTag();


        }

        dbProvider dbprovider = (dbProvider) this.getItem(position);
        layoutHandler.name.setText(dbprovider.getName());
        layoutHandler.reference.setText(dbprovider.getReference());
        layoutHandler.type.setText(dbprovider.getType());
        layoutHandler.date.setText(dbprovider.getDate());
        layoutHandler.time.setText(dbprovider.getTime());
        layoutHandler.location.setText(dbprovider.getLocation());
        layoutHandler.description.setText(dbprovider.getDescription());

        return row;
    }
}
