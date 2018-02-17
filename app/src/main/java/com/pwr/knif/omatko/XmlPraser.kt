package com.pwr.knif.omatko

import android.app.Activity
import android.content.res.XmlResourceParser
import com.google.android.gms.maps.model.LatLng


class XmlPraser {
    private val PLACE_TITLE_TAG = "title"
    private val PLACE_DESCRIPTION_TAG = "description"
    private val PLACE_LATITUDE_TAG = "latitude"
    private val PLACE_LONGITUDE_TAG = "longitude"
    private val PLACE_CATEGORY_TAG = "category"
    private val PLACE_TAG = "place"

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
}
