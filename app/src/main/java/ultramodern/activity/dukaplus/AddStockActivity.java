package ultramodern.activity.dukaplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddStockActivity extends AppCompatActivity {

    RecyclerView list;
    Button add_stock_button,add_to_stock,scan_again;
    TextView emptyView;
    ImageView emptyView_image;
    CardView addItemOptions;
    EditText code_input, name_input,quantity_input,unitprice_input;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        list=findViewById(R.id.recyclerview);

        emptyView=findViewById(R.id.emptyView);
        emptyView_image=findViewById(R.id.emptyView_image);

        scan_again=findViewById(R.id.scan_again);
        name_input=findViewById(R.id.name_input);
        quantity_input=findViewById(R.id.quantity_input);
        unitprice_input=findViewById(R.id.unitprice_input);
        code_input=findViewById(R.id.code_input);
        addItemOptions=findViewById(R.id.add_item_options);
        Window window = AddStockActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(AddStockActivity.this,R.color.colorPrimaryDark));

        add_to_stock=findViewById(R.id.add_to_stock);
        scan_again.setBackgroundTintList(null);
        add_to_stock.setBackgroundTintList(null);
        add_stock_button=findViewById(R.id.add_stock_button);
        add_stock_button.setBackgroundTintList(null);
        list.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<RecyclerModel> modelArrayList = new ArrayList<>();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String user = firebaseUser.getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user).child("Stock");
        //Query query = reference.orderByChild("UserID").equalTo(user);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String code = String.valueOf(dataSnapshot.child("Code").getValue());
                    String item = String.valueOf(dataSnapshot.child("Item name").getValue());
                    String quantity = String.valueOf(dataSnapshot.child("Quantity").getValue());
                    String up = String.valueOf(dataSnapshot.child("Unit price").getValue());
                    RecyclerModel model = new RecyclerModel(code,item,quantity,up,"0");
                    Log.d("TAG",code);
                    Log.d("TAG",item);
                    Log.d("TAG",quantity);
                    Log.d("TAG",up);
                    modelArrayList.add(model);


                }


                StockAdapter recyclerAdapter = new StockAdapter( modelArrayList);
                recyclerAdapter.notifyDataSetChanged();
                list.setAdapter(recyclerAdapter);

                if (modelArrayList.isEmpty()){
                    list.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView_image.setVisibility(View.VISIBLE);
                }
                else{
                    list.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    emptyView_image.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }
    public void add_stock_button_Activity(View view){
        Scanner scanner = new Scanner();
        scanner.ScanCode(AddStockActivity.this);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null){
            if (result.getContents() !=null){
                addItemOptions.setVisibility(View.VISIBLE);
                code_input.setText(result.getContents());

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void add_stock(){
        String code = String.valueOf(code_input.getText());
        String name = String.valueOf(name_input.getText());
        String quantity = String.valueOf(quantity_input.getText());
        String unitPrice = String.valueOf(unitprice_input.getText());
        DatabaseActivities databaseActivities = new DatabaseActivities();
        databaseActivities.addingStockToDatabase(code,name,quantity,unitPrice);
        Toast.makeText(AddStockActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();

    }
    public void setAdd_stock_button(View view){
        add_stock();
        addItemOptions.setVisibility(View.GONE);
    }
    public void setScan_again(View view){
        Scanner scanner = new Scanner();
        scanner.ScanCode(AddStockActivity.this);
    }


}