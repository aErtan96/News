package com.dertsizvebugsiz.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.News;
import java.util.ArrayList;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<News> news;
    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;

    public RecentNewsAdapter(ArrayList<News> news, MainActivity mainActivity, LayoutInflater layoutInflater) {
        this.news = news;
        this.mainActivity = mainActivity;
        this.layoutInflater = layoutInflater;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View v0 = layoutInflater.inflate(R.layout.recent_news_recycler_item, parent,false);
                return new RecentNewsViewHolder(v0);
            case 1:
                View v1 = layoutInflater.inflate(R.layout.recent_news_recycler_item_loading, parent,false);
                return new LoadingViewHolder(v1);
        }

        return null;
    }

    public int getItemViewType(int position) {
        if(position < news.size()){
            return 0;
        }
        return 1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position < news.size()){
            ((RecentNewsViewHolder)holder).setData(news.get(position));
        }
    }



    @Override
    public int getItemCount() {
        return news.size() + 1;
    }

    class RecentNewsViewHolder extends RecyclerView.ViewHolder{

        ImageView icon, bookmark;
        TextView header, datetime;
        CardView container;

        RecentNewsViewHolder(View itemView) {
            super(itemView);
            getViews(itemView);
        }

        void getViews(View root){
            icon = root.findViewById(R.id.recent_news_item_icon);
            bookmark = root.findViewById(R.id.recent_news_item_bookmark);
            header = root.findViewById(R.id.recent_news_item_header);
            container = root.findViewById(R.id.recent_news_recycler_item_container);
            datetime = root.findViewById(R.id.recent_news_item_datetime);
        }

        void setData(News news){
            header.setText(news.title);
            datetime.setText(news.getPublishDatePart());
            int rand = new Random().nextInt(4);
            int resourceId = R.drawable.logo_coindesk_256px;
            switch (rand){
                case 0:
                    resourceId = R.drawable.logo_crytpodaily_256px;
                    break;
                case 1:
                    resourceId = R.drawable.logo_newsbtc_256px;
                    break;
                case 2:
                    resourceId = R.drawable.logo_newsbitcoin_256px;
                    break;
            }
            Glide.with(icon).load(resourceId).into(icon);
            container.setOnClickListener(v -> {
                mainActivity.openArticleFragment(news.newsId);
            });
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }

    }


}
