package com.alesno.ratingkino.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alesno.ratingkino.App.App;
import com.alesno.ratingkino.R;
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if(isFinishing()){
            App.getApp(this).releaseFirstActivityComponent();
        }
    }
}
