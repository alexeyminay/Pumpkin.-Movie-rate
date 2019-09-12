package com.alesno.ratingkino.ui.dagger;

import com.alesno.ratingkino.ui.SearchActivity;

import dagger.Subcomponent;

@Subcomponent(modules = SearchActivityModule.class)
@SearchActivityScope
public interface SearchActivitySubcomponent {
    void inject(SearchActivity searchActivity);
}
