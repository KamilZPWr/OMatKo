package com.pwr.knif.omatko

import android.app.Activity
import android.app.Fragment

/**
 * Created by jakub on 15.11.2017.
 */
class SwapManager(val activity: Activity) {

    fun changeFragments(fragment: Fragment, shouldAddToStack: Boolean) {
        activity.fragmentManager.beginTransaction().apply {
            replace(R.id.content_main, fragment)
            if(shouldAddToStack)
                addToBackStack(null)
            commit()
        }
    }
}