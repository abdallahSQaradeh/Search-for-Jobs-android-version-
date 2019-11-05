package com.example.webscrappingjobs;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * A simple {@link Fragment} subclass.
ci */
public  class CityFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener,OnItemClicked {

    private ProgressBar spinner;

    int threadret=0;
    ListView listview;
    ArrayList<CityView> posts;
    CityListAdapter listadapter;
    View viewx;
    ArrayList<CityView> all_city = new ArrayList<>();
    String cityurl = "https://www.jobs.ps/locations";
    Context con;
    public CityFragment() {
        // Required empty public constructor
    }
    ProgressDialog prog;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        con = context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            viewx = view;
           ;
            getCity();

            spinner=(ProgressBar)view.findViewById(R.id.progressBar);
            spinner.setVisibility(View.VISIBLE);
            //prog.dismiss();
           Thread.sleep(1500);
            spinner.setVisibility(view.GONE);



        } catch (InterruptedException e) {
            Log.e("InterruptedException", e.getStackTrace().toString());
            e.printStackTrace();
        }
        listview = view.findViewById(R.id.city_list_fragment);
        posts = new ArrayList<>();
        for (int o = 0; o < all_city.size(); o++) {

            posts.add(all_city.get(o));
        }
        Log.e("throw", " " + Thread.activeCount());
        listadapter = new CityListAdapter(getContext(), posts);
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, array);
        listadapter.setOnClick(new OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                if(checkactivation() || checkactivation2()) {
                    CityView city = all_city.get(position);
                    Intent i = new Intent(getContext(), JobsList.class);
                    i.putExtra("link", city.link);
                    i.putExtra("src", 2);
                    startActivity(i);
                }

            }
        });
        prog.dismiss();
        listview.setAdapter(listadapter);


    }

    @Override
    public synchronized View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewx = inflater.inflate(R.layout.fragment_city, container, false);
        prog= new ProgressDialog(viewx.getContext());
        prog.show();

            return viewx;

        }

    void  getCity() throws InterruptedException {

        Cities cities_ = new Cities();
        Thread execute = new Thread(cities_);
        execute.setDaemon(true);
        execute.setPriority(Thread.MAX_PRIORITY);
        execute.join();
        execute.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    @Override
    public void onItemClick(int position) {
        CityView city =all_city.get(position);
        Intent i = new Intent(getParentFragment().getContext(),JobsList.class);
        i.putExtra("link",city.link);
        i.putExtra("src",2);
        startActivity(i);
    }

    class city {
        String name;;
        String link;
    }

   public class Cities implements Runnable {

        @Override
        public  void run() {
            final StringBuilder builder = new StringBuilder();

            Log.e("GET","getting job");
            String cityurl = "https://www.jobs.ps/locations";
            ArrayList<CityView> city_list;
            CityView currentcity;
            Document doc = null;
            try {
                doc = (Document) Jsoup.connect(cityurl).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IOException",e.getStackTrace().toString());
            }
            String title = doc.title();
            Element body = doc.body();
            Elements table = body.getElementsByClass("list-6--browse_locations");
            Elements cityinfo = table.select("a");
            Object element[] =  cityinfo.toArray();
            city_list = new ArrayList<>();
            for (Object i : element) {
                currentcity = new CityView("");
                currentcity.link= ((Element)i).attr("href");
                currentcity.name = ((Element)i).attr("title");
                city_list.add(currentcity);
            }


            all_city=city_list;
            threadret=1;

            Log.e("GET","end getting job");


            // Log.e("inflate", " " + "Inflate");
        }
    }
    boolean checkactivation()
    {
        ConnectivityManager connMgr =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d("DEBUG", "Wifi connected: " + isWifiConn);
        Log.d("DEBUG", "Mobile connected: " + isMobileConn);
        return  isWifiConn;
    }
    boolean checkactivation2()
    {
        ConnectivityManager connMgr =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d("DEBUG", "Wifi connected: " + isWifiConn);
        Log.d("DEBUG", "Mobile connected: " + isMobileConn);
        return  isMobileConn;
    }


}
