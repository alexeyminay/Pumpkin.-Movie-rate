package com.alesno.ratingkino.ui;

import android.util.Log;

import com.alesno.ratingkino.base.BasePresenter;
import com.alesno.ratingkino.model.Rating;
import com.alesno.ratingkino.network.KinopoiskParser;
import com.alesno.ratingkino.network.KinopoiskRatingRepository;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class SearchPresenter extends BasePresenter<SearchMVP.SearchView> implements SearchMVP.SearchPresenter {


    private Disposable mQueryDisposable;
    private KinopoiskParser mKinopoiskParser;
    private KinopoiskRatingRepository.KinopoiskRatingService mKinopoiskRatingService;
    private Scheduler mSubscribeScheduler;
    private Scheduler mResultScheduler;

    public SearchPresenter(KinopoiskParser kinopoiskParser,
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

    }

    @Override
    public void onButtonClicked() {
        getView().setResult("");
        getView().showProgressBar();
        mQueryDisposable = Single.fromCallable(() -> mKinopoiskParser.getIdFilm(getView().getMovieName()))
                .subscribeOn(mSubscribeScheduler)
                .observeOn(mResultScheduler)
                .subscribe(this::getRating,
                        throwable -> Log.e("mLog", throwable.getMessage(), throwable));
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
                .subscribe(rating -> showRating(rating),
                        throwable -> Log.e("mLog", throwable.getMessage(), throwable));
    }

    private void showRating(Rating rating) {
        StringBuilder result = new StringBuilder();
        result.append("Kinopoisk: ")
                .append(rating.getRatingKinopoisk())
                .append(",\n")
                .append("IMDb: ")
                .append(rating.getRatingIMDb());
        getView().hideProgressBar();
        getView().setResult(result.toString());
    }
}
