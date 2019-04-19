package com.example.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class upload2Adapter extends RecyclerView.Adapter<upload2Adapter.ImageHolder> {

    private OnItemClickListener ilistener;
    private Context mcontext;
    private List <upload> lupload;

    public upload2Adapter (Context context, List<upload> uploads) {
        mcontext = context;
        lupload = uploads;
    }


    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item, parent, false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        upload uploadCurrent = lupload.get(position);
        holder.tvname.setText(uploadCurrent.getName());
        Picasso.with(mcontext)
                .load(uploadCurrent.getImageurl())
                .fit()
                .centerCrop()
                .into(holder.ivupload);

    }

    @Override
    public int getItemCount() {
       return lupload.size() ;
    }


    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvname;
        public ImageView ivupload;


        public ImageHolder(View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tvname);
            ivupload = itemView.findViewById(R.id.ivupload);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           if (ilistener !=null){
              int position =  getAdapterPosition();
              if (position != RecyclerView.NO_POSITION){
                  ilistener.onItemclick(position);
              }
           }
        }
    }

    public  interface OnItemClickListener {
        void onItemclick (int position);

        void ondownloadclick (int position);

        void onDeleteclick (int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
    ilistener = listener;
    }
}
