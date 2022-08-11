package ultramodern.activity.dukaplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private ArrayList<RecyclerModel> list;

    public StockAdapter(ArrayList<RecyclerModel> listItems) {
        this.list = listItems;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_stock_layout,parent,false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockViewHolder holder, int position) {
        //list=new ArrayList<>();
        holder.code.setText(list.get(position).getCode());
        holder.unit_price.setText(list.get(position).getUnit_price());
        holder.quantity.setText(list.get(position).getQuantity());
        holder.stock_item.setText(list.get(position).getItem());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String user = firebaseUser.getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user).child("Stock");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        public TextView code,stock_item,quantity,unit_price;
        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.stock_qr_code);
            stock_item = itemView.findViewById(R.id.stock_item);
            quantity = itemView.findViewById(R.id.stock_quantity);
            unit_price = itemView.findViewById(R.id.stock_unit_price);
        }
        public void setDetails(RecyclerModel model){
            code.setText(model.getCode());
            stock_item.setText(model.getItem());
            quantity.setText(model.getQuantity());
            unit_price.setText(model.getUnit_price());
        }
    }

}

