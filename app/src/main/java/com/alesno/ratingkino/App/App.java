package com.alesno.ratingkino.App;

import android.app.Application;
import android.content.Context;

import com.alesno.ratingkino.App.dagger.AppComponent;
import com.alesno.ratingkino.App.dagger.DaggerAppComponent;
import com.alesno.ratingkino.ui.SearchActivity;
import com.alesno.ratingkino.ui.dagger.SearchActivitySubcomponent;

public class App extends Application {

    private AppComponent appComponent;
    private SearchActivitySubcomponent searchActivitySubcomponent;

    public static App getApp(Context context){
        return (App)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public SearchActivitySubcomponent getSearchActivitySubcomponent(){
        if(searchActivitySubcomponent == null){
            searchActivitySubcomponent = getAppComponent().createSearchActivitySubcomponent();
        }
        return searchActivitySubcomponent;
    }

    public void releaseFirstActivityComponent(){
        searchActivitySubcomponent = null;
    }
}
