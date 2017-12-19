package pvtitov.anothertranslator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.main_list);
        String[] listExample = {"one", "two", "three"};
        recyclerView.setAdapter(new RecyclerAdapter());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static private class RecyclerAdapter extends RecyclerView.Adapter {

        /*
        Обработчик клика
        */
        OnItemClickListener mClickListener;

        interface OnItemClickListener {
            void onClick();
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            mClickListener = listener;
        }



        private List<String> mWords = new ArrayList<>();


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            String item = mWords.get(position);
            //Отобразить в поле mWord холдера item: holder.
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder{

        TextView mWord;
        TextView mTranslation;

        ItemHolder(View itemView) {
            super(itemView);

            mWord = itemView.findViewById(R.id.word);
            mTranslation = itemView.findViewById(R.id.translation);
        }
    }
}
