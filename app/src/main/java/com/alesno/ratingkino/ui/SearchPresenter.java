package com.alesno.ratingkino.ui;

import android.util.Log;
import android.view.View;

import com.alesno.ratingkino.R;
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

    public static final int MIN_BUTTON_ANIMATION_SIZE = 16;
    public static final int DURATION_REVEAL_ANIMATION = 500;

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
        if(getView().getMovieName().isEmpty()){
            getView().hideKeyboard();
            getView().showErrorSnackBar(R.string.input_movie_name);
            return;
        }

        startCloseButtonRevealAnimation();

        getView().setResult("");
        getView().showProgressBar();
        getView().hideKeyboard();
        getView().setClickableButton(false);

        mQueryDisposable = Completable.fromCallable((Callable<Void>) () -> {
            mKinopoiskParser.getHtmlPage(new Movie(getView().getMovieName(), getView().getYear()));
            return null;
        }).subscribeOn(mSubscribeScheduler)
                .observeOn(mResultScheduler)
                .subscribe(() -> getRating(mKinopoiskParser.getIdFilm())
                        , throwable -> showFailResult());
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mQueryDisposable!=null && !mQueryDisposable.isDisposed())
            mQueryDisposable.dispose();
    }

    private void showFailResult(){
        startOpenButtonRevealAnimation();
        getView().hideProgressBar();
        getView().setClickableButton(true);
        getView().showErrorSnackBar(R.string.movie_is_not_find);
    }

    private void getRating(String idFilm){
        mQueryDisposable = mKinopoiskRatingService
                .getHTMLPage(idFilm).subscribeOn(mSubscribeScheduler)
                .observeOn(mResultScheduler)
                .subscribe(rating -> {
                            this.mRating = rating;
                            startOpenButtonRevealAnimation();
                            showRating();
                        }, throwable -> showFailResult());
    }

    private void showRating() {
        StringBuilder result = new StringBuilder("Результаты поиска: \n");
        result.append(mKinopoiskParser.getFilmName())
                .append(",\n")
                .append("Kinopoisk: ")
                .append(mRating.getRatingKinopoisk())
                .append(",\n")
                .append("IMDb: ")
                .append(mRating.getRatingIMDb())
                .append(",\n")
                .append("Год фильма: ")
                .append(mKinopoiskParser.getFilmYear());

        getView().hideProgressBar();
        getView().setClickableButton(true);
        getView().setResult(result.toString());
    }

    private void startOpenButtonRevealAnimation(){
        getView().startCircularButtonRevealAnimation(getView().getButtonWidth()/2,
                getView().getButtonHeight()/2,
                MIN_BUTTON_ANIMATION_SIZE,
                getView().getButtonWidth(),
                DURATION_REVEAL_ANIMATION,
                View.VISIBLE);
    }

    private void startCloseButtonRevealAnimation(){
        getView().startCircularButtonRevealAnimation(getView().getButtonWidth()/2,
                getView().getButtonHeight()/2,
                getView().getButtonWidth(),
                MIN_BUTTON_ANIMATION_SIZE,
                DURATION_REVEAL_ANIMATION,
                View.INVISIBLE);
    }
}
