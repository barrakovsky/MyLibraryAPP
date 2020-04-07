package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Favorite;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FavoriteAdapter extends FirestoreRecyclerAdapter<Favorite, FavoriteAdapter.FavoriteHolder> {
    private OnItemClickListener listener;

    public FavoriteAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FavoriteHolder holder, int position, @NonNull Favorite model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewAuthor.setText(model.getAuthor());
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,
                parent, false);
        return new FavoriteHolder(v);
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle
                ,textViewAuthor;

        public FavoriteHolder(@NonNull View itemView) {
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
