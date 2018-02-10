package com.pwr.knif.omatko

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


class SwapManager(private val activity: AppCompatActivity) {

    fun changeFragments(fragment: Fragment, shouldAddToStack: Boolean) {
        with(activity.supportFragmentManager.beginTransaction()) {
            replace(R.id.content_main, fragment)
            if (shouldAddToStack)
                addToBackStack(null)
            commit()
        }
    }

    fun getFacebookIntent(): Intent {
        return try {
            activity.applicationContext.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/269551433187258"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/omatkopwr"))
        }
    }

    fun getInstagramIntent(): Intent {
        return try {
            activity.applicationContext.packageManager.getPackageInfo("com.instagram.android", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/omatko.pwr"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/omatko.pwr"))
        }
    }

    fun getSnapchatIntent(): Intent {
        return try {
            activity.applicationContext.packageManager.getPackageInfo("com.snapchat.android", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("https://snapchat.com/add/omatkopwr"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://snapchat.com/add/omatkopwr"))
        }
    }
}