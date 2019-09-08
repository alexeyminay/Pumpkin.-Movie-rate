package com.alesno.ratingkino.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alesno.ratingkino.R;
import com.alesno.ratingkino.network.KinopoiskParser;
import com.alesno.ratingkino.network.KinopoiskRatingRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements SearchMVP.SearchView {

    @BindView(R.id.edit_input) EditText mEditInput;
    @BindView(R.id.text_result) TextView mTextResult;

    SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        KinopoiskParser kinopoiskParser = new KinopoiskParser();
        KinopoiskRatingRepository.KinopoiskRatingService kinopoiskRatingService =
                KinopoiskRatingRepository.getRetrofit()
                        .create(KinopoiskRatingRepository.KinopoiskRatingService.class);

        mPresenter = new SearchPresenter(kinopoiskParser,
                kinopoiskRatingService,
                Schedulers.io(),
                AndroidSchedulers.mainThread());
        mPresenter.attachView(this);
        mPresenter.viewIsReady();
    }

    @OnClick(R.id.button_search)
    public void onButtonSearchClick(){
        mPresenter.onButtonClicked();
    }

    @Override
    public String getMovieName() {
        return mEditInput.getText().toString();
    }

    @Override
    public void setResult(String result) {
        mTextResult.setText(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
