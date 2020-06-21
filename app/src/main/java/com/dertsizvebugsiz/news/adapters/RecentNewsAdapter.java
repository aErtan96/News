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
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.News;
import java.util.ArrayList;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<News> news;
    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;

    public int separatorIndex = -1;

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
            case 2:
                View v2 = layoutInflater.inflate(R.layout.recent_news_recycler_item_separator, parent,false);
                return new LoadingViewHolder(v2);
        }

        return null;
    }

    public int getItemViewType(int position) {
        if(position == separatorIndex){
            return 2;
        }
        if(position < news.size()){
            return 0;
        }
        return 1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position != separatorIndex && position < news.size()){
            ((RecentNewsViewHolder)holder).setData(news.get(position));
        }
    }



    @Override
    public int getItemCount() {
        if(separatorIndex == -1)
            return news.size() + 1;
        else
            return news.size() + 2;
    }

    class RecentNewsViewHolder extends RecyclerView.ViewHolder{

        ImageView icon, bookmark;
        TextView siteName, header, datetime;
        CardView container;

        RecentNewsViewHolder(View itemView) {
            super(itemView);
            getViews(itemView);
        }

        void getViews(View root){
            icon = root.findViewById(R.id.recent_news_item_icon);
            bookmark = root.findViewById(R.id.recent_news_item_bookmark);
            siteName = root.findViewById(R.id.recent_news_item_site_name);
            header = root.findViewById(R.id.recent_news_item_header);
            container = root.findViewById(R.id.recent_news_recycler_item_container);
            datetime = root.findViewById(R.id.recent_news_item_datetime);
        }

        void setData(News news){
            siteName.setText(news.siteName);
            header.setText(news.title);
            datetime.setText(news.getPublishDatePart());

            Glide.with(icon)
                    .load(AppConstants.getSiteImageUrl(news.siteId))
                    .into(icon);


            container.setOnClickListener(v -> {
                mainActivity.openArticleFragment(news.newsId);
            });
            if(mainActivity != null){
                if(mainActivity.getBookmarks().containsKey(news.newsId)){
                    bookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
                    bookmark.setOnClickListener(new BookmarkClickListener(true, news));
                }
                else{
                    bookmark.setImageResource(R.drawable.ic_bookmark_border_empty_24dp);
                    bookmark.setOnClickListener(new BookmarkClickListener(false, news));
                }
            }
            else{
                Log.e("DEBUG","Main Activity is Null!!!");
            }
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder{
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class SeparatorViewHolder extends RecyclerView.ViewHolder{
        SeparatorViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BookmarkClickListener implements View.OnClickListener{

        boolean isBookmarked;
        News news;

        BookmarkClickListener(boolean isBookmarked, News news){
            this.isBookmarked = isBookmarked;
            this.news = news;
        }

        @Override
        public void onClick(View v) {
            if(isBookmarked){
                mainActivity.deleteBookmark(news.newsId);
                ((ImageView)v).setImageResource(R.drawable.ic_bookmark_border_empty_24dp);
                v.setOnClickListener(new BookmarkClickListener(false, news));
            }
            else{
                mainActivity.addBookmark(news);
                ((ImageView)v).setImageResource(R.drawable.ic_bookmark_white_24dp);
                v.setOnClickListener(new BookmarkClickListener(true, news));
            }
        }
    }



}
