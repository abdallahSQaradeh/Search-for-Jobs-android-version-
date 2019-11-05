package com.example.webscrappingjobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JobsList extends AppCompatActivity implements OnItemClicked,BottomNavigationView.OnNavigationItemSelectedListener, Closeable {
    String url;
    int threadret=0;
    ListView listview;
    ArrayList<JobsView> posts;
    JobsAdapter listadapter;
    ArrayList<JobsView> all_jobs = new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        finish();
        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       int thread=0;
        if (getIntent().getIntExtra("src", 0) == 1) {
            url  = getIntent().getStringExtra("link");
        } else if (getIntent().getIntExtra("src", 0) == 2)
            url = getIntent().getStringExtra("link");
            try {
                getjobs();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listview = findViewById(R.id.jobs_list_);
            posts = new ArrayList<>();
            while(threadret!=1 )
            {
                try {
                    getjobs();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int o=0;o<all_jobs.size();o++) {

                posts.add(all_jobs.get(o));
                Log.e("size"," "+all_jobs.size());
            }
           // Toast.makeText(this,all_jobs.size(),Toast.LENGTH_SHORT).show();
            Log.e("size"," "+all_jobs.size());

            listadapter = new JobsAdapter(this,posts);
            //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, array);
            listadapter.setOnClick(new OnItemClicked() {
                @Override
                public void onItemClick(int position) {

                    JobsView job=all_jobs.get(position);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(job.link));
                    startActivity(i);
                }
            });
            listview.setAdapter(listadapter);
            //listview.setOnItemClickListener(this);
        }



    @Override
    public void onItemClick(int position) {


    }


    public void getjobs() throws InterruptedException {
        Runnable job = new LoadJobs();
        Thread get = new Thread(job);
        get.setDaemon(true);
        get.setPriority(Thread.MAX_PRIORITY);
        get.join();
        get.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void close() throws IOException {

    }

    class LoadJobs implements Runnable {

        @Override
        public void run() {
            try {
                HashSet<JobsView> job_list;
                JobsView currentjob;
                Connection con = Jsoup.connect(url);
                con.timeout(10*1000);
                Document doc =con.get();
                String title = doc.title();
                Element body = doc.body();
                Elements table = body.getElementsByClass("list-3--body");
                Elements content = table.select("a");
                // Elements link = doc.select("tr");
                job_list = new HashSet<>();
                for (Element i : content) {
                    currentjob = new JobsView("");
                    currentjob.link = i.attr("href");
                    currentjob.name = i.attr("title");
                    Elements info = i.children();
                    currentjob.company= info.get(0).child(0).text();
                    currentjob.date = info.get(2).text();
                    job_list.add(currentjob);
                    all_jobs.add(currentjob);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            threadret=1;

        }

    }

}