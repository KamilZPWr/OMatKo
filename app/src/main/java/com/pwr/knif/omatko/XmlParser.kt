package com.pwr.knif.omatko

import android.app.Activity
import android.content.res.XmlResourceParser
import android.util.Xml
import com.google.android.gms.maps.model.LatLng
import com.pwr.knif.omatko.history.Edition
import com.pwr.knif.omatko.map.Place


class XmlPraser {
    companion object {
        const val PLACE_TITLE_TAG = "title"
        const val PLACE_DESCRIPTION_TAG = "description"
        const val PLACE_LATITUDE_TAG = "latitude"
        const val PLACE_LONGITUDE_TAG = "longitude"
        const val PLACE_CATEGORY_TAG = "category"
        const val PLACE_TAG = "place"

        const val EDITION_TAG = "edition"
        const val EDITION_TITLE_TAG = "title"
        const val EDITION_DESCRIPTION_TAG = "description"
        const val EDITION_IMAGE_URL_TAG = "imageUrl"
    }


    fun getPlacesFromFile(activity: Activity): List<Place> {
        val place = activity.resources.getXml(R.xml.places)
        var eventType = -1
        val places = mutableListOf<Place>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {

            if (eventType == XmlResourceParser.START_TAG && place.name == PLACE_TAG) {

                val title = place.getAttributeValue(null, PLACE_TITLE_TAG)
                val description = place.getAttributeValue(null, PLACE_DESCRIPTION_TAG)
                val latitude = place.getAttributeValue(null, PLACE_LATITUDE_TAG).toDouble()
                val longitude = place.getAttributeValue(null, PLACE_LONGITUDE_TAG).toDouble()
                val category = place.getAttributeValue(null, PLACE_CATEGORY_TAG)
                val location = LatLng(latitude, longitude)
                places.add(Place(title, description, location, category))
            }
            eventType = place.next()
        }
        return places
    }

    fun getHistoryFromFile(activity: Activity): List<Edition> {
        val history = activity.resources.getXml(R.xml.history)
        var eventType = -1
        val editions = mutableListOf<Edition>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG && history.name == EDITION_TAG) {

                val title = history.getAttributeValue(null, EDITION_TITLE_TAG)
                val desc = history.getAttributeValue(null, EDITION_DESCRIPTION_TAG)
                val imageUrl = history.getAttributeValue(null, EDITION_IMAGE_URL_TAG)

                editions.add(Edition(title, desc, imageUrl))
            }
            eventType = history.next()
        }
        return editions
    }
}
