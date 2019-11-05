package com.example.webscrappingjobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
private OnItemClicked onClick;
private Context mContext;
private List<CityView> citylist = new ArrayList<>();


public CityAdapter(List<CityView> cityv, Context context) {

        this.citylist=cityv;
        mContext = context;
        }

@NonNull
@Override
public CityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//called when view created
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_card, viewGroup, false);
        MyViewHolder myviewholder = new MyViewHolder(itemView);
        return myviewholder;

        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {//bind data with holder
        CityView city = citylist.get(i);
        myViewHolder.title.setText(city.getName());
        myViewHolder.title.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v) {


        }
        });

        }

@Override
public int getItemCount() {
        return citylist.size();
        }


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title, count;



    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        ;
    }
}


    }


