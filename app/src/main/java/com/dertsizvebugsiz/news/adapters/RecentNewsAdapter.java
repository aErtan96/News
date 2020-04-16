package com.dertsizvebugsiz.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.data.News;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecentNewsAdapter.RecentNewsViewHolder> {

    private ArrayList<News> news;
    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;

    public RecentNewsAdapter(ArrayList<News> news, MainActivity mainActivity, LayoutInflater layoutInflater) {
        this.news = news;
        this.mainActivity = mainActivity;
        this.layoutInflater = layoutInflater;
    }

    public RecentNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.recent_news_recycler_item, parent,false);
        return new RecentNewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecentNewsViewHolder holder, int position) {
        holder.setData(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    static class RecentNewsViewHolder extends RecyclerView.ViewHolder{


        ImageView icon, bookmark;
        TextView header;

        RecentNewsViewHolder(View itemView) {
            super(itemView);
            getViews(itemView);
        }

        void getViews(View root){
            icon = root.findViewById(R.id.recent_news_item_icon);
            bookmark = root.findViewById(R.id.recent_news_item_bookmark);
            header = root.findViewById(R.id.recent_news_item_header);
        }

        void setData(News news){
            header.setText(news.title);
        }


    }

}
