package com.pwr.knif.omatko.notifications

import android.preference.PreferenceManager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {

    companion object {
        const val FIREBASE_TOKEN = "firebaseToken"
    }

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().putString(FIREBASE_TOKEN, refreshedToken).apply()
    }
}