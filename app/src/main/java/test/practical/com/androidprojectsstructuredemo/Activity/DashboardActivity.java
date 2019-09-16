package test.practical.com.androidprojectsstructuredemo.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.practical.com.androidprojectsstructuredemo.Adapter.Adapter_Dashboard_Category;
import test.practical.com.androidprojectsstructuredemo.R;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.img_dashboard_menu)
    ImageView imgDashboardMenu;
    @BindView(R.id.img_dashboard_search)
    ImageView imgDashboardSearch;
    @BindView(R.id.recyclerCategories)
    RecyclerView recyclerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        this.setMemoryAllocation();
        this.setListeners();
    }

    private void setListeners() {

    }

    private void setMemoryAllocation() {

        Adapter_Dashboard_Category adt = new Adapter_Dashboard_Category(DashboardActivity.this);
        recyclerCategories.setFocusable(false);
        recyclerCategories.setNestedScrollingEnabled(false);
        @SuppressLint("WrongConstant") GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerCategories.setLayoutManager(layoutManager);
        recyclerCategories.setAdapter(adt);
    }
}
