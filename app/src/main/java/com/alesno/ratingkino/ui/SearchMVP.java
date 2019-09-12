package com.alesno.ratingkino.ui;

import com.alesno.ratingkino.base.Presenter;
import com.alesno.ratingkino.base.View;

public interface SearchMVP {

    interface SearchView extends View {
        String getMovieName();
        String getYear();
        void setResult(String result);
        void hideKeyboard();
        void showProgressBar();
        void hideProgressBar();
    }

    interface SearchPresenter extends Presenter<SearchView>{
        void onButtonClicked();
    }
}
