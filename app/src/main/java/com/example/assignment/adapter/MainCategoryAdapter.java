/**

 The MainCategoryAdapter is a RecyclerView adapter that binds data to the Main Category RecyclerView.
 It uses the data model class to get the required data and displays it in the RecyclerView.
 The adapter implements an OnItemClickListener interface that allows the user to interact with the
 RecyclerView items.
 */
package com.example.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.dao.dataModel;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.RecipieViewholder> {
    Context ctx;
    private final RecyclerViewInterface recyclerViewInterface;
    private OnItemClickListener listener;
    private ArrayList<dataModel> arrMainCategory;

    // Constructor
    public MainCategoryAdapter(Context context, ArrayList<dataModel> mainCateg, RecyclerViewInterface recyclerViewInterfaces) {
        this.ctx = context;
        this.recyclerViewInterface = recyclerViewInterfaces;
        this.arrMainCategory = mainCateg;
    }

    // View Holder class for the RecyclerView items
    public class RecipieViewholder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView item_title;

        // Constructor
        public RecipieViewholder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_dish);
            item_title = itemView.findViewById(R.id.tv_dish_name);

            // Set an OnClickListener to handle item clicks
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemclick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecipieViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the RecyclerView item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main_category, parent, false);
        return new RecipieViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipieViewholder holder, int position) {
        // Bind data to the RecyclerView item
        holder.imgView.setImageResource(arrMainCategory.get(position).img);
        holder.item_title.setText(arrMainCategory.get(position).title);
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the RecyclerView
        return arrMainCategory.size();
    }

    // Interface to handle item clicks in the RecyclerView
    public interface OnItemClickListener {
        void onClicked(String categoryName);
    }

    // Method to set data in the adapter
    public void setData(List<dataModel> arrData) {
        arrMainCategory = new ArrayList<>(arrData);
    }

    // Method to set the click listener for the adapter
    public void setClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }




}
