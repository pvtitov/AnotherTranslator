package pvtitov.anothertranslator;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import pvtitov.anothertranslator.model.Word;
import pvtitov.anothertranslator.model.WordsManager;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mAdapter == null) {
            mAdapter = new RecyclerAdapter();
            mAdapter.setData(WordsManager.getInstance(this).extractAll());
            recyclerView.setAdapter(mAdapter);
        }
        else {
            reloadAdapter();
        }

        ImageButton addButton = findViewById(R.id.add_word);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
    }

    private void reloadAdapter() {
        mAdapter.setData(WordsManager.getInstance(this).extractAll());
        mAdapter.notifyDataSetChanged();
    }

    // Адаптер для RecyclerView
    private class RecyclerAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Word> mWords;

        public void setData(List<Word> mWords) {
            this.mWords = mWords;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Word item = mWords.get(position);
            holder.mWord.setText(item.getWord());
            holder.mTranslation.setText(item.getTranslation());
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }


    class ItemHolder extends RecyclerView.ViewHolder{

        private TextView mWord;
        private TextView mTranslation;

        ItemHolder(View itemView) {
            super(itemView);

            mWord = itemView.findViewById(R.id.word);
            mTranslation = itemView.findViewById(R.id.translation);

            itemView.setOnLongClickListener(v -> {
                WordsManager.getInstance(MainActivity.this).remove(mWord.getText().toString());
                reloadAdapter();
                return false;
            });
        }
    }
}
