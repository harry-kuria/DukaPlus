package ultramodern.activity.dukaplus;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    private RecyclerAdapter recyclerAdapter;
    private ArrayList<RecyclerModel> modelArrayList;
    //private ArrayList<String> sum_of_values;
    RecyclerView checkout_list;
    CardView addItemOptions;
    ImageView emptyViewImage;
    TextView emptyViewText;
    RelativeLayout host;
    Button check_out_items_button,add_to_stock_checkout,scan_again_checkout,sum;
    EditText totalprice_input_checkout,unitprice_input_checkout,quantity_input_checkout,name_input_checkout,code_input_checkout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Window window = CheckOutActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CheckOutActivity.this,R.color.colorPrimaryDark));

        sum=findViewById(R.id.sum);
        //host=findViewById(R.id.host);
        emptyViewImage=findViewById(R.id.emptyView_image_checkout);
        emptyViewText=findViewById(R.id.emptyView_checkout);
        add_to_stock_checkout=findViewById(R.id.add_to_stock_checkout);
        scan_again_checkout=findViewById(R.id.scan_again_checkout);
        check_out_items_button=findViewById(R.id.checkout_items_button);

        sum.setBackgroundTintList(null);
        add_to_stock_checkout.setBackgroundTintList(null);
        scan_again_checkout.setBackgroundTintList(null);
        check_out_items_button.setBackgroundTintList(null);
        checkout_list=findViewById(R.id.checkout_list);
        addItemOptions=findViewById(R.id.add_item_options_checkout);
        checkout_list.setLayoutManager(new LinearLayoutManager(this));
        modelArrayList=new ArrayList<>();

        recyclerAdapter=new RecyclerAdapter(this,modelArrayList);
        checkout_list.setAdapter(recyclerAdapter);
        
        totalprice_input_checkout=findViewById(R.id.totalprice_input_checkout);
        unitprice_input_checkout=findViewById(R.id.unitprice_input_checkout);
        quantity_input_checkout=findViewById(R.id.quantity_input_checkout);
        name_input_checkout=findViewById(R.id.name_input_checkout);
        code_input_checkout=findViewById(R.id.code_input_checkout);



    }
    public void setCheckout_button(View view){
        Scanner scanner = new Scanner();
        scanner.ScanCode(CheckOutActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null){
            if (result.getContents() !=null){
                addItemOptions.setVisibility(View.VISIBLE);
                code_input_checkout.setText(result.getContents());
            }
            else {
                Toast.makeText(this, "No results", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void AddStock(View view){
        String code = String.valueOf(code_input_checkout.getText());
        String item = String.valueOf(name_input_checkout.getText());
        String quantity = String.valueOf(quantity_input_checkout.getText());
        String unit_Price = String.valueOf(unitprice_input_checkout.getText());
        String total = String.valueOf(totalprice_input_checkout.getText());
        RecyclerModel model = new RecyclerModel(code,item,quantity,unit_Price,total);
        modelArrayList.add(model);
        //sum_of_values = new ArrayList<>();
        //sum_of_values.add(total.toString());
        recyclerAdapter.notifyDataSetChanged();

        Toast.makeText(CheckOutActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
        Scanner scanner = new Scanner();
        scanner.ScanCode(CheckOutActivity.this);
    }
    public int sumTotal(){

        int sum=0;
        for (int i=0;i<modelArrayList.size();i++){

            //sum += modelArrayList.get(i).getTotal_price();

            Toast.makeText(CheckOutActivity.this, sum, Toast.LENGTH_SHORT).show();
        }
        return sum;
    }
    public void FinishAdding(View view){
        addItemOptions.setVisibility(View.GONE);
    }
    public void total(View view){
        sumTotal();
    }
}