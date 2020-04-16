package com.dertsizvebugsiz.news.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;

import android.os.Bundle;

import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.adapters.ViewPagerAdapter;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.fragments.RecentNewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    NavigationTabBar navigationTabBar;
    ViewPager viewPager;
    ViewPagerAdapter fragmentAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createBottomTabBarAndViwPager();
        setUpToolbar();


    }

    public void setUpToolbar(){
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Recent News");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    public void createBottomTabBarAndViwPager(){

        fragmentAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.fragmentsViewPager);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

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



    public void loadMoreIntoRecentNews(){
        RecentNewsFragment recentNewsFragment = ((RecentNewsFragment)fragmentAdapter.getRegisteredFragment(0));
        News[] news = new News[]{
                new News("News 1","",new Date(),1),
                new News("News 2","",new Date(),1),
                new News("News 3","",new Date(),1),
                new News("News 4","",new Date(),1),
                new News("News 5","",new Date(),1),
                new News("News 5","",new Date(),1),
        };
        recentNewsFragment.recentNewsAdapter.news.addAll(
                Arrays.asList(news)
        );
        recentNewsFragment.recentNewsAdapter.notifyItemRangeInserted(recentNewsFragment.recentNewsAdapter.news.size() - news.length, news.length);
    }


}
