package test.practical.com.androidprojectsstructuredemo.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import test.practical.com.androidprojectsstructuredemo.Adapter.Adapter_Dashboard_Category;
import test.practical.com.androidprojectsstructuredemo.Bean.BeanCatagory;
import test.practical.com.androidprojectsstructuredemo.R;
import test.practical.com.androidprojectsstructuredemo.Utils.CommonUtils;
import test.practical.com.androidprojectsstructuredemo.Utils.CustomLoader;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.img_dashboard_menu)
    ImageView imgDashboardMenu;
    @BindView(R.id.img_dashboard_Settings)
    ImageView imgDashboardSettings;
    @BindView(R.id.recyclerCategories)
    RecyclerView recyclerCategories;
    Dialog dialog;
    LinearLayout btnSubmit;
    EditText edtPassword;
    String mainPassword;

    List<BeanCatagory> list =new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("password");
    FirebaseDatabase Catagorydatabase = FirebaseDatabase.getInstance();
    DatabaseReference CatRef = Catagorydatabase.getReference("BeanCatagory");
//    DatabaseReference catRef = database.getReference("BeanCatagory");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);


        this.setMemoryAllocation();
        this.setListeners();
    }

    private void setListeners() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtPassword.getText().toString().isEmpty()){
                    if (mainPassword.equalsIgnoreCase(edtPassword.getText().toString())){
                        Toast.makeText(DashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        edtPassword.setError("Incorrect Password");
                        edtPassword.requestFocus();
                    }
                }

            }
        });

        imgDashboardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isNetworkAvailable(getApplicationContext())) {
                    CommonUtils.showSnackBar(DashboardActivity.this, getResources()
                            .getString(R.string.no_network_available));
                } else {
                    dialog.show();
                }

            }
        });
    }

    private void setMemoryAllocation() {
        dialog = new Dialog(DashboardActivity.this);

        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimationfrombootom;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alert);

        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        btnSubmit= dialog.findViewById(R.id.submit);
        edtPassword = (EditText) dialog.findViewById(R.id.setting_password);


        if (!CommonUtils.isNetworkAvailable(getApplicationContext())) {
            CommonUtils.showSnackBar(DashboardActivity.this, getResources()
                    .getString(R.string.no_network_available));
        } else {
            getCatagories();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mainPassword = dataSnapshot.child("password").getValue().toString();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }

    }

    private void getCatagories(){

        CustomLoader customLoader = new CustomLoader(DashboardActivity.this);
        customLoader.setMessage("Loading..");
        customLoader.startAnim();
        CatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    BeanCatagory beanCatagory = postSnapshot.getValue(BeanCatagory.class);
                    //adding artist to the list
                    list.add(beanCatagory);
                }
                Log.e("log", list.size() + "");

                Adapter_Dashboard_Category adt = new Adapter_Dashboard_Category(DashboardActivity.this,list);
                recyclerCategories.setFocusable(false);
                recyclerCategories.setNestedScrollingEnabled(false);
                @SuppressLint("WrongConstant") GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerCategories.setLayoutManager(layoutManager);
                recyclerCategories.setAdapter(adt);

                if (customLoader != null)
                    customLoader.stopAnim();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (customLoader != null)
                    customLoader.stopAnim();
            }
        });


    }
}
