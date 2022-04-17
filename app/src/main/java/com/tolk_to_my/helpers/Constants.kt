@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.tolk_to_my.helpers

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.tolk_to_my.BuildConfig
import com.tolk_to_my.R
import com.tolk_to_my.controller.activities.SplashActivity
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val APP_URL =
        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID

    const val TYPE = "type"
    const val TYPE_TITLE = "type_title"
    const val TYPE_ID = "type_id"
    const val TYPE_MODEL = "type_model"

    const val IS_FIRST_LOGIN = "is_first_login"
    const val IS_LOGIN = "is_login"
    const val USER = "user"
    const val USER_ID = "user_id"
    const val USER_TOKEN = "user_token"
    const val USER_TYPE = "user_type"
    const val TYPE_CUSTOMER = "customer"
    const val TYPE_VENDOR = "vendor"

    const val TYPE_LANGUAGE = "type_language"
    const val TYPE_LANGUAGE_AR = "ar"
    const val TYPE_LANGUAGE_EN = "en"

    @JvmStatic
    fun getDate(): String? {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.US)
        return sdf.format(Date())
    }

    @JvmStatic
    fun logout(context: Context) {
        Hawk.deleteAll()
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, SplashActivity::class.java))
    }


    @JvmStatic
    fun setText(
        context: Context,
        @StringRes string: Int,
        text: String
    ): String {
        return context.getString(string) + context.getString(R.string.colon) + " " + text
    }

}