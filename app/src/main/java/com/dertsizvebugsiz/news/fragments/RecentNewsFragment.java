package com.dertsizvebugsiz.news.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import com.dertsizvebugsiz.news.EndlessRecyclerViewScrollListener;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.adapters.CollectionsAdapter;
import com.dertsizvebugsiz.news.adapters.RecentNewsAdapter;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.utils.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RecentNewsFragment extends Fragment {

    private ConstraintLayout recentNewsContainer;
    private LinearLayout collectionsContainer;

    public RecyclerView recentNewsRecyclerView;
    public RecentNewsAdapter recentNewsAdapter;

    private RecyclerView collectionsRecyclerView;
    private CollectionsAdapter collectionsAdapter;

    public SwipeRefreshLayout recentNewsSwipe;

    private Button loadUnreadNewsButton;
    private float loadUnreadNewsButtonBottomTranslationY, loadUnreadNewsButtonUpTranslationY;

    public static RecentNewsFragment newInstance(){
        return new RecentNewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_news, container, false);

        getViews(rootView);
        initRecentNewsRecycler();
        initCollectionsRecycler();
        initUnreadNewsButtonTranslations();
        registerEventListeners();

        return rootView;
    }



    private void getViews(View root){
        recentNewsRecyclerView = root.findViewById(R.id.recent_news_recyclerview);
        collectionsRecyclerView = root.findViewById(R.id.collections_recyclerview);

        recentNewsContainer = root.findViewById(R.id.recent_news_recyclerview_container);
        collectionsContainer = root.findViewById(R.id.collections_recyclerview_container);

        recentNewsSwipe = root.findViewById(R.id.recent_news_swipe_refresh);
        loadUnreadNewsButton = root.findViewById(R.id.recent_news_load_unread_news_button);
    }

    private void initRecentNewsRecycler(){
        News[] news = new News[]{};
        ArrayList arrayList = new ArrayList<>(Arrays.asList(news));
        recentNewsAdapter = new RecentNewsAdapter(arrayList, ((MainActivity)getActivity()), getActivity().getLayoutInflater());
        recentNewsRecyclerView.setAdapter(recentNewsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recentNewsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initCollectionsRecycler(){
        collectionsAdapter = new CollectionsAdapter(((MainActivity) getActivity()), getActivity().getLayoutInflater());
        collectionsRecyclerView.setAdapter(collectionsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        collectionsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initUnreadNewsButtonTranslations(){
        loadUnreadNewsButtonBottomTranslationY = loadUnreadNewsButton.getTranslationY();
        loadUnreadNewsButtonUpTranslationY = loadUnreadNewsButton.getTranslationY() + Converter.convertDpToPixel(-50, getActivity());
    }

    private void registerEventListeners(){
        recentNewsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) recentNewsRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                ((MainActivity)getActivity()).loadMoreIntoRecentNews();
            }
        });
        recentNewsSwipe.setOnRefreshListener(() -> ((MainActivity)getActivity()).loadLastUploadedNews());
        loadUnreadNewsButton.setOnClickListener(v -> {
            if(!recentNewsSwipe.isRefreshing()){
                recentNewsRecyclerView.smoothScrollToPosition(0);
                recentNewsSwipe.setRefreshing(true);
                ((MainActivity)getActivity()).loadLastUploadedNews();
            }
        });
    }




    public void openRecentNewsRecycler(){
        recentNewsContainer.setVisibility(View.VISIBLE);
        collectionsContainer.setVisibility(View.INVISIBLE);
        recentNewsAdapter.notifyDataSetChanged();
    }

    public void openCollectionsRecycler(){
        recentNewsContainer.setVisibility(View.INVISIBLE);
        collectionsContainer.setVisibility(View.VISIBLE);
        collectionsAdapter.notifyDataSetChanged();
    }

    public void refreshCompleted(){
        recentNewsSwipe.setRefreshing(false);
    }



    public void playLoadUnreadNewsButtonAnim(boolean isReverse){
        loadUnreadNewsButton
                .animate()
                .translationY(isReverse ? loadUnreadNewsButtonBottomTranslationY : loadUnreadNewsButtonUpTranslationY)
                .setDuration(1000)
                .start();
    }

    public void setLoadUnreadNewsButtonCount(int count){
        loadUnreadNewsButton.setText(count + " Unread News");
    }


}
