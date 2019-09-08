package com.alesno.ratingkino.base;

public abstract class BasePresenter<T extends View> implements Presenter<T> {

    private T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }
}
