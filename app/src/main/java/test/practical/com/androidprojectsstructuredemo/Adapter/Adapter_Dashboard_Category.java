package test.practical.com.androidprojectsstructuredemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.practical.com.androidprojectsstructuredemo.Activity.ProductDetailsActivity;
import test.practical.com.androidprojectsstructuredemo.Bean.BeanCatagory;
import test.practical.com.androidprojectsstructuredemo.R;

public class Adapter_Dashboard_Category extends RecyclerView.Adapter<Adapter_Dashboard_Category.FeesViewHolder> {

    Context context;
    List<BeanCatagory> arrayList = new ArrayList<>();
    int[] images = {R.drawable.dummy_tv, R.drawable.dummy_android, R.drawable.dummy_iphone,
            R.drawable.dummy_ac, R.drawable.dummy_washing_machine, R.drawable.dummy_refrigerator, R.drawable.dummy_tv, R.drawable.dummy_android, R.drawable.dummy_iphone};


    public Adapter_Dashboard_Category(Context context, List<BeanCatagory> list) {
        this.context = context;
        this.arrayList = list;
    }

    @NonNull
    @Override
    public FeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.raw_dashboard_category, parent, false);
        return new FeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeesViewHolder holder, int position) {

        holder.txtCategoryName.setText(arrayList.get(position).getName());

        if (arrayList.get(position).getToDisplay().equalsIgnoreCase("no"))
        {
            holder.cvCatagory.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(arrayList.get(position).getUrl())
                .placeholder(R.drawable.dummy_tv) //placeholder while loading image
                .into(holder.imgCategory);

        holder.linearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FeesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_category)
        ImageView imgCategory;
        @BindView(R.id.txt_category_name)
        TextView txtCategoryName;
        @BindView(R.id.txt_category_price)
        TextView txtCategoryPrice;
        @BindView(R.id.linear_item)
        LinearLayout linearItem;
        @BindView(R.id.cv_catagory)
        CardView cvCatagory;

        public FeesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
