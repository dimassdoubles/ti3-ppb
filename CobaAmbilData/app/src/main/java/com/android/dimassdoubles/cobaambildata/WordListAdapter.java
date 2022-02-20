package com.android.dimassdoubles.cobaambildata;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
        }
    }

    private Cursor mCursor = null;
    private static final String TAG = WordListAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private Context mContext;

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setData(Cursor cursor) {
        Log.d(TAG, "Halo Bro 2");
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        Log.d(TAG, "Halo Bro 1");
        String word = "";
        int id = -1;
        if (mCursor != null) {
            int count = mCursor.getCount();
            if (position < count) {
                mCursor.moveToPosition(position);
                int indexWrod = mCursor.getColumnIndex(Contract.WordList.KEY_WORD);
                word = mCursor.getString(indexWrod);
                holder.wordItemView.setText(word);
            } else {
                holder.wordItemView.setText("Waiting");
            }
        } else {
            holder.wordItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return -1;
        }
    }
}
