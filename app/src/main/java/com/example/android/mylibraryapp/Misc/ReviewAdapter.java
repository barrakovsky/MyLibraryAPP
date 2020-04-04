package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Book;
import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReviewAdapter extends FirestoreRecyclerAdapter<Review, ReviewAdapter.ReviewHolder> {

    private OnItemClickListener listener;

    public ReviewAdapter(@NonNull FirestoreRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull Review model) {
        holder.usernameText.setText(model.getUserName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String dateString = dateFormat.format(model.getDate());
        holder.dateText.setText(dateString);
        holder.reviewText.setText(model.getReview());
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,
                parent, false);
        return new ReviewHolder(v);
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView dateText;
        TextView reviewText;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.rev_item_user_tx);
            dateText = itemView.findViewById(R.id.rev_item_date_tx);
            reviewText = itemView.findViewById(R.id.rev_item_main_tx);

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
