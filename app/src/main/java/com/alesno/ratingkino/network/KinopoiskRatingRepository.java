package com.alesno.ratingkino.network;

import com.alesno.ratingkino.model.Rating;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class KinopoiskRatingRepository {

    private static final String BASE_URL = "https://rating.kinopoisk.ru/";

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public interface KinopoiskRatingService {

        @GET("{id}.xml")
        Single<Rating> getHTMLPage(@Path("id")String id);
    }
}
