package ultramodern.activity.dukaplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import ultramodern.activity.dukaplus.databinding.ActivityRegistrationBinding;
@SuppressWarnings("ALL")
public class Registration extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PHOT0 = 100;
    private IntentFilter intentFilter;
    CircleImageView profile_pic;

    ActivityRegistrationBinding binding;
    private CardView reg_button;

    public static final String BroadCastStringForAction = "CheckInternet";
    public Registration() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //checking for quick response
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

        binding.notConnected.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())){
            set_Visibility_ON();
        }
        else {
            set_Visibility_OFF();
        }



        profile_pic=findViewById(R.id.profile_pic);
        reg_button=findViewById(R.id.registerbutton);
        profile_pic.setOnClickListener(this);
        reg_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==reg_button){
            EditText name = findViewById(R.id.name);
            EditText email = findViewById(R.id.email);
            EditText contact = findViewById(R.id.contact);
            EditText id_no = findViewById(R.id.identity_number);
            EditText shopName = findViewById(R.id.shopName);
            if (name.getText().length()==0 || email.getText().length()==0 || contact.getText().length()==0 || id_no.getText().length()==0 || shopName.getText().length()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                builder.setMessage("All the Fields are required");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                String nameData = name.getText().toString();
                String emailData = email.getText().toString();
                String contactData = contact.getText().toString();
                String idNumber = id_no.getText().toString();
                String shopNameData = shopName.getText().toString();

                DatabaseActivities databaseActivities = new DatabaseActivities();
                databaseActivities.AddingRegistrationDetailsToDatabase(Registration.this,nameData,emailData,contactData,idNumber,shopNameData);

                Intent intent = new Intent(getApplicationContext(),Location.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }



        }
        if (view==profile_pic){
            openCamera();
        }
    }

    public void openCamera() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOT0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode==SELECT_PHOT0){
                Uri uri=data.getData();
                Glide.with(Registration.this).load(uri).into(profile_pic);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver,intentFilter);
        FirebaseAuth user = FirebaseAuth.getInstance();
        if (user.getCurrentUser() !=null){
            Intent intent = new Intent(Registration.this,POS_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else{
            Log.d("TRACK","onAuthStateChanged:signed_out");
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver,intentFilter);
    }
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastStringForAction)){
                if (intent.getStringExtra("online_status").equals("true")){
                    set_Visibility_ON();
                }
                else {
                    set_Visibility_OFF();
                }
            }
        }
    };

    public boolean isOnline(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }

    public void set_Visibility_ON(){
        binding.notConnected.setVisibility(View.GONE);
        binding.contact.setVisibility(View.VISIBLE);
        binding.email.setVisibility(View.VISIBLE);
        binding.identityNumber.setVisibility(View.VISIBLE);
        binding.name.setVisibility(View.VISIBLE);
        binding.parent.setVisibility(View.VISIBLE);
        binding.regDetails.setVisibility(View.VISIBLE);
        binding.registerbutton.setVisibility(View.VISIBLE);
        binding.shopName.setVisibility(View.VISIBLE);
        binding.textView2.setVisibility(View.VISIBLE);
        binding.toolbar.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Integer.parseInt("#6200EE"));

    }

    public void set_Visibility_OFF(){
        binding.notConnected.setVisibility(View.VISIBLE);
        binding.contact.setVisibility(View.GONE);
        binding.email.setVisibility(View.GONE);
        binding.identityNumber.setVisibility(View.GONE);
        binding.name.setVisibility(View.GONE);
        //binding.parent.setVisibility(View.GONE);
        binding.regDetails.setVisibility(View.GONE);
        binding.registerbutton.setVisibility(View.GONE);
        binding.shopName.setVisibility(View.GONE);
        binding.textView2.setVisibility(View.GONE);
        binding.toolbar.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }
}