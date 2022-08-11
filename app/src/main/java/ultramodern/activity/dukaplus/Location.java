package ultramodern.activity.dukaplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import ultramodern.activity.dukaplus.databinding.ActivityLocationBinding;

@SuppressWarnings("ALL")
public class Location extends AppCompatActivity implements OnMapReadyCallback {

    private IntentFilter intentFilter;

    ActivityLocationBinding binding;
    public static final String BroadCastStringForAction = "CheckInternet";

    GoogleMap map;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    SearchView sv_location;
    CardView saveLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sv_location=findViewById(R.id.sv_location);
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);

        //binding.notConnected.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())) {
            set_Visibility_ON();
        } else {
            set_Visibility_OFF();
        }


        //searchView=findViewById(R.id.sv_location);
        //saveLocationButton = findViewById(R.id.save_shop_location);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            //request permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        sv_location.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = String.valueOf(sv_location.getQuery());
                List<Address> addressList = new List<Address>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean contains(@Nullable Object o) {
                        return false;
                    }

                    @NonNull
                    @Override
                    public Iterator<Address> iterator() {
                        return null;
                    }

                    @NonNull
                    @Override
                    public Object[] toArray() {
                        return new Object[0];
                    }

                    @NonNull
                    @Override
                    public <T> T[] toArray(@NonNull T[] ts) {
                        return null;
                    }

                    @Override
                    public boolean add(Address address) {
                        return false;
                    }

                    @Override
                    public boolean remove(@Nullable Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsAll(@NonNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public boolean addAll(@NonNull Collection<? extends Address> collection) {
                        return false;
                    }

                    @Override
                    public boolean addAll(int i, @NonNull Collection<? extends Address> collection) {
                        return false;
                    }

                    @Override
                    public boolean removeAll(@NonNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public boolean retainAll(@NonNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Address get(int i) {
                        return null;
                    }

                    @Override
                    public Address set(int i, Address address) {
                        return null;
                    }

                    @Override
                    public void add(int i, Address address) {

                    }

                    @Override
                    public Address remove(int i) {
                        return null;
                    }

                    @Override
                    public int indexOf(@Nullable Object o) {
                        return 0;
                    }

                    @Override
                    public int lastIndexOf(@Nullable Object o) {
                        return 0;
                    }

                    @NonNull
                    @Override
                    public ListIterator<Address> listIterator() {
                        return null;
                    }

                    @NonNull
                    @Override
                    public ListIterator<Address> listIterator(int i) {
                        return null;
                    }

                    @NonNull
                    @Override
                    public List<Address> subList(int i, int i1) {
                        return null;
                    }
                };

                if (location != null || location.equals("")) {
                    Geocoder geocoder = new Geocoder(Location.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        supportMapFragment.getMapAsync(this);
        binding.saveShopLocation.setVisibility(View.VISIBLE);
        binding.saveShopLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                String user_id = user.getUid();



                Intent intent = new Intent(Location.this, ShopDescription.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<android.location.Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(final android.location.Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here");
                            //zooming map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //add marker on map
                            googleMap.addMarker(markerOptions);

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver, intentFilter);
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
//        binding.notConnected.setVisibility(View.GONE);
        Toast.makeText(this, " Internet Connection Available", Toast.LENGTH_SHORT).show();

        binding.saveShopLocation.setVisibility(View.VISIBLE);
        binding.svLocation.setVisibility(View.VISIBLE);
  //      binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
}