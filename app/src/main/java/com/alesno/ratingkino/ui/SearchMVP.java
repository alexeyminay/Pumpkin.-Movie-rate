package com.alesno.ratingkino.ui;

import com.alesno.ratingkino.base.Presenter;
import com.alesno.ratingkino.base.View;

public interface SearchMVP {

    interface SearchView extends View {
        String getMovieName();
        void setResult(String result);
    }

    interface SearchPresenter extends Presenter<SearchView>{
        void onButtonClicked();
    }
}
