package com.dimassdoubles.wordlistsqlinteractive;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    // custom view holder with a text view and two buttons.
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
    // public static final String EXTRA_POSITION = "POSITION";
    // A11202012497

    private final LayoutInflater mInflater;
    DatabaseOpenHelper mDB;
    Context mContext;

    public WordListAdapter(Context context, DatabaseOpenHelper db) {
        this.mInflater = LayoutInflater.from(context);
        this.mDB = db;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist_item, parent,false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        WordItem current = mDB.query(position);
        int currentId = current.getId();
        String currentWord = current.getWord();
        holder.wordItemView.setText(currentWord);

        // keep a reference to the view holder for the click listener
        final WordViewHolder h = holder;
        // attach a click listener to the delete button
        holder.delete_button.setOnClickListener(new MyButtonOnClickListener(currentId, null) {
            @Override
            public void onClick(View v) {
                // you have to get the position like this, you can't hol a reference
                Log.d (TAG + "onClick", "VHPos " + h.getAdapterPosition() + " ID " + id);
                mDB.delWord(id);
                // dimas saputro
                // kode dibawah untuk update tampilan fragment,
                // agar item yang dihapus menghilang dari tampilan.
                notifyItemRemoved(h.getAdapterPosition());
            }
        });


        holder.edit_button.setOnClickListener(new MyButtonOnClickListener(currentId, currentWord){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditWordActivity.class);

                Log.d (TAG + "onClick", "VHPos " + h.getAdapterPosition() + " ID " + id);

                intent.putExtra(EXTRA_ID, id);
                intent.putExtra(EXTRA_WORD, word);

                // Start an empty edit activity.
                ((Activity) mContext).startActivityForResult(intent, MainActivity.WORD_EDIT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

}
