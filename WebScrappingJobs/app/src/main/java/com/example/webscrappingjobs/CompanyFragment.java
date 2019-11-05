package com.example.webscrappingjobs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class CompanyFragment extends Fragment implements  AdapterView.OnItemClickListener , OnItemClicked{


    int threadret=0;
    ListView listview;
    ArrayList<CompanyView> posts;
    CompanyListAdapter listadapter;
    ArrayList<CompanyView> all_companies = new ArrayList<>();
    String companyurl = "https://www.jobs.ps/employers";
   public CompanyFragment()
   {

   }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.company_list_f);
        posts = new ArrayList<>();
        threadret=0;
        int index;
        for(int o=0;o<all_companies.size();o++) {

            posts.add(all_companies.get(o));
        }
        listadapter = new CompanyListAdapter(getContext(),posts);
        listadapter.setOnClick(new OnItemClicked() {
            @Override
            public void onItemClick(int position) {

                CompanyView comp = all_companies.get(position);
                Intent views = new Intent(Intent.ACTION_VIEW);
                views.setData(Uri.parse(comp.link));
                startActivity(views);
            }
        });;
        listview.setAdapter(listadapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.company_fragment, container, false);

        try {
            getCompany();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getCompany() throws InterruptedException {
      Companies companiees_= new Companies();
        Thread execute = new Thread(companiees_);
        execute.setDaemon(true);
        execute.setPriority(Thread.MAX_PRIORITY);
       // execute.join();
        execute.start();
    }

    @Override
    public void onItemClick(int position) {

        CompanyView comp = all_companies.get(position);
        Intent views = new Intent(Intent.ACTION_VIEW);
        views.setData(Uri.parse(comp.link));
        startActivity(views);

    }

    class Companies implements Runnable {

        @Override
        public void run() {
            final StringBuilder builder = new StringBuilder();


            ArrayList<CompanyView> company_list;
            CompanyView currentcompany;
            String single_url;
            Document doc = null;
            try {
                doc = (Document) Jsoup.connect(companyurl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String title = doc.title();
            Element body = doc.body();
            Elements table = body.getElementsByClass("list-3--row flex-r");
            Object element[] = table.toArray();
            company_list = new ArrayList<>();
            for (Object i : element) {
                currentcompany= new CompanyView("");
                Elements inner =((Element)i).select("div");
                Elements img = inner.get(0).getElementsByTag("img");
                currentcompany.imgsrc=img.attr("src");
                currentcompany.companyname = img.attr("title");
                Element link = inner.get(2);
                currentcompany.link = link.getElementsByTag("a").attr("href");
                currentcompany.location=  inner.last().text();
                company_list.add(currentcompany);
            }


            all_companies= company_list;
        }
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }







}
