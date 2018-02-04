package pvtitov.anothertranslator;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

public class MainActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener{

    public static final String WORD = "word_to_pass";
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
        // добавление новой записи
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
    }

    private void reloadAdapter() {
        mAdapter.setData(WordsManager.getInstance(this).extractAll());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickPositive(DialogFragment dialogFragment, String word) {
        WordsManager.getInstance(MainActivity.this).remove(word);
        reloadAdapter();
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

            // редактирование записи
            holder.mItemView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(WORD, item.getWord());
                startActivity(intent);
            });

            // удаление записей - всех, содержащих слово
            holder.mItemView.setOnLongClickListener(v -> {
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.setWord(item.getWord());
                deleteDialog.show(getSupportFragmentManager(), DeleteDialog.DELETE_TAG);
                return false;
            });
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder{

        View mItemView;
        TextView mWord;
        TextView mTranslation;

        ItemHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            mWord = mItemView.findViewById(R.id.word);
            mTranslation = mItemView.findViewById(R.id.translation);
        }
    }
}
