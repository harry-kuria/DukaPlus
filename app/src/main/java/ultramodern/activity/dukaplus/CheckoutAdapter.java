package ultramodern.activity.dukaplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutHolder> {

    private Context context;

    private ArrayList<CheckoutModel> list;

    public CheckoutAdapter(Context context, ArrayList<CheckoutModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CheckoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_layout,parent,false);
        return new CheckoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutHolder holder, int position) {
        holder.setDetails(this.list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CheckoutHolder extends RecyclerView.ViewHolder {
        public TextView code,item,quantity,unit_price, total_price;
        public CheckoutHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.qr_code);
            item=itemView.findViewById(R.id.item);
            quantity=itemView.findViewById(R.id.quantity);
            unit_price=itemView.findViewById(R.id.unit_price);
            total_price=itemView.findViewById(R.id.total_price);
        }
        public void setDetails(CheckoutModel model){
            code.setText(model.getCode());
            item.setText(model.getItem());
            quantity.setText(model.getQuantity());
            unit_price.setText(model.getUnit_price());
            total_price.setText(model.getTotal_price());
        }
    }
}
