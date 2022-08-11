package ultramodern.activity.dukaplus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.firebase.database.core.Context;

public class POS_Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_s_);

        Window window = POS_Activity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(POS_Activity.this,R.color.colorPrimaryDark));
    }
    public void nextActivity(View view){
        Intent intent = new Intent(POS_Activity.this, AddStockActivity.class);
        startActivity(intent);
    }
    public void TransactionHistoryActivity(View view){
        Intent intent = new Intent(POS_Activity.this,TransactionHistory.class);
        startActivity(intent);
    }
    public void checkOutItemsButton(View view){
        Intent intent = new Intent(POS_Activity.this,CheckOutActivity.class);
        startActivity(intent);
    }
}