package com.example.thomasfox.thestudentsaver;

/**
 * Created by thomasfox on 20/11/2017.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class DiscountRecycleAdapter extends RecyclerView.Adapter<DiscountRecycleAdapter.DiscountsViewHolder> {
    
    private List<Discounts> listDiscounts;

    public DiscountRecycleAdapter(List<Discounts> listDiscounts) {
        this.listDiscounts = listDiscounts;
    }

    @Override
    public DiscountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount_recycler, parent, false);

        return new DiscountsViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(DiscountsViewHolder holder, int position) {

        holder.textViewClientName.setText(listDiscounts.get(position).getClientName());
        holder.textViewDescription.setText(listDiscounts.get(position).getDescription());
        holder.textViewDiscountCode.setText(listDiscounts.get(position).getDiscountCode());
    }

    @Override
    public int getItemCount() {
        Log.v(DiscountRecycleAdapter.class.getSimpleName(),""+listDiscounts.size());
        return listDiscounts.size();
    }


    /**
     * ViewHolder class
     */
    public class DiscountsViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewClientName;
        public AppCompatTextView textViewDescription;
        public AppCompatTextView textViewDiscountCode;


        public DiscountsViewHolder(View view) {
            super(view);
            textViewClientName = (AppCompatTextView) view.findViewById(R.id.textViewClientName);
            textViewDescription = (AppCompatTextView) view.findViewById(R.id.textViewDescription);
            textViewDiscountCode = (AppCompatTextView) view.findViewById(R.id.textViewDiscountCode);
        }
    }


}