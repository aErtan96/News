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
import java.util.Map;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder> {

    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;

    public CollectionsAdapter( MainActivity mainActivity, LayoutInflater layoutInflater) {
        this.mainActivity = mainActivity;
        this.layoutInflater = layoutInflater;
    }

    public CollectionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.recent_news_recycler_item, parent,false);
        return new CollectionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CollectionsViewHolder holder, int position) {
        holder.setData(((News)((Map.Entry)mainActivity.getBookmarks().entrySet().toArray()[position]).getValue()));
    }

    @Override
    public int getItemCount() {
        return mainActivity.getBookmarks().size();
    }


    class CollectionsViewHolder extends RecyclerView.ViewHolder{

        ImageView icon, bookmark;
        TextView header, datetime;
        CardView container;

        public CollectionsViewHolder(View itemView) {
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

        public void setData(News news){
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
