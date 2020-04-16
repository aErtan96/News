package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.adapters.CurrenciesAdapter;
import com.dertsizvebugsiz.news.dataclasses.Currency;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrenciesFragment extends Fragment {

    RecyclerView recyclerView;
    CurrenciesAdapter currenciesAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_currencies, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        recyclerView = root.findViewById(R.id.currencies_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        currenciesAdapter  = new CurrenciesAdapter(new Currency[0], mainActivity);
        recyclerView.setAdapter(currenciesAdapter);

        mainActivity.loadCurrencyData();

        return root;
    }

    public void bindData(Currency[] currencies){
        currenciesAdapter.currencies = currencies;
        currenciesAdapter.notifyDataSetChanged();
    }

}
