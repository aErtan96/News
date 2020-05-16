package com.dertsizvebugsiz.news.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dertsizvebugsiz.news.AppConstants;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.adapters.ViewPagerAdapter;
import com.dertsizvebugsiz.news.dataclasses.Currency;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.fragments.ArticleFragment;
import com.dertsizvebugsiz.news.fragments.CurrenciesFragment;
import com.dertsizvebugsiz.news.fragments.RecentNewsFragment;
import com.dertsizvebugsiz.news.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import static com.dertsizvebugsiz.news.AppConstants.COINS_API_URL;
import static com.dertsizvebugsiz.news.AppConstants.CURRENCY_REQUEST_TAG;
import static com.dertsizvebugsiz.news.AppConstants.NEWS_FEED_REQUEST_TAG;
import static com.dertsizvebugsiz.news.AppConstants.SINGLE_NEW_REQUEST_TAG;

public class MainActivity extends AppCompatActivity {


    NavigationTabBar navigationTabBar;
    ViewPager viewPager;
    ViewPagerAdapter fragmentAdapter;

    Toolbar toolbar;
    RequestQueue queue;

    private int newsFeedLimit = 15;
    private int newsFeedLastOffset = -newsFeedLimit;

    private LinearLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.article_fragment_container);

        createBottomTabBarAndViwPager();
        setUpToolbar();

        queue = Volley.newRequestQueue(this);
        newsFeedLastOffset = 0;

    }

    @Override
    public void onBackPressed() {
        if(fragmentContainer.getVisibility() == View.VISIBLE){
            closeArticleFragment();
        }
        else{
            super.onBackPressed();
        }
    }

    public void setUpToolbar(){
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Currencies");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    public void createBottomTabBarAndViwPager(){

        fragmentAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.fragmentsViewPager);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        changeTitle("News");
                        break;
                    case 1:
                        changeTitle("Currencies");
                        break;
                    case 2:
                        changeTitle("Archive");
                        break;
                    case 3:
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
                        getResources().getDrawable(R.drawable.ic_view_headline_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("News")
                        .badgeTitle("NTB")
                        .build()
        );
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
                        getResources().getDrawable(R.drawable.ic_collections_bookmark_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Archive")
                        .badgeTitle("state")
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



    public void openArticleFragment(int articleId){
        Log.d("DEBUG", "Open Article Id: " + articleId);
        fragmentContainer.setVisibility(View.VISIBLE);
        ArticleFragment articleFragment = ((ArticleFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.ARTICLE_FRAGMENT_TAG));

        articleFragment.makeLoading();

        JsonArrayRequest singleNewsRequest = new JsonArrayRequest(AppConstants.getSingleNewUrl(articleId),
            response -> {
                News singlewNews = JSONParser.parseSingleNewsResponse(response);
                if(singlewNews != null){
                    articleFragment.setNewsData(singlewNews);
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
        fragmentContainer.setVisibility(View.GONE);
    }



    public void loadMoreIntoRecentNews(){
        final RecentNewsFragment recentNewsFragment = ((RecentNewsFragment)fragmentAdapter.getRegisteredFragment(0));
        newsFeedLastOffset += newsFeedLimit;
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
                ((CurrenciesFragment) fragmentAdapter.getRegisteredFragment(1)).bindData(currencies);
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


    public void changeTitle(String title){
        toolbar.setTitle(title);
    }

}
