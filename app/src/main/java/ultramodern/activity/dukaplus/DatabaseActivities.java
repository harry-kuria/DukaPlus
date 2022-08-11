package ultramodern.activity.dukaplus;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Context;

import java.util.Calendar;
import java.util.HashMap;

public class DatabaseActivities {

    public DatabaseActivities() {
    }

    public void AddingRegistrationDetailsToDatabase(final Context context, final String name, final String email, final String Contact, final String IdNo, final String shopName){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = mAuth.getCurrentUser();
        mAuth.createUserWithEmailAndPassword(email,IdNo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser fuser = mAuth.getCurrentUser();
                    String user = fuser.getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(user);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("Name", name);
                    hashMap.put("Email",email);
                    hashMap.put("Contact",Contact);
                    hashMap.put("Id Number",IdNo);
                    hashMap.put("Shop Name", shopName);

                    databaseReference.setValue(hashMap);
                    Toast.makeText(context, "Registration Successfull", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "An Error Occurred. Try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void addingStockToDatabase(String code,String name,String quantity,String unitPrice){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fuser = firebaseAuth.getCurrentUser();
        String user = fuser.getUid();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Code",code);
        hashMap.put("Item name",name);
        hashMap.put("Quantity",quantity);
        hashMap.put("Unit price",unitPrice);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(user);
        databaseReference.child("Stock").push().setValue(hashMap);
        add_to_history(user,code,name,quantity,unitPrice);
    }
    public void add_to_history(String user, String code,String name,String quantity, String up){
        Calendar calendar = Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        String date = day+"/"+month+"/"+year;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user).child("Transaction History");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Name", name);
        hashMap.put("Code", code);
        hashMap.put("Quantity", quantity);
        hashMap.put("Unit price", up);
        hashMap.put("Date",date);
        hashMap.put("Description","Added to Stock");
        reference.push().setValue(hashMap);
    }

}
