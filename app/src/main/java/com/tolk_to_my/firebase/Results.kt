package com.tolk_to_my.firebase

interface Results<T> {
    fun onSuccess(t: T)
    fun onFailureInternet(message: String)
    fun onEmpty()
}