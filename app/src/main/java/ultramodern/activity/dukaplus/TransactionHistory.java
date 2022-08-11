package ultramodern.activity.dukaplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionHistory extends AppCompatActivity {

    public RecyclerView list;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        list = findViewById(R.id.transactionList);

        Window window = TransactionHistory.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(TransactionHistory.this,R.color.colorPrimaryDark));

        list.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<TransactionHistoryModel> modelArrayList = new ArrayList<>();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String user = firebaseUser.getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user).child("Transaction History");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String code = String.valueOf(dataSnapshot.child("Code").getValue());
                    String item = String.valueOf(dataSnapshot.child("Name").getValue());
                    String quantity = String.valueOf(dataSnapshot.child("Quantity").getValue());
                    String up = String.valueOf(dataSnapshot.child("Unit price").getValue());
                    String date = String.valueOf(dataSnapshot.child("Date").getValue());
                    String description = String.valueOf(dataSnapshot.child("Description").getValue());
                    TransactionHistoryModel model = new TransactionHistoryModel(code,item,quantity,up,date,description);
                    Log.d("TAG",code);
                    Log.d("TAG",item);
                    Log.d("TAG",quantity);
                    Log.d("TAG",up);
                    modelArrayList.add(model);


                }


                TransactionHistoryAdapter recyclerAdapter = new TransactionHistoryAdapter( TransactionHistory.this,modelArrayList);
                recyclerAdapter.notifyDataSetChanged();
                list.setAdapter(recyclerAdapter);

                if (modelArrayList.isEmpty()){
                    list.setVisibility(View.GONE);
                    //emptyView.setVisibility(View.VISIBLE);
                    //emptyView_image.setVisibility(View.VISIBLE);
                }
                else{
                    list.setVisibility(View.VISIBLE);
                    //emptyView.setVisibility(View.GONE);
                    //emptyView_image.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}