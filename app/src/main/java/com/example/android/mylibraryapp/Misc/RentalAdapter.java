package com.example.android.mylibraryapp.Misc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.LinearLayoutCompat;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mylibraryapp.EntityObjects.Payment;
import com.example.android.mylibraryapp.EntityObjects.Rental;
import com.example.android.mylibraryapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RentalAdapter extends FirestoreRecyclerAdapter<Rental, RentalAdapter.RentalHolder> {

    private List<Rental> dataRentallList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public RentalAdapter(@NonNull FirestoreRecyclerOptions<Rental> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalHolder holder, int position, @NonNull Rental model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
        String dateString = dateFormat.format(model.getRentalDueDate());
        holder.bookTitle.setText(model.getBookTitle());
        holder.dueDate.setText(dateString);

        Date startDate = model.getRentalStartDate();
        Date dueDate = model.getRentalDueDate();

        int diffInDays = Math.round(( (dueDate.getTime() - startDate.getTime())
                / (1000 * 60 * 60 * 24) ));

        if(diffInDays >= 29){
            holder.renewRental.setEnabled(false);
            holder.renewRental.setText("Disable");

        }
    }

    @NonNull
    @Override
    public RentalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myrental_list_adapter, parent, false);
        return new RentalHolder(view, mListener);
    }


    class RentalHolder extends RecyclerView.ViewHolder{
        TextView bookTitle;
        TextView dueDate;
        Button renewRental;



        protected RentalHolder(@NonNull final View itemView, final OnItemClickListener listener){
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookName);
            dueDate = itemView.findViewById(R.id.var_myrental_duedate);
            renewRental = itemView.findViewById(R.id.bmyrentals_renewRental);

            renewRental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onButtonClick(position);
                        }
                    }
                }
            });



        }
    }



}