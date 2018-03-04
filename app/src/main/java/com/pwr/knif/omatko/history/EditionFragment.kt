package com.pwr.knif.omatko.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_edition.view.*
import com.pwr.knif.omatko.history.HistoryPagerAdapter.Companion.EDITION_DES
import com.pwr.knif.omatko.history.HistoryPagerAdapter.Companion.EDITION_IMAGE_URL
import com.pwr.knif.omatko.history.HistoryPagerAdapter.Companion.EDITION_TITLE


class EditionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edition, container, false)

        val title = arguments?.getString(EDITION_TITLE)
        val description = arguments?.getString(EDITION_DES)
        val imageUrl = arguments?.getString(EDITION_IMAGE_URL)

        val config = ImageLoaderConfiguration.Builder(activity).build()
        ImageLoader.getInstance().init(config)
        val imageLoader = ImageLoader.getInstance()
        with(view) {
            tv_ediotion_title.text = title
            tv_edition_description.text = description
            imageLoader.displayImage(imageUrl, iv_edition_picture)
        }
        return view
    }
}