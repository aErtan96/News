package com.dertsizvebugsiz.news.adapters;

import com.dertsizvebugsiz.news.fragments.CurrenciesFragment;
import com.dertsizvebugsiz.news.fragments.RecentNewsFragment;
import com.dertsizvebugsiz.news.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private final List<Fragment> mList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new CurrenciesFragment();
            case 1:
                return RecentNewsFragment.newInstance();
            case 2:
                return SettingsFragment.newInstance(i);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
