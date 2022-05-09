package com.webdoc.Essentials

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.webdoc.theforum.R

class Utils {

    var alertDialog: AlertDialog? = null
    var isShowingCustomProgress = false

    // Define the code block to be executed
    //    public Runnable runnable = new Runnable() {
    //        @Override
    //        public void run() {
    //            // Insert custom code here
    //            easyFlipView.flipTheView();
    //            // Repeat every 2 seconds
    //            handler.postDelayed(runnable, 500);  /*1000*/
    //        }
    //    };
    //    public void showCustomLoadingDialog(Activity activity) {
    //        handler = new Handler();
    //        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
    //        View v = activity.getLayoutInflater().inflate(R.layout.loading_screen, null);
    //        dialogBuilder.setView(v);
    //
    //        alertDialog = dialogBuilder.create();
    //        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //        easyFlipView = (EasyFlipView) v.findViewById(R.id.easyFlipView);
    //        alertDialog.setCancelable(false);
    //        alertDialog.show();
    //        isShowingCustomProgress = true;
    //        // Start the Runnable immediately
    //        handler.post(runnable);
    //    }//alert
    //    public void hideCustomLoadingDialog() {
    //        handler.removeCallbacks(runnable);
    //        alertDialog.dismiss();
    //        isShowingCustomProgress = false;
    //    }
    fun showCustomLoadingDialog(activity: Activity) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val v: View = activity.layoutInflater.inflate(R.layout.progressbar, null)
        dialogBuilder.setView(v)
        alertDialog = dialogBuilder.create()
        alertDialog!!.getWindow()!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
        isShowingCustomProgress = true
    }

    fun hideCustomLoadingDialog() {
        alertDialog!!.dismiss()
        isShowingCustomProgress = false
    }
}