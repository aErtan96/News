package com.dertsizvebugsiz.news.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dertsizvebugsiz.news.AppConstants;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.SqliteConnector;
import com.dertsizvebugsiz.news.adapters.ViewPagerAdapter;
import com.dertsizvebugsiz.news.dataclasses.Currency;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.enums.Vote;
import com.dertsizvebugsiz.news.fragments.ArticleFragment;
import com.dertsizvebugsiz.news.fragments.CurrenciesFragment;
import com.dertsizvebugsiz.news.fragments.RecentNewsFragment;
import com.dertsizvebugsiz.news.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.dertsizvebugsiz.news.AppConstants.COINS_API_URL;
import static com.dertsizvebugsiz.news.AppConstants.CURRENCY_REQUEST_TAG;
import static com.dertsizvebugsiz.news.AppConstants.NEWS_FEED_REQUEST_TAG;
import static com.dertsizvebugsiz.news.AppConstants.SINGLE_NEW_REQUEST_TAG;

public class MainActivity extends AppCompatActivity {

    private static LinkedHashMap<Integer, News> bookmarks;

    NavigationTabBar navigationTabBar;
    ViewPager viewPager;
    ViewPagerAdapter fragmentAdapter;

    Toolbar toolbar;
    RequestQueue queue;

    private int newsFeedLimit = 15;
    private int newsFeedLastOffset = -15;

    private ConstraintLayout fragmentArticleContainer;
    private ConstraintLayout articleLoadingIndicator;

    private MenuItem collectionsButton, allNewsButton;
    private String selectedToolbarButton = "all_news";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentArticleContainer = findViewById(R.id.article_fragment_container);
        articleLoadingIndicator = findViewById(R.id.article_loading);

        createBottomTabBarAndViwPager();
        setUpToolbar();

        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onBackPressed() {
        if(fragmentArticleContainer.getVisibility() == View.VISIBLE){
            closeArticleFragment();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        collectionsButton = menu.findItem(R.id.toolbar_collections_button);
        allNewsButton = menu.findItem(R.id.toolbar_all_news_button);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_collections_button:
                if(selectedToolbarButton.equals("collections")){
                    return true;
                }
                allNewsButton.setIcon(R.drawable.ic_view_headline_grey_24dp);
                collectionsButton.setIcon(R.drawable.ic_collections_bookmark_white_24dp);
                ((RecentNewsFragment)fragmentAdapter.getRegisteredFragment(1)).openCollectionsRecycler();
                selectedToolbarButton = "collections";
                break;
            case R.id.toolbar_all_news_button:
                if(selectedToolbarButton.equals("all_news")){
                    return true;
                }
                allNewsButton.setIcon(R.drawable.ic_view_headline_white_24dp);
                collectionsButton.setIcon(R.drawable.ic_collections_bookmark_grey_24dp);
                ((RecentNewsFragment)fragmentAdapter.getRegisteredFragment(1)).openRecentNewsRecycler();
                selectedToolbarButton = "all_news";
                break;
        }
        return true;
    }

