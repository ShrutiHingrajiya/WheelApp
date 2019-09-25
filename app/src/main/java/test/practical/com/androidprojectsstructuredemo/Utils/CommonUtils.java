package test.practical.com.androidprojectsstructuredemo.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import test.practical.com.androidprojectsstructuredemo.Activity.DashboardActivity;
import test.practical.com.androidprojectsstructuredemo.R;

public class CommonUtils {

    public static void showToast(Context context, String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(Context ctx, View v, String text) {

        Snackbar snackbar;
        snackbar = Snackbar.make(v, text, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        //snackBarView.setBackgroundColor(color);
        snackbar.show();

    }

    public static void showSnackBar(Activity  ctx ,String text) {

        Snackbar s = Snackbar.make(ctx.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);
        View color = s.getView();
        color.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
        TextView tv = (TextView) color.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        s.show();

    }

    public static File createFileFromBitmap(Bitmap bitmap, String name, Context context) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Error", "Error writing bitmap", e);
        }

        return imageFile;
    }

    public static void setImage(Context ctx, final ImageView imageView, final ProgressBar progressBar, final String url) {


    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static void showLog(String TAG, String logvalue) {
        Log.e(TAG, logvalue);
    }

    private static void loadImagefromfilepath(Context context, ImageView imageView, final String path, String TAG) {
        Picasso.with(context)
                .load(Uri.fromFile(new File(path)))
                .fit()
                .centerInside()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        showLog(TAG, "Picasso Success Loading Thumbnail - " + path);
                    }

                    @Override
                    public void onError() {
                        showLog(TAG, "Picasso Error Loading Thumbnail - " + path);
                    }
                });
    }

    public static void loadimagefromURL(Context context, String Profilepic, ImageView imageView) {
        Picasso.with(context).load(Profilepic).fit()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }


    public static void grantallpermission(Activity activity) {

        new Permissive.Request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        showLog("Permission", permissions.length + "");
                        showToast(activity,"Granted");


                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {
                        showToast(activity,"Refused");

                    }
                })
                .execute(activity);
    }


    public static boolean isNetworkAvailable(@NotNull Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager == null)
            return false;

        // 3g-4g available
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        // wifi available
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);

        if (!is3g && !isWifi) {
            return false;
        } else
            return true;

    }


    public static void changeTextColor(TextView textView, int color){
        textView.setTextColor(color);
    }

    public static void changeLayoutColor(LinearLayout linearLayout, int color){
        linearLayout.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static void changeLayoutColorImage(ImageView linearLayout, int color){
        linearLayout.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }


    public static void showSnackBar(DashboardActivity dashboardActivity, String string) {
    }
}


