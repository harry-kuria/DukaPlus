package ultramodern.activity.dukaplus;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapterHolder> {

    private Context context;

    private List<RecyclerModel> list;

    public RecyclerAdapter(Context context, ArrayList<RecyclerModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TRACK","OnCreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_layout,parent,false);
        return new RecyclerAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterHolder holder, final int position) {
        Log.d("TRACK", "onBindViewHolder");
        //RecyclerModel recyclerModel = new RecyclerModel();
        holder.setDetails(this.list.get(position));
        //list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(view.getContext())
                .setMessage("Do you want to remove this item?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,list.size());
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
class RecyclerAdapterHolder extends RecyclerView.ViewHolder {
    public TextView code,item,quantity,unit_price, total_price;
    public RecyclerAdapterHolder(@NonNull View itemView) {
        super(itemView);
        code=itemView.findViewById(R.id.qr_code);
        item=itemView.findViewById(R.id.item);
        quantity=itemView.findViewById(R.id.quantity);
        unit_price=itemView.findViewById(R.id.unit_price);
        total_price=itemView.findViewById(R.id.total_price);
    }
    public void setDetails(RecyclerModel model){
        code.setText(model.getCode());
        item.setText(model.getItem());
        quantity.setText(model.getQuantity());
        unit_price.setText(model.getUnit_price());
        total_price.setText(model.getTotal_price());
    }
}