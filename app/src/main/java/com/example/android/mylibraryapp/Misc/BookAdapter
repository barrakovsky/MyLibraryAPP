package com.example.android.mylibraryapp.Misc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.BookHolder> {

    public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookHolder holder, int position, @NonNull Book model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewAuthor.setText(model.getAuthor());
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,
                parent, false);
        return new BookHolder(v);
    }

    class BookHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle
                ,textViewAuthor
                ,textViewPublisher
                ,textViewYear
                ,textViewGenre
                ,textViewAvailable
                ,textViewTotal
                ,textViewIsbn
                ,textViewSummary
                ,textViewPages;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.book_item_title);
            textViewAuthor = itemView.findViewById(R.id.book_item_author);
        }
    }

}