    public void setUpToolbar(){
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("News Feed");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    public void createBottomTabBarAndViwPager(){

        fragmentAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.fragmentsViewPager);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        changeTitle("Currencies");
                        break;
                    case 1:
                        changeTitle("News Feed");
                        break;
                    case 2:
                        changeTitle("Settings");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationTabBar = findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_monetization_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Currencies")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_view_headline_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("News")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_settings_applications_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Settings")
                        .badgeTitle("icon")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });

        navigationTabBar.setViewPager(viewPager);

    }

    public void changeTitle(String title){
        toolbar.setTitle(title);
    }



    public void openArticleFragment(int articleId){
        Log.d("DEBUG", "Open Article Id: " + articleId);
        fragmentArticleContainer.setVisibility(View.VISIBLE);
        articleLoadingIndicator.setVisibility(View.VISIBLE);
        ArticleFragment articleFragment = ((ArticleFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.ARTICLE_FRAGMENT_TAG));

        articleFragment.makeLoading();

        JsonArrayRequest singleNewsRequest = new JsonArrayRequest(AppConstants.getSingleNewUrl(articleId),
            response -> {
                News singleNews = JSONParser.parseSingleNewsResponse(response);
                if(singleNews != null){
                    articleLoadingIndicator.setVisibility(View.INVISIBLE);
                    articleFragment.setNewsData(singleNews);
                }
                else{
                    //TODO: O id'ye ait bir kayıt bulunamadı!
                }
            },
            error -> {
                Log.e("ERROR","Volley error occur\n" + error.getMessage());
            });

        singleNewsRequest.setTag(SINGLE_NEW_REQUEST_TAG);
        queue.cancelAll(SINGLE_NEW_REQUEST_TAG);
        queue.add(singleNewsRequest);
    }

    public void closeArticleFragment(){
        queue.cancelAll(SINGLE_NEW_REQUEST_TAG);
        fragmentArticleContainer.setVisibility(View.GONE);
    }




    public LinkedHashMap<Integer, News> getBookmarks(){
        if(bookmarks == null){
            bookmarks = SqliteConnector.getInstance(this).getAllBookmarks();
        }
        return bookmarks;
    }

    public void addBookmark(News news){
        SqliteConnector.getInstance(this).addBookmark(news);
        bookmarks.put(news.newsId, news);
    }

    public void deleteBookmark(int newsId) {
        SqliteConnector.getInstance(this).deleteBookmark(newsId);
        bookmarks.remove(newsId);
    }



    public void loadMoreIntoRecentNews(){
        Log.d("DEBUG","loadMoreIntoRecentNews");
        final RecentNewsFragment recentNewsFragment = ((RecentNewsFragment)fragmentAdapter.getRegisteredFragment(1));
        newsFeedLastOffset += newsFeedLimit;
        Log.d("DEBUG", AppConstants.getNewsFeedUrl(newsFeedLimit, newsFeedLastOffset));
        JsonArrayRequest newsFeedRequest = new JsonArrayRequest(AppConstants.getNewsFeedUrl(newsFeedLimit, newsFeedLastOffset),
            new Response.Listener<JSONArray>() {
                public void onResponse(JSONArray response) {
                    News[] newsFeed = JSONParser.parseNewsFeedResponse(response);
                    if(recentNewsFragment.recentNewsAdapter.news.size() == 0){
                        recentNewsFragment.recentNewsAdapter.news.addAll(Arrays.asList(newsFeed));
                        recentNewsFragment.recentNewsAdapter.notifyDataSetChanged();
                    }
                    else{
                        recentNewsFragment.recentNewsAdapter.news.addAll(Arrays.asList(newsFeed));
                        recentNewsFragment.recentNewsAdapter.notifyItemRangeInserted(recentNewsFragment.recentNewsAdapter.news.size() - newsFeed.length, newsFeed.length);
                    }
                }
            },
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR","Volley error occur\n" + error.getMessage());
                }
            });

        newsFeedRequest.setTag(NEWS_FEED_REQUEST_TAG);
        queue.cancelAll(NEWS_FEED_REQUEST_TAG);
        queue.add(newsFeedRequest);
    }

    public void loadCurrencyData(){
        JsonObjectRequest currencyRequest = new JsonObjectRequest(COINS_API_URL, null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Currency[] currencies = JSONParser.parseCurrencyApiResponse(response);
                ((CurrenciesFragment) fragmentAdapter.getRegisteredFragment(0)).bindData(currencies);
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        currencyRequest.setTag(CURRENCY_REQUEST_TAG);
        queue.cancelAll(CURRENCY_REQUEST_TAG);
        queue.add(currencyRequest);
    }

    public void newsFeedbackSent(int newsId, Vote vote){
        SqliteConnector.getInstance(this).setVoteOfNews(newsId, vote);
        StringRequest summaryFeedbackRequest = new StringRequest(Request.Method.POST, AppConstants.SUMMARY_FEEDBACK_URL, null, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("device_id", getDeviceId());
                params.put("news_id", String.valueOf(newsId));
                params.put("vote", String.valueOf(Vote.parseInt(vote)));
                return params;
            }
        };
        summaryFeedbackRequest.setTag(AppConstants.SUMMARY_FEEDBACK_TAG);
        queue.add(summaryFeedbackRequest);
    }



    private String getDeviceId(){
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }


}
