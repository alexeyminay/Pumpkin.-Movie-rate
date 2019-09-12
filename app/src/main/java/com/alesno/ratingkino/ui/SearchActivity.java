package com.alesno.ratingkino.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alesno.ratingkino.App.App;
import com.alesno.ratingkino.R;
import com.alesno.ratingkino.utils.MyAnimatorListener;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements SearchMVP.SearchView {

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.edit_input_name) EditText mEditInputName;
    @BindView(R.id.edit_input_years) EditText mEditInputYear;
    @BindView(R.id.text_result) TextView mTextResult;
    @BindView(R.id.button_search) Button mButtonSearch;

    @Inject SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        App.getApp(this).getSearchActivitySubcomponent().inject(this);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
    }

    @OnClick(R.id.button_search)
    public void onButtonSearchClick(){
        mPresenter.onButtonClicked();
    }

    @Override
    public String getMovieName() {
        return mEditInputName.getText().toString();
    }

    @Override
    public String getYear() {
        return mEditInputYear.getText().toString();
    }

    @Override
    public void showErrorSnackBar(int resource) {
        Snackbar.make(mEditInputName, getResources().getString(resource), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setResult(String result) {
        mTextResult.setText(result);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setClickableButton(boolean isClickable) {
        mButtonSearch.setClickable(isClickable);
    }

    @Override
    public void startCircularButtonRevealAnimation(int centerX, int centerY,
                                                   int startRadius, int endRadius,
                                                   int duration,
                                                   int visibilityViewStat) {
        mButtonSearch.setVisibility(View.VISIBLE);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                mButtonSearch,
                centerX,
                centerY,
                startRadius,
                endRadius);
        circularReveal.setDuration(duration);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        circularReveal.start();
        circularReveal.addListener(new MyAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mButtonSearch.setVisibility(visibilityViewStat);
            }
        });
    }

    @Override
    public int getButtonWidth() {
        return mButtonSearch.getWidth();
    }

    @Override
    public int getButtonHeight() {
        return mButtonSearch.getHeight();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if(isFinishing()){
            App.getApp(this).releaseFirstActivityComponent();
        }
    }
}
