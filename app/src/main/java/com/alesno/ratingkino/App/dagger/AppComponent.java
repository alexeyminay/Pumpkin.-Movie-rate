package com.alesno.ratingkino.App.dagger;

import com.alesno.ratingkino.ui.dagger.SearchActivitySubcomponent;

import dagger.Component;

@AppScope
@Component
public interface AppComponent {
    SearchActivitySubcomponent createSearchActivitySubcomponent();
}
