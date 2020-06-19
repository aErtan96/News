package com.dertsizvebugsiz.news.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.SqliteConnector;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.enums.Vote;

import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {

    TextView articleHeader, articleBody, articleDate, articleReadFull;
    ImageView articleBack, articleOpenInNew, articleBookmark;
    ImageButton articleFeedbackUpVote, articleFeedbackDownVote;

    News news;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        getViews(v);
        registerEventListeners();

        return v;
    }

    private void getViews(View root){
        articleHeader = root.findViewById(R.id.article_header);
        articleBody = root.findViewById(R.id.article_body);
        articleDate = root.findViewById(R.id.article_date);
        articleBack = root.findViewById(R.id.article_back);
        articleOpenInNew = root.findViewById(R.id.article_open_in_new);
        articleBookmark = root.findViewById(R.id.article_bookmark);
        articleReadFull = root.findViewById(R.id.read_full_article);
        articleFeedbackUpVote = root.findViewById(R.id.article_up_vote);
        articleFeedbackDownVote = root.findViewById(R.id.article_down_vote);
    }

    private void registerEventListeners(){
        articleBack.setOnClickListener(v -> {
            backClicked();
        });
    }


    public void makeLoading(){
        articleHeader.setText("");
        articleDate.setText("");
        articleBody.setText("");
        articleBookmark.setOnClickListener(null);
        articleOpenInNew.setOnClickListener(null);
        articleReadFull.setOnClickListener(null);
        articleFeedbackUpVote.setOnClickListener(null);
        articleFeedbackDownVote.setOnClickListener(null);
    }

    public void setNewsData(News news){
        this.news = news;
        articleHeader.setText(news.title);
        if(news.subTitle != null && news.subTitle.length() != 0){
            articleBody.setText(news.subTitle + "\n\n" + news.summary);
        }
        else{
            articleBody.setText(news.summary);
        }
        articleDate.setText(news.getPublishDatePart());
        articleOpenInNew.setOnClickListener(v -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(news.link));
            startActivity(viewIntent);
        });
        articleReadFull.setOnClickListener(v -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(news.link));
            startActivity(viewIntent);
        });
        changeFeedbackStatus(SqliteConnector.getInstance(getActivity()).getVoteOfNews(news.newsId));
    }

    private void changeFeedbackStatus(Vote vote){
        switch (vote){
            case NONE:
                articleFeedbackUpVote.setImageResource(R.drawable.ic_like_empty_64px);
                articleFeedbackDownVote.setImageResource(R.drawable.ic_dislike_empty_64px);
                articleFeedbackUpVote.setOnClickListener(new VoteClickListener(Vote.UPVOTE));
                articleFeedbackDownVote.setOnClickListener(new VoteClickListener(Vote.DOWNVOTE));
                break;
            case UPVOTE:
                articleFeedbackUpVote.setImageResource(R.drawable.ic_like_filled_64px);
                articleFeedbackDownVote.setImageResource(R.drawable.ic_dislike_empty_64px);
                articleFeedbackUpVote.setOnClickListener(null);
                articleFeedbackDownVote.setOnClickListener(new VoteClickListener(Vote.DOWNVOTE));
                break;
            case DOWNVOTE:
                articleFeedbackUpVote.setImageResource(R.drawable.ic_like_empty_64px);
                articleFeedbackDownVote.setImageResource(R.drawable.ic_dislike_filled_64px);
                articleFeedbackUpVote.setOnClickListener(new VoteClickListener(Vote.UPVOTE));
                articleFeedbackDownVote.setOnClickListener(null);
                break;
        }
    }

    private void backClicked(){
        if(getActivity() != null){
            ((MainActivity) getActivity()).closeArticleFragment();
        }
    }

    private class VoteClickListener implements View.OnClickListener{

        Vote vote;

        VoteClickListener(Vote vote){
            this.vote = vote;
        }

        public void onClick(View v) {
            changeFeedbackStatus(vote);
            v.setOnClickListener(null);
            if(getActivity() != null){
                ((MainActivity)getActivity()).newsFeedbackSent(news.newsId, vote);
            }
        }
    }

}
