@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.tolk_to_my.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.allyants.notifyme.NotifyMe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.hawk.Hawk
import com.tapadoo.alerter.Alerter
import com.tolk_to_my.R
import com.tolk_to_my.controller.activities.SplashActivity
import com.tolk_to_my.notifications.model.Notification
import com.tolk_to_my.notifications.network.ApiRequests
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Constants {

    const val SERVER_KEY =
        "AAAAiqQIIM4:APA91bH01z5ium3Xe62U2xEj7sBKXmUiWEJ8qO5bcqg1wL1SqR0oHngBVH3g78RAt7LJR86_R-Jgek-F1yQ3eI3fl5bCW4lHdkz01VkzDNVxuZk0ParkY_Wncht9vFpSce6DTUrog6g_"

    const val TYPE_TITLE = "type_title"
    const val TYPE_ID = "type_id"
    const val TYPE_MODEL = "type_model"

    const val IS_LOGIN = "is_login"
    const val USER = "user"
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
    fun notifyMe(context: Context, title: String, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val localDate = LocalDateTime.now()
                val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss")
//                val dateString = dtFormatter.format(localDate.plusMonths(6))
                val dateString = dtFormatter.format(localDate)
                val format: DateFormat = SimpleDateFormat("yyyy-MMM-dd hh:mm:ss", Locale.ENGLISH)
                val date = format.parse(dateString)
                NotifyMe.Builder(context)
                    .title(title)
                    .content(content)
                    .time(date)
                    .addAction(
                        Intent(),
                        "الغاء",
                        true,
                        false
                    )
//                    .rrule("FREQ=MONTHLY;INTERVAL=5;COUNT=1")
//                    .rrule("FREQ=DAILY;INTERVAL=5;COUNT=1")
//                    .rrule("FREQ=MINUTELY;INTERVAL=1;COUNT=50")
                    .rrule("FREQ=SECONDLY;INTERVAL=10;COUNT=50")
                    .build()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun logout(context: Context) {
        Hawk.deleteAll()
        unsubscribeFromTopic(Hawk.get(USER_TOKEN, ""))
        FirebaseAuth.getInstance().signOut()
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

    @JvmStatic
    fun showAlert(
        activity: Activity,
        text: String?,
        @ColorRes color: Int?,
    ) {
        Alerter.create(activity)
            .setTitle("")
            .setText(text!!)
            .setBackgroundColorRes(color!!)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .hideIcon()
            .show()
    }

    @JvmStatic
    fun sendNotifications(context: Context, title: String?, message: String?, token: String) {
        val data = Notification(title, message)
        ApiRequests().sendNotification(
            context,
            data,
            "/topics/$token"
        ) { response: Notification? ->
            run {
                Log.e("response", "response = " + response.toString())
            }
        }
    }

    @JvmStatic
    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    @JvmStatic
    fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }
}