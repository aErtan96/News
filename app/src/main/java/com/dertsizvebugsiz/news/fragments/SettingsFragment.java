package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dertsizvebugsiz.news.R;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    public int testInt;

    public static SettingsFragment newInstance(int testInt){
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.testInt = testInt;
        return settingsFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        TextView testTextView = view.findViewById(R.id.fragment_test_textview);
        testTextView.setText("This is Page " + testInt);
        return view;
    }
}
