package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.SqliteConnector;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.adapters.SitesAdapter;
import com.dertsizvebugsiz.news.dataclasses.Site;

import java.util.LinkedHashMap;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragment extends Fragment {

    RecyclerView sitesRecycler;
    SitesAdapter sitesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        getViews(root);
        ((MainActivity)getActivity()).getServerSites();

        return root;
    }

    private void getViews(View root){
        sitesRecycler = root.findViewById(R.id.settings_sites_recycler);
    }

    public void initSitesRecycler(){
        LinkedHashMap<Integer, Site> sites = SqliteConnector.getInstance(getContext()).getAllSitesList();
        sitesAdapter = new SitesAdapter(sites, ((MainActivity) getActivity()));
        sitesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        sitesRecycler.setAdapter(sitesAdapter);
    }



}
