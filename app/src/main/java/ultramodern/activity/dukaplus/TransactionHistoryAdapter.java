package ultramodern.activity.dukaplus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryHolder> {

    public Context context;
    public ArrayList<TransactionHistoryModel> list;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionHistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_layout,parent,false);
        return new TransactionHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryHolder holder, int position) {
        holder.setDetails(this.list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TransactionHistoryHolder extends RecyclerView.ViewHolder {
        public TextView code,item,quantity,unit_price,date, description;
        public TransactionHistoryHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.codeinput);
            item=itemView.findViewById(R.id.nameInput_history);
            quantity=itemView.findViewById(R.id.quantity_input_history);
            unit_price=itemView.findViewById(R.id.up_input);
            date=itemView.findViewById(R.id.dateInput);
            description=itemView.findViewById(R.id.descriptionTitle);
        }
        public void setDetails(TransactionHistoryModel model){
            code.setText(model.getCode());
            item.setText(model.getItem());
            quantity.setText(model.getQuantity());
            unit_price.setText(model.getUnit_price());
            date.setText(model.getDate());
            description.setText(model.getDescription());
        }
    }
}
