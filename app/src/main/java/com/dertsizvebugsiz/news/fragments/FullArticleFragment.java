package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dertsizvebugsiz.news.AppConstants;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.News;

import androidx.fragment.app.Fragment;

public class FullArticleFragment extends Fragment {

    public News news;

    private WebView webView;
    private ImageButton backButton;
    private TextView newsSourceName;
    private ImageView siteIcon;

    public static FullArticleFragment newInstance(News news){
        FullArticleFragment fullArticleFragment = new FullArticleFragment();
        fullArticleFragment.news = news;
        return fullArticleFragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_full_article, container, false);

        getViews(view);
        configureWebView();
        registerEventListeners();

        return view;

    }

    void getViews(View root){
        webView = root.findViewById(R.id.full_article_web_view);
        backButton = root.findViewById(R.id.full_article_back_button);
        newsSourceName = root.findViewById(R.id.full_article_source_name);
        siteIcon = root.findViewById(R.id.full_article_site_icon);
    }

    void configureWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.requestFocusFromTouch();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }

    void registerEventListeners(){
        backButton.setOnClickListener(v -> ((MainActivity)getActivity()).closeFullArticleFragment());
    }


    public void setNewsAndOpen(News news){
        this.news = news;
        webView.loadUrl(news.link);
        newsSourceName.setText(news.siteName);
        Glide.with(siteIcon).load(AppConstants.getSiteImageUrl(news.siteId)).into(siteIcon);
    }

    public void resetWebView() {
        webView.loadUrl("about:blank");
        webView.clearHistory();
    }

    public boolean canGoBack(){
        if (webView.copyBackForwardList().getCurrentIndex() > 0) {
            webView.goBack();
            return true;
        }
        return false;
    }

}
