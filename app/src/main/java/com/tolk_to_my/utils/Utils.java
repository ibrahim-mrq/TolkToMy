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

    public void openPhoneCall(Activity context) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0592440530"));
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("response", "ERROR WHATSAPP = " + e.toString());
        }
    }

    public void openPhoneMessage(Activity context) {
        try {
            String number = "0592440530";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        } catch (Exception e) {
            Log.e("response", "ERROR WHATSAPP = " + e.toString());
        }
    }
}
