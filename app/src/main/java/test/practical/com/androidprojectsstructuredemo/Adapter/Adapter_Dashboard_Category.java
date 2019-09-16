package test.practical.com.androidprojectsstructuredemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.practical.com.androidprojectsstructuredemo.R;

public class Adapter_Dashboard_Category extends RecyclerView.Adapter<Adapter_Dashboard_Category.FeesViewHolder> {

    Context context;
    List<String> arrayList = new ArrayList<>();
    int[] images = {R.drawable.dummy_tv, R.drawable.dummy_android, R.drawable.dummy_iphone,
            R.drawable.dummy_ac, R.drawable.dummy_washing_machine, R.drawable.dummy_refrigerator, R.drawable.dummy_tv, R.drawable.dummy_android, R.drawable.dummy_iphone};

    public Adapter_Dashboard_Category(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.raw_dashboard_category, parent, false);
        return new FeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeesViewHolder holder, int position) {

        holder.imgCategory.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class FeesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_category)
        ImageView imgCategory;
        @BindView(R.id.txt_category_name)
        TextView txtCategoryName;
        @BindView(R.id.txt_category_price)
        TextView txtCategoryPrice;

        public FeesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
