package com.dertsizvebugsiz.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    NavigationTabBar navigationTabBar;
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DEBUG","Deneme Değiş");

        createBottomTabBarAndViwPager();

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
                ).title("Page 4")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Page 5")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Page 6")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_white_24dp),
                        getResources().getColor(R.color.colorPrimaryDark)
                ).title("Page 7")
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
