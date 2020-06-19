package com.dertsizvebugsiz.news.enums;

public enum Vote {

    UPVOTE,
    DOWNVOTE,
    NONE;

    public static int parseInt(Vote vote){
        switch (vote){
            case UPVOTE:
                return 1;
            case DOWNVOTE:
                return -1;
            case NONE:
                return 0;
        }
        return 0;
    }

    public static Vote parseEnum(int i){
        switch (i){
            case -1:
                return Vote.DOWNVOTE;
            case 0:
                return Vote.NONE;
            case 1:
                return Vote.UPVOTE;
        }
        return Vote.NONE;
    }

}
