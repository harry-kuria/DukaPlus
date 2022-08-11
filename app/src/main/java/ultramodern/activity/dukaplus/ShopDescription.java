package ultramodern.activity.dukaplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopDescription extends AppCompatActivity implements View.OnClickListener {


    CardView continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_description);


        continueButton=findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {
        if (view==continueButton){
            Intent intent = new Intent(getApplicationContext(),POS_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}