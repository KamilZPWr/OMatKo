package com.pwr.knif.omatko

import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment

class SwapManager(private val activity: AppCompatActivity) {

    fun changeFragments(fragment: Fragment, shouldAddToStack: Boolean) {
        with(activity.supportFragmentManager.beginTransaction()) {
            replace(R.id.content_main, fragment)
            if (shouldAddToStack)
                addToBackStack(null)
            commit()
        }
    }
}