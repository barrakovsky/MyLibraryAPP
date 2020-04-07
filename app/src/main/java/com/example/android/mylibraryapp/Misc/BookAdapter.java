package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.BookHolder> {

    OnItemClickListener listener;

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
                ,textViewAuthor;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.book_item_title);
            textViewAuthor = itemView.findViewById(R.id.book_item_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
