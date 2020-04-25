package com.example.android.mylibraryapp.Misc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Payment;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PaymentAdapter extends FirestoreRecyclerAdapter<Payment, PaymentAdapter.PaymentHolder> {

    public PaymentAdapter(@NonNull FirestoreRecyclerOptions<Payment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PaymentHolder holder, int position, @NonNull Payment model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
        String dateString = dateFormat.format(model.getInvDate());
        holder.date.setText(dateString);
        String amountString = String.format(Locale.ENGLISH,"$%.2f", model.getAmount());
        holder.amount.setText(amountString);
        holder.description.setText(model.getItemDesc());
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item,
                parent, false);
        return new PaymentHolder(v);
    }

    class PaymentHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView amount;
        TextView description;

        public PaymentHolder(@NonNull final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.payitem_date_tx);
            amount = itemView.findViewById(R.id.payitem_amt_tx);
            description = itemView.findViewById(R.id.payitem_desc_tx);

        }
    }
}
