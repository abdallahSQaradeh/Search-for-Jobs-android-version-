package com.example.webscrappingjobs;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatigoryAdapter extends RecyclerView.Adapter<CatigoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<CatigoriesView> catigorylist;
    private int images[];
    private OnItemClicked onClick;
    public CatigoryAdapter(List<CatigoriesView> catigoryv, Context context,int []images) {
        this.images = images;
        this.catigorylist =catigoryv;
        mContext = context;
    }

    @NonNull
    @Override
    public CatigoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//called when view created
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.catigory_card, viewGroup, false);
        MyViewHolder myviewholder = new MyViewHolder(itemView);
        return myviewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {//bind data with holder
        int image_id = images[i];
       CatigoriesView catigory = catigorylist.get(i);
       myViewHolder.thumbnail.setImageResource(image_id);
       myViewHolder.title.setText(catigory.getName());
     myViewHolder.title.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {

               onClick.onItemClick(i);
               Log.e("clcicked","clicked_-------------------------------------------------------------");

           }
       });

    }

    @Override
    public int getItemCount() {
        return catigorylist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, count;
        public ImageView thumbnail, overflow;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.catigory);
           ;
        }
        }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
    }


