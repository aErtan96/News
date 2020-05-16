package com.dertsizvebugsiz.news.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.News;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {

    TextView articleHeader, articleBody, articleDate;
    ImageView articleBack, articleOpenInNew, articleBookmark;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        getViews(v);
        registerEventListeners();

        return v;
    }

    private void getViews(View root){
        articleHeader = root.findViewById(R.id.article_header);
        articleBody = root.findViewById(R.id.article_body);
        articleDate = root.findViewById(R.id.article_date);
        articleBack = root.findViewById(R.id.article_back);
        articleOpenInNew = root.findViewById(R.id.article_open_in_new);
        articleBookmark = root.findViewById(R.id.article_bookmark);
    }

    private void registerEventListeners(){
        articleBack.setOnClickListener(v -> {
            backClicked();
        });
    }


    public void makeLoading(){
        articleHeader.setText("");
        articleDate.setText("");
        articleBody.setText("");
        articleBookmark.setOnClickListener(null);
        articleOpenInNew.setOnClickListener(null);
    }

    public void setNewsData(News news){
        articleHeader.setText(news.title);
        if(news.subTitle != null && news.subTitle.length() != 0){
            articleBody.setText(news.subTitle + "\n\n" + news.body);
        }
        else{
            articleBody.setText(news.body);
        }
        articleDate.setText(news.getPublishDatePart());
        articleOpenInNew.setOnClickListener(v -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(news.link));
            startActivity(viewIntent);
        });
    }

    private void backClicked(){
        if(getActivity() != null){
            ((MainActivity) getActivity()).closeArticleFragment();
        }
    }


}
