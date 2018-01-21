package pvtitov.anothertranslator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class AddActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://translate.yandex.net";
    private static final String API_KEY = "trnsl.1.1.20170403T203024Z.e1c296e170563e6b.112043154a95d73055b48634e4607682bdd23817";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ListView listView = findViewById(R.id.translations_list);
        ArrayAdapter<Word> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<Word>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Добавляет выбранное слово в базу и запускаем снова MainActivity
                WordsManager.getInstance(AddActivity.this).addNew(adapter.getItem(position));
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        EditText inputWord = findViewById(R.id.input_word);
        inputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            private final long DELAY = 1000;
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable reloadSuggestions;
            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(reloadSuggestions);
                reloadSuggestions = () -> {
                    RetrofitFactory.WebService webService = RetrofitFactory.getInstance(BASE_URL).create(RetrofitFactory.WebService.class);
                    String word = inputWord.getText().toString();
                    Call<TranslationModel> call = webService.getTranslation(
                            API_KEY,
                            "en-ru",
                            word);
                    call.enqueue(new Callback<TranslationModel>() {
                        @Override
                        public void onResponse(Call<TranslationModel> call, Response<TranslationModel> response) {
                            if (response.isSuccessful()) {
                                try {
                                    List<String> translations = response.body().getText();
                                    adapter.clear();
                                    for (String translation:translations) adapter.add(new Word(word, translation));
                                    adapter.notifyDataSetChanged();
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
                handler.postDelayed(reloadSuggestions, DELAY);
            }
        });


    }
}
