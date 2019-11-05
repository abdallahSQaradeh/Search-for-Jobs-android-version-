package com.example.webscrappingjobs;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity  implements/* OnNavigationItemSelectedListener,*/OnItemClicked{

    String [] url = {"https://www.jobs.ps/categories/social-science-jobs","https://www.jobs.ps/categories/sales-marketing-jobs",
            "https://www.jobs.ps/categories/public-relation-jobs","https://www.jobs.ps/categories/press-media-jobs",
            "https://www.jobs.ps/categories/others-jobs","https://www.jobs.ps/categories/operations-jobs",
            "https://www.jobs.ps/categories/legal-jobs","https://www.jobs.ps/categories/it-jobs",
            "https://www.jobs.ps/categories/hospitality-tourism-jobs","https://www.jobs.ps/categories/healthcare-jobs",
            "https://www.jobs.ps/categories/graphic-design-jobs","https://www.jobs.ps/categories/languages-and-translation-jobs",
    "https://www.jobs.ps/categories/accounting-finance-jobs","https://www.jobs.ps/categories/engineering-jobs",
    "https://www.jobs.ps/categories/education-training-jobs","https://www.jobs.ps/categories/culture-arts-jobs",
    "https://www.jobs.ps/categories/business-administration-jobs"};
    int [] images ={R.drawable.team,R.drawable.inboundicon,R.drawable.rel,R.drawable.video,
            R.drawable.other,R.drawable.maintenance,R.drawable.lawss,
            R.drawable.technology_chip,R.drawable.hotell,R.drawable.aid,
            R.drawable.art,R.drawable.translation,R.drawable.bank,R.drawable.eng,
            R.drawable.educatioicon,R.drawable.graphic,R.drawable.managment};
    List<CatigoriesView> all_Categoray =  new  ArrayList<>();
    HashMap<String, HashSet<Job>> all_jobs = new LinkedHashMap<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoumanager;
    private Button getBtn;
    private TextView result;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewpager;
    private TabLayout tablayout;
    private FragmentTransaction fragmentTransaction ;
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    ActivityManager activityManager ;
    private ConnectivityManager connectivityManager;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void informationuser(String message)
    {
        Snackbar.make(findViewById(R.id.activity_main),message,Snackbar.LENGTH_SHORT).show();;
    }


    public int checkconnectionstatous(View view)
    {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            informationuser("متصل بالانترنت");
            return 1;
        }else
        {
            informationuser(" غير متصل بالانترنت");
            return  0;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener ListnereNav = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = null;
            FragmentManager manag = getSupportFragmentManager();
            List<Fragment> frag = manag.getFragments();
            ActionMenuItemView  menu = findViewById(R.id.load);
            Log.e("fragment",""+frag.size());
            Log.e("Backstack",""+manag.getBackStackEntryCount());


            double availableMegs = mi.availMem / 0x100000L;
//Percentage can be calculated for API 16+
            double percentAvail = mi.availMem / (double)mi.totalMem * 100.0;
            boolean used = mi.lowMemory;

            Log.e("MeM",percentAvail + "  available "+availableMegs+ " lawmemory " + used );
            switch(menuItem.getItemId())
            {
                case R.id.homee:

                    if(checkactivation() || checkactivation2()) {
                        selected = new CatigoryFragment();
                        menu.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_f, selected).commit();
                        Toast.makeText(getApplicationContext(),"connect",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"not connect",Toast.LENGTH_SHORT).show();
                    }


                    break;
                case R.id.companies:
                    if(checkactivation()|| checkactivation2()) {
                        selected = new CompanyFragment();
                        menu.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_f, selected).commit();
                        Toast.makeText(getApplicationContext(),"connect",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"not connect",Toast.LENGTH_SHORT).show();
                    }


                    break;
                case R.id.cities:
                    if(checkactivation()|| checkactivation2()) {
                        selected = new CityFragment();
                        menu.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_f, selected).commit();
                        Toast.makeText(getApplicationContext(),"connect",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"not connect",Toast.LENGTH_SHORT).show();
                    }

                    break;


            }


            return true;
        }
    };
    void Loading()
    {
        LinearLayout linear = new LinearLayout(this);
        ImageView image = new ImageView((this));
        image.setImageResource(R.drawable.loakding);
        image.setAdjustViewBounds(true);
        image.setLayoutParams(new Gallery.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        linear.addView(image);
        setContentView(linear);
    }


    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Fragment x = new CatigoryFragment();
       // getSupportFragmentManager().beginTransaction().replace(R.id.container_f,x).commit();




    }
    BroadcastReceiver connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigationhobs);;
        bottomNavigationView.setOnNavigationItemSelectedListener(ListnereNav);
        Fragment selected = new CatigoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_f,selected).commit();


        IntentFilter i = new IntentFilter();
        i.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        i.addAction(Context.WIFI_SERVICE);
        connection = null;
        if(connection==null)
        {
            connection= new ConnectionR();
            registerReceiver(connection,i);
        }


        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /*   nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;

            }
        });*/

       /* recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoumanager = new GridLayoutManager(this,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoumanager);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu ==null)
            return false;
        getMenuInflater().inflate(R.menu.menu_op,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.load:
            {
                if(checkactivation() || checkactivation2()) {
                getCategoryFromWeb();
                GetJobsFromWeb();
                Toast.makeText(this,all_Categoray.size() + " " + all_jobs.size() ,Toast.LENGTH_LONG).show();
                CatigoryFragment fm = (CatigoryFragment) getSupportFragmentManager().findFragmentById(R.id.container_f);
                    fm.loadall(all_jobs,all_Categoray);
                    Toast.makeText(getApplicationContext(),"connect",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"not connect",Toast.LENGTH_SHORT).show();
                }

                break;
            }
            default:



        }
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    void GetJobsFromWeb() {
        Runnable job =new Jobs();
        Thread execute = new Thread(job);
        execute.start();
}
void getCategoryFromWeb()
{
    Runnable catigory =new catigories();
    Thread execute = new Thread(catigory);
    execute.start();
}

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.cities)
        {

            Intent i = new Intent(this,City_Avtivity.class);
            startActivity(i);
        }
        else if(menuItem.getItemId() == R.id.companies)
        {
            Intent i = new Intent(this,Company_Avtivity.class);
            startActivity(i);
        }
        return false;
    }*/

    @Override
    public void onItemClick(int position) {
        if(checkactivation() || checkactivation2()) {
            Intent intent = new Intent(this, JobsList.class);
            Bundle bundle = new Bundle();
            String link = all_Categoray.get(position).link;
            intent.putExtra("link", link);
            intent.putExtra("src", 1);
            startActivity(intent);
        }


    }



     void LoadRecycleViewData() {
/*
         adapter = new CatigoryAdapter(all_Categoray,getApplicationContext(),images);
         ((CatigoryAdapter) adapter).setOnClick(MainActivity.this);
         recyclerView.setAdapter(adapter);
*/

    }


    void get_catigories()
    {
        Vector<String> all = new Vector<String>();
        try {

            Document doc = Jsoup.connect("https://www.jobs.ps/categories").get();
            Elements catigories = doc.getElementsByClass("b2--content");
            Elements content = catigories.get(0).children().select("a");
            CatigoriesView catigory_n;
            int j=0;
            for(Element i :content)
            {
                catigory_n = new CatigoriesView("");
                catigory_n.setName(i.attr("title").toString());
                catigory_n.setLink(i.attr("href").toString());
                if(!all_Categoray.isEmpty() && all_Categoray.size()>j&&all_Categoray.get(j).name.equals(i.attr("title").toString()) );
                else
                all_Categoray.add(catigory_n);
                j++;
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    class catigories implements  Runnable
    {

        @Override
        public void run() {
            get_catigories();
        }
    }

     class Jobs implements Runnable {

        @Override
        public void run() {
            final StringBuilder builder = new StringBuilder();

            try {
                URL oracle ;
                HashSet<Job> job_list;
                Job currentjob;
                String single_url;
                for(int c=0;c<url.length;c++) {
                    oracle= new URL(url[c]);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(oracle.openStream()));

                    String inputLine;
                    String page = "";
                    while ((inputLine = in.readLine()) != null) {
                       // System.out.println(inputLine);
                        page+=inputLine+'\n';
                    }
                   // in.close();
                    //single_url = url[c];
                  //  Document doc = Jsoup.connect(single_url).get();
                    Jsoup.parse(page);
                    Document half=Jsoup.parseBodyFragment(page);
                    Element el =half.body();
                   // String title = doc.title();
                  //  Element body = doc.body();
                    Elements table =el.getElementsByClass("list-3--body");
                    Elements content = table.select("a");
                    // Elements link = doc.select("tr");
                    job_list = new HashSet<>();
                    for (Element i : content) {
                        currentjob = new Job();
                        currentjob.link = i.attr("href");
                        currentjob.jobtitle = i.attr("title");
                        Elements info = i.children();
                        currentjob.Company = info.get(0).child(0).text();
                        currentjob.location = info.get(1).child(0).text();
                        currentjob.date = info.get(2).text();
                        job_list.add(currentjob);



                    }
                    if(  !all_jobs.isEmpty()&&all_jobs.containsKey(all_Categoray.get(c).name)) {
                        all_jobs.get(all_Categoray.get(c).name).clear();
                        all_jobs.get(all_Categoray.get(c).name).addAll(job_list);

                    }
                    else if(all_jobs!=null && all_Categoray!=null && !all_Categoray.isEmpty())
                        all_jobs.put(all_Categoray.get(c).name, job_list);
                    else
                    {

                    }
                    Jsoup.clean(page, Whitelist.relaxed());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    boolean checkactivation()
    {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
