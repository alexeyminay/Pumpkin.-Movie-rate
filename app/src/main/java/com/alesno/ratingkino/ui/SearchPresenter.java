package com.alesno.ratingkino.ui;

import android.util.Log;

import com.alesno.ratingkino.base.BasePresenter;
import com.alesno.ratingkino.model.Movie;
import com.alesno.ratingkino.model.Rating;
import com.alesno.ratingkino.network.KinopoiskHTMLParser;
import com.alesno.ratingkino.network.KinopoiskRatingRepository;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class SearchPresenter extends BasePresenter<SearchMVP.SearchView> implements SearchMVP.SearchPresenter {


    private Disposable mQueryDisposable;
    private KinopoiskHTMLParser mKinopoiskParser;
    private KinopoiskRatingRepository.KinopoiskRatingService mKinopoiskRatingService;
    private Scheduler mSubscribeScheduler;
    private Scheduler mResultScheduler;
    private Rating mRating;

    public SearchPresenter(KinopoiskHTMLParser kinopoiskParser,
                           KinopoiskRatingRepository.KinopoiskRatingService mKinopoiskRatingService,
                           Scheduler subscribeScheduler,
                           Scheduler resultScheduler) {
        this.mKinopoiskParser = kinopoiskParser;
        this.mKinopoiskRatingService = mKinopoiskRatingService;
        mSubscribeScheduler = subscribeScheduler;
        mResultScheduler = resultScheduler;
    }

    @Override
    public void viewIsReady() {
        if(mRating == null) return;
        showRating();
    }

    @Override
    public void onButtonClicked() {
        getView().setResult("");
        getView().showProgressBar();
        getView().hideKeyboard();

        mQueryDisposable = Completable.fromCallable((Callable<Void>) () -> {
            mKinopoiskParser.getHtmlPage(new Movie(getView().getMovieName(), getView().getYear()));
            return null;
        }).subscribeOn(mSubscribeScheduler)
                .observeOn(mResultScheduler)
                .subscribe(() -> getRating(mKinopoiskParser.getIdFilm()));
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mQueryDisposable!=null && !mQueryDisposable.isDisposed())
            mQueryDisposable.dispose();
    }

    private void getRating(String idFilm){
        mQueryDisposable = mKinopoiskRatingService
                .getHTMLPage(idFilm).subscribeOn(mSubscribeScheduler)
                .observeOn(mResultScheduler)
                .subscribe(rating -> {
                            this.mRating = rating;
                            showRating();
                        },
                        throwable -> Log.e("mLog", throwable.getMessage(), throwable));
    }

    private void showRating() {
        StringBuilder result = new StringBuilder();
        result.append("Kinopoisk: ")
                .append(mRating.getRatingKinopoisk())
                .append(",\n")
                .append("IMDb: ")
                .append(mRating.getRatingIMDb())
                .append(",\n")
                .append("Год фильма: ")
                .append(mKinopoiskParser.getFilmYear());
        getView().hideProgressBar();
        getView().setResult(result.toString());
    }
}
