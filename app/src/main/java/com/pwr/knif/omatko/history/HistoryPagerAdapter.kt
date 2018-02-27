package com.pwr.knif.omatko.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

class HistoryPagerAdapter(manager: android.support.v4.app.FragmentManager,
                          private val history: List<Edition>) : FragmentPagerAdapter(manager) {

    companion object {
        const val EDITION_TITLE = "TITLE"
        const val EDITION_DES = "DESCRIPTION"
        const val EDITION_IMAGE_URL = "IMAGE_URL"
    }

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        val title = history[position].title
        val description = history[position].description
        val image = history[position].imageUrl

        bundle.putString(EDITION_TITLE, title)
        bundle.putString(EDITION_DES, description)
        bundle.putString(EDITION_IMAGE_URL, image)

        return EditionFragment().apply { arguments = bundle }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return history[position].title
    }

    override fun getCount(): Int = history.size


}