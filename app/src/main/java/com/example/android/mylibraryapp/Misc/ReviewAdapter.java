package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Review;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, hh:mm a", Locale.ENGLISH);
        String dateString = dateFormat.format(model.getOrigDate());
        holder.origDateText.setText(dateString);
        holder.reviewText.setText(model.getReview());

        // We only need see an edited date if it's been edited!
        if ( 0 != (model.getOrigDate()).compareTo(model.getLastDate())) {
            holder.editDateText.setVisibility(View.VISIBLE);
            dateString = dateFormat.format(model.getLastDate());
            holder.editDateText.setText(String.format("Last edited on %s", dateString));
        } else {
            holder.editDateText.setVisibility(View.INVISIBLE);
        }

        if (!model.getUserID().equals(FirebaseAuth.getInstance().getUid())) {
            holder.editButton.setVisibility(View.INVISIBLE);
        } else {
            holder.editButton.setVisibility(View.VISIBLE);
        }
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
        TextView origDateText;
        TextView reviewText;
        TextView editDateText;
        ImageButton editButton;

        public ReviewHolder(@NonNull final View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.rev_item_user_tx);
            origDateText = itemView.findViewById(R.id.rev_item_date_tx);
            reviewText = itemView.findViewById(R.id.rev_item_main_tx);
            editDateText = itemView.findViewById(R.id.rev_item_editdate_tx);
            editButton = itemView.findViewById(R.id.rev_item_edit_ibut);

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
