package com.example.webscrappingjobs;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatigoryFragment extends Fragment implements OnItemClicked{

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

    public CatigoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_catigory, container, false);
        recyclerView = view.findViewById(R.id.recyclerview1);
        GridLayoutManager layoumanager = new GridLayoutManager(getContext(),1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoumanager);
        return view;
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), JobsList.class);
        intent.putExtra("src", 1);
        startActivity(intent);


    }
    public  void loadall(HashMap<String, HashSet<Job>> all_jobs,List<CatigoriesView> all_Categoray )
    {
        this.all_jobs=all_jobs;
        this.all_Categoray=all_Categoray;

        LoadRecycleViewData();



    }




    public void LoadRecycleViewData() {

         adapter = new CatigoryAdapter(all_Categoray,getContext(),images);
         ((CatigoryAdapter) adapter).setOnClick((OnItemClicked) getContext());
         recyclerView.setAdapter(adapter);


    }



}
