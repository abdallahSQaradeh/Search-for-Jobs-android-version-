package com.example.webscrappingjobs;

import android.content.Context;
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

public class JobsAdapter extends ArrayAdapter<JobsView> {
    private OnItemClicked onClick;
    public JobsAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_job,parent,false);
        TextView title = convertView.findViewById(R.id.jobtitle);
        TextView companyname = convertView.findViewById(R.id.companytitle);
        TextView date = convertView.findViewById(R.id.dates);
        Button info = convertView.findViewById(R.id.informationjob);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);

            }
        });
        JobsView post =getItem(position);
        title.setText(post.name);
        companyname.setText(post.company);
        date.setText(post.date);
        return convertView;
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
