package com.alesno.ratingkino.ui.dagger;


import com.alesno.ratingkino.network.KinopoiskHTMLParser;
import com.alesno.ratingkino.network.KinopoiskRatingRepository;
import com.alesno.ratingkino.ui.SearchPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SearchActivityModule {

    @SearchActivityScope
    @Provides
    public KinopoiskHTMLParser provideKinopoiskHtmlParser(){
        return new KinopoiskHTMLParser();
    }

    @SearchActivityScope
    @Provides
    public KinopoiskRatingRepository.KinopoiskRatingService provideKinopoiskRatingService(){
        return KinopoiskRatingRepository.getRetrofit()
                .create(KinopoiskRatingRepository.KinopoiskRatingService.class);
    }

    @SearchActivityScope
    @Provides
    public SearchPresenter provideSearchPresenter(KinopoiskHTMLParser kinopoiskParser,
                                                  KinopoiskRatingRepository.KinopoiskRatingService kinopoiskRatingService){
        return new SearchPresenter(kinopoiskParser,
                kinopoiskRatingService,
                Schedulers.io(),
                AndroidSchedulers.mainThread());
    }
}
