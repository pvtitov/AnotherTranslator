package pvtitov.anothertranslator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pvtitov.anothertranslator.model.Word;
import pvtitov.anothertranslator.model.WordsManager;
import pvtitov.anothertranslator.networking.RetrofitFactory;
import pvtitov.anothertranslator.networking.TranslationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Павел on 15.01.2018.
 */

public class AddActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener {

    private static final String BASE_URL = "https://translate.yandex.net";
    private static final String API_KEY = "trnsl.1.1.20170403T203024Z.e1c296e170563e6b.112043154a95d73055b48634e4607682bdd23817";
    private final long DELAY = 1000;

    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable mReloadSuggestions;
    EditText mInputWord;
    EditText mForTranslation;
    ArrayAdapter<String> mAdapter;
    Word mWord = new Word();
    boolean editWord = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intentStartedActivity = getIntent();
        if (intentStartedActivity.hasExtra(MainActivity.WORD)) {
            mWord = WordsManager.getInstance(this)
                    .findByWord(intentStartedActivity.getStringExtra(MainActivity.WORD));
            editWord = true;
        }

        if (editWord) {
            ImageButton deleteButton = findViewById(R.id.delete_word);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> {
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.setWord(mWord.getWord());
                deleteDialog.show(getSupportFragmentManager(), DeleteDialog.DELETE_TAG);
            });
        }

        mForTranslation = findViewById(R.id.translation);
        if (editWord) mForTranslation.setText(mWord.getTranslation());
        Button confirmButton = findViewById(R.id.confirm);
        // Сохраняет перевод в базу, запускает MainActivity
        confirmButton.setOnClickListener(v -> {
            mWord.setTranslation(mForTranslation.getText().toString());
            try {
                if ((mWord.getWord().length() == 0) || (mWord.getTranslation().length() == 0))
                    return;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return;
            }
            WordsManager.getInstance(AddActivity.this).addNew(mWord);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.suggestions);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> mForTranslation.setText(mAdapter.getItem(position)));

        mInputWord = findViewById(R.id.input_word);
        if (editWord) mInputWord.setText(mWord.getWord());
        mInputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                getSuggestions();
            }
        });
    }

    private void getSuggestions() {

        mHandler.removeCallbacks(mReloadSuggestions);

        mWord.setWord(mInputWord.getText().toString());

        mReloadSuggestions = () -> {
            RetrofitFactory.WebService webService = RetrofitFactory.getInstance(BASE_URL).create(RetrofitFactory.WebService.class);

            String translationDirection = "ru";

            Call<TranslationModel> call = webService.getTranslation(
                    API_KEY,
                    translationDirection,
                    mWord.getWord());
            call.enqueue(new Callback<TranslationModel>() {
                @Override
                public void onResponse(Call<TranslationModel> call, Response<TranslationModel> response) {
                    if (response.isSuccessful()) {
                        try {
                            List<String> translations = response.body().getText();
                            mAdapter.clear();
                            mAdapter.addAll(translations);
                            mAdapter.notifyDataSetChanged();
                            if (mForTranslation.getText().toString().length() == 0) mForTranslation.setText(translations.get(0));
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TranslationModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        };
        mHandler.postDelayed(mReloadSuggestions, DELAY);
    }

    @Override
    public void onClickPositive(DialogFragment dialogFragment, String word) {
        WordsManager.getInstance(AddActivity.this).remove(word);
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
