package com.tolk_to_my.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tolk_to_my.R;
import com.tolk_to_my.helpers.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public String getKeyHash(Activity context) {
        String keyHash = "";
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(),
                            PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
            keyHash = "Exception";
        }
        return keyHash;
    }

    public void openWhatsApp(Activity context) {
        try {
            String message = "https://api.whatsapp.com/send?phone=+972592440530&text=Hello";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(message));
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("response", "ERROR WHATSAPP = " + e.toString());
        }
    }

    public void openGoogleDuo(Activity context, String phone) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.google.android.apps.tachyon");
            intent.setAction("com.google.android.apps.tachyon.action.CALL");
            intent.setData(Uri.parse("tel:+966" + phone));
            context.startActivity(intent);
        } catch (Exception e) {
            Constants.showAlert(context, "الرجاء تثبيت تطبيق جوجل دو اولا", R.color.orange);
            Log.e("response", "ERROR Google Duo = " + e.toString());
        }
    }

    public void openPhoneCall(Activity context) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0592440530"));
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("response", "ERROR Phone Call = " + e.toString());
        }
    }

    public void openPhoneMessage(Activity context) {
        try {
            String number = "0592440530";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        } catch (Exception e) {
            Log.e("response", "ERROR Phone Message = " + e.toString());
        }
    }

    @SuppressLint("SetTextI18n")
    private void toast(Activity context) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, context.findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_logout);
        TextView text = layout.findViewById(R.id.text);
        text.setText("Hello! This is a custom toast!");

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.START, 10, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
