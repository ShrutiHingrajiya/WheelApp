package test.practical.com.androidprojectsstructuredemo.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.practical.com.androidprojectsstructuredemo.Adapter.ProductViewPagerAdapter;
import test.practical.com.androidprojectsstructuredemo.R;

public class ProductDetailsActivity extends AppCompatActivity {

    @BindView(R.id.img_dashboard_menu)
    ImageView imgDashboardMenu;
    @BindView(R.id.view_pager_productdetails)
    ViewPager viewPagerProductdetails;
    @BindView(R.id.txt_category_name)
    TextView txtCategoryName;
    @BindView(R.id.dots_indicator)
    DotsIndicator dotsIndicator;
    @BindView(R.id.worm_dots_indicator)
    WormDotsIndicator wormDotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);


        ProductViewPagerAdapter adapter = new ProductViewPagerAdapter(getApplicationContext());
        viewPagerProductdetails.setAdapter(adapter);
        viewPagerProductdetails.setCurrentItem(0);
        dotsIndicator.setViewPager(viewPagerProductdetails);
        wormDotsIndicator.setViewPager(viewPagerProductdetails);
    }
}
