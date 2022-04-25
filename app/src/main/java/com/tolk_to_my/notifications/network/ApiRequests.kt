package com.tolk_to_my.notifications.network

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.tolk_to_my.notifications.model.Notification
import com.tolk_to_my.notifications.model.PushNotification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

class ApiRequests {

    private val service: RetrofitService = RetrofitClient.makeRetrofitService()

    fun sendNotification(
        context: Context,
        data: Notification,
        token: String,
        consumer: Consumer<Notification?>
    ) {
        val sender = PushNotification(data, token)
        service.sendNotification(sender)!!.enqueue(object : Callback<Notification?> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<Notification?>,
                response: Response<Notification?>
            ) {
                Log.e("response", "data = $data")
                Log.e("response", "sender = $sender")
                Log.e("response", "response = $response")
                if (response.isSuccessful) {
                    consumer.accept(response.body())
                }
            }

            override fun onFailure(call: Call<Notification?>, t: Throwable) {
                Toast.makeText(context, "error: " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}