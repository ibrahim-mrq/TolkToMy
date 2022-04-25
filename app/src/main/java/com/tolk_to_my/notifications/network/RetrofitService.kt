package com.tolk_to_my.notifications.network

import com.tolk_to_my.helpers.Constants
import com.tolk_to_my.notifications.model.Notification
import com.tolk_to_my.notifications.model.PushNotification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @Headers(
        "Content-Type:application/json",
        "Authorization: key=" + Constants.SERVER_KEY
    )
    @POST("fcm/send")
    fun sendNotification(@Body body: PushNotification?): Call<Notification?>?

}
