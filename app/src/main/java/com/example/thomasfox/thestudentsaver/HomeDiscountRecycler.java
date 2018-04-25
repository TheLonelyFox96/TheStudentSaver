/**
 * Created by thomasfox on 02/02/2018.
 */



package com.example.thomasfox.thestudentsaver;

        import android.app.AlertDialog;
        import android.app.FragmentManager;
        import android.support.v4.app.Fragment;
        import android.app.FragmentTransaction;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.v7.widget.AppCompatTextView;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;

        import com.bumptech.glide.Glide;

        import java.util.List;

public class HomeDiscountRecycler extends RecyclerView.Adapter<HomeDiscountRecycler.DiscountsViewHolder> {

    private List<Discounts> listNewDiscounts;
    public Context c;
    Button moredetails_button;

    public HomeDiscountRecycler(List<Discounts> listDiscounts, Context context) {
        this.listNewDiscounts = listDiscounts;
        this.c = context;
    }



    @Override
    public DiscountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_discount_recycler, parent, false);


        return new DiscountsViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder (DiscountsViewHolder holder, int position) {
        String client = listNewDiscounts.get(position).getClientName();
        holder.textViewClientName.setText(listNewDiscounts.get(position).getClientName());
        holder.textViewDescription.setText(listNewDiscounts.get(position).getDescription());
//        holder.textViewDiscountCode.setText(listNewDiscounts.get(position).getDiscountCode());


        String clientName = client.toLowerCase().replace("'", "");
        Integer image = null;

        switch(client){
            case "Mcdonalds":
                image = R.drawable.mcdonald_s_logo;
                break;
            case "Accessorize":
                image = R.drawable.accessorize_logo;
                break;
            case "Ann Summers":
                image = R.drawable.ann_summers_logo;
                break;
            case "Burton":
                image = R.drawable.burton_logo;
                break;
            case "Topshop":
                image = R.drawable.topshop_logo;
                break;
            case "Oasis":
                image = R.drawable.oasis_logo;
                break;
            case "Miss Selfridge":
                image = R.drawable.miss_selfridge_logo;
                break;
            case "USC":
                image = R.drawable.usc_logo;
                break;
            case "Schuh":
                image = R.drawable.schuh_logo;
                break;
            case "Select":
                image = R.drawable.select_logo;
                break;
            case "FootAsylum":
                image = R.drawable.footasylum_logo;
                break;
        }

        Glide.with(c).load(image).into(holder.image_discount);
    }

    @Override
    public int getItemCount() {
        Log.v(DiscountRecycleAdapter.class.getSimpleName(),""+listNewDiscounts.size());
        return listNewDiscounts.size();
    }


    /**
     * ViewHolder class
     */
    public class DiscountsViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewClientName;
        public AppCompatTextView textViewDescription;
        public ImageView image_discount;

        public DiscountsViewHolder(View view) {
            super(view);
            textViewClientName = view.findViewById(R.id.textViewClientName);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            image_discount = view.findViewById(R.id.image_discount);



            moredetails_button = view.findViewById(R.id.moredetails);

            moredetails_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);

                    String client = listNewDiscounts.get(getPosition()).getClientName();
                    String code = listNewDiscounts.get(getPosition()).getDiscountCode();
                    // set dialog title
                    alertDialogBuilder.setTitle(client);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(code)
                            .setCancelable(false)
                            .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.dismiss();}
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });
        }
    }


}