package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.User;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder> {

    private OnItemClickListener listener;

    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {
        holder.usernameText.setText(model.getUserName());
        holder.idText.setText(String.format("(%s)", model.getUserID()));
        holder.nameText.setText(String.format("%s %s", model.getFirstName(), model.getLastName()));
        holder.emailText.setText(model.getEmail());
        String phone = model.getPhone().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        holder.phoneText.setText(phone);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,
                parent, false);
        return new UserHolder(v);
    }

    class UserHolder extends RecyclerView.ViewHolder {

        TextView usernameText;
        TextView idText;
        TextView nameText;
        TextView emailText;
        TextView phoneText;

        public UserHolder(@NonNull final View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.usr_item_uname_txt);
            idText = itemView.findViewById(R.id.usr_item_id_txt);
            nameText = itemView.findViewById(R.id.usr_item_fname_txt);
            emailText = itemView.findViewById(R.id.usr_item_email_txt);
            phoneText = itemView.findViewById(R.id.usr_item_phone_txt);

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
