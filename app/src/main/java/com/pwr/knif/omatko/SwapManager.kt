package com.pwr.knif.omatko

//import android.app.Activity
//import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment

/**
 * Created by jakub on 15.11.2017.
 */
class SwapManager(val activity: AppCompatActivity) {

    fun changeFragments(fragment: Fragment, shouldAddToStack: Boolean) {
        with(activity.supportFragmentManager.beginTransaction()) {
            replace(R.id.content_main, fragment)
            if(shouldAddToStack)
                addToBackStack(null)
            commit()
        }
    }
}