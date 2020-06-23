package com.dertsizvebugsiz.news.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dertsizvebugsiz.news.AppConstants;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.SqliteConnector;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.Site;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.SitesViewHolder> {

    MainActivity mainActivity;
    private LayoutInflater layoutInflater;
    private LinkedHashMap<Integer, Site> sites;
    private List<Integer> siteKeys;

    public SitesAdapter(LinkedHashMap<Integer, Site> sites, MainActivity mainActivity){
        this.mainActivity = mainActivity;
        layoutInflater = mainActivity.getLayoutInflater();
        this.sites = sites;
        siteKeys = new ArrayList<>();
        for(LinkedHashMap.Entry<Integer, Site> entry : sites.entrySet()){
            siteKeys.add(entry.getKey());
        }
        Collections.sort(siteKeys);
        /*
        List<LinkedHashMap.Entry<Integer, Site>> entries = new ArrayList<LinkedHashMap.Entry<Integer, Site>>(sites.entrySet());
        Collections.sort(entries, new Comparator<LinkedHashMap.Entry<Integer, Site>>() {
            public int compare(LinkedHashMap.Entry<Integer, Site> a, LinkedHashMap.Entry<Integer, Site> b){
                return Integer.compare(a.getValue().siteId, b.getValue().siteId);
            }
        });
        LinkedHashMap<Integer, Site> sortedMap = new LinkedHashMap<>();
        for (LinkedHashMap.Entry<Integer, Site> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        */
    }

    public SitesAdapter.SitesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.sites_recycler_item, parent,false);
        return new SitesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SitesViewHolder holder, int position) {
        holder.setData(sites.get(siteKeys.get(position)));
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    class SitesViewHolder extends RecyclerView.ViewHolder{

        Site site;

        ImageView siteImage;
        TextView siteName;
        SwitchCompat switchCompat;

        SitesViewHolder(View itemView) {
            super(itemView);
            siteImage = itemView.findViewById(R.id.sites_item_icon);
            siteName = itemView.findViewById(R.id.sites_item_name);
            switchCompat = itemView.findViewById(R.id.sites_item_switch);
        }

        void setData(Site site){
            this.site = site;
            Glide.with(siteImage).load(AppConstants.getSiteImageUrl(site.siteId)).into(siteImage);
            siteName.setText(site.name);
            if(switchCompat.isChecked() != site.isEnabled)
                switchCompat.setChecked(site.isEnabled);
            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(mainActivity != null){
                    mainActivity.disabledSitesHaveChanged = true;
                    this.site.isEnabled = buttonView.isChecked();
                    if(buttonView.isChecked()){
                        SqliteConnector.getInstance(mainActivity).setSiteEnabled(this.site.siteId);
                    }
                    else{
                        SqliteConnector.getInstance(mainActivity).setSiteDisabled(this.site.siteId);
                    }
                }
            });
        }

    }

}
