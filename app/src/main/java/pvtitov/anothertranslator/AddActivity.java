package pvtitov.anothertranslator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        List<String> translations = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, translations);
        listView.setAdapter(adapter);

        EditText inputWord = findViewById(R.id.input_word);
        inputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            private Timer timer = new Timer();
            private final long DELAY = 500;

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        translations.add(inputWord.getText().toString());
                        for (String item: translations) Log.d("happy", item);
                    }
                }, DELAY);
            }
        });


    }
}
