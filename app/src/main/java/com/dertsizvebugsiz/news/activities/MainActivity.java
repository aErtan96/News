package com.dertsizvebugsiz.news.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;

import android.os.Bundle;

import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    NavigationTabBar navigationTabBar;
    ViewPager viewPager;
    ViewPagerAdapter adapter;

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
        toolbar.setTitle("News Feed");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    public void createBottomTabBarAndViwPager(){

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.fragmentsViewPager);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);

        navigationTabBar = findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("News Feed")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Currencies")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Archive")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
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

}
