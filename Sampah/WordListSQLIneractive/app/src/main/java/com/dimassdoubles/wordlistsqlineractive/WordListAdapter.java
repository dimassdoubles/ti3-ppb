package com.dimassdoubles.wordlistsqlineractive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    /**
     *  Custom view holder with a text view and two buttons.
     */

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        Button delete_button;
        Button edit_button;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            delete_button = (Button) itemView.findViewById(R.id.delete_button);
            edit_button = (Button) itemView.findViewById(R.id.edit_button);

        }
    }

    private static final String TAG = WordListAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_WORD = "WORD";

    private final LayoutInflater mInflater;
    DatabaseHandler mDB;
    Context mContext;

    public WordListAdapter(Context context, DatabaseHandler db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
    }

    @Override
    public int getItemCount() {
        // Placeholder so we can see some mock data.
        return 10;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position, @NonNull List<Object> payloads) {
        // Tidak ada di starter (1 line dibawah)
        super.onBindViewHolder(holder, position, payloads);
        Word current = mDB.GetWord(position);
        holder.wordItemView.setText(current.getWord());
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {

    }
}
