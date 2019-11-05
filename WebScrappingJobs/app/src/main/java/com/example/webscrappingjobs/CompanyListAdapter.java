package com.example.webscrappingjobs;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

public class CompanyListAdapter extends ArrayAdapter<CompanyView> {
    private OnItemClicked onClick;
    public CompanyListAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.company_list_item,parent,false);
        TextView title = convertView.findViewById(R.id.compnayname);
        TextView location = convertView.findViewById(R.id.companylocation);
        Button info = convertView.findViewById(R.id.information);
        ImageView img =convertView.findViewById(R.id.picture);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);

            }
        });
        CompanyView post =getItem(position);
        title.setText(post.companyname);
        location.setText(post.location);
        Picasso.with(getContext()).load(post.imgsrc).resize(200,200).into(img);
        return convertView;
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}