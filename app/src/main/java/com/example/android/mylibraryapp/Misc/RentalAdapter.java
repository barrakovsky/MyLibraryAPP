package com.example.android.mylibraryapp.Misc;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.mylibraryapp.EntityObjects.Rental;
import com.example.android.mylibraryapp.R;

import java.util.ArrayList;
import java.util.List;

public class RentalAdapter extends ArrayAdapter<Rental> {

    private Context mContext;
    private List<Rental> rentalList = new ArrayList<>();

    public RentalAdapter(@NonNull Context context, ArrayList<Rental> list) {
        super(context, 0 , list);
        mContext = context;
        rentalList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Rental currentRental = rentalList.get(position);

        TextView bookName = (TextView) listItem.findViewById(R.id.bookName);
        bookName.setText(currentRental.getBook().toString());

        TextView bookDate = (TextView) listItem.findViewById(R.id.bookDate);
        bookDate.setText(currentRental.getRentalDueDate().toString());

        return listItem;
    }
}
