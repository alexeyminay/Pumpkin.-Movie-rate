package com.alesno.ratingkino.ui;

import com.alesno.ratingkino.base.Presenter;
import com.alesno.ratingkino.base.View;

public interface SearchMVP {

    interface SearchView extends View {
        String getMovieName();
        String getYear();
        void showErrorSnackBar(int resource);
        void setResult(String result);
        void hideKeyboard();
        void showProgressBar();
        void hideProgressBar();
        void setClickableButton(boolean isClickable);
        void startCircularButtonRevealAnimation(int centerX, int centerY
                , int startRadius, int endRadius, int duration, int visibilityViewStat);
        int getButtonWidth();
        int getButtonHeight();
    }

    interface SearchPresenter extends Presenter<SearchView>{
        void onButtonClicked();
    }
}
