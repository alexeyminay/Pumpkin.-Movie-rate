package com.alesno.ratingkino.base;

public interface Presenter<V extends View>{
    void attachView(V view);
    void viewIsReady();
    void detachView();
}
