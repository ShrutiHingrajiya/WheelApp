package test.practical.com.androidprojectsstructuredemo.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import test.practical.com.androidprojectsstructuredemo.R;

public class CustomLoader {

    Dialog dialog;
    TextView tv_msgTxt;
    private AVLoadingIndicatorView avi;

    public CustomLoader(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_loader);
        tv_msgTxt = (TextView) dialog.findViewById(R.id.tv_loader_msg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //  dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
    }

    public void setMessage(String text) {
        tv_msgTxt.setText(text);

    }


  /*  public void showLoader(){
        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismiss(){
        if(dialog != null) dialog.dismiss();
    }*/

    public void startAnim() {

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
//            avi.show();
            avi.smoothToShow();
        }

    }

    public void stopAnim() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();

//            avi.hide();
            avi.smoothToHide();
        }

    }

}
