package com.pwr.knif.omatko

import android.app.Activity
import android.app.Fragment

/**
 * Created by jakub on 15.11.2017.
 */
class SwapManager(val activity: Activity) {

    fun changeFragments(fragment: Fragment, shouldAddToStack: Boolean) {
        activity.fragmentManager.beginTransaction().apply {
            //TODO 1: change id to something meaningful
            replace(-1, fragment)
            if(shouldAddToStack)
                addToBackStack(null)
            commit()
        }
    }
}