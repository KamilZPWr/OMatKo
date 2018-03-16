package com.pwr.knif.omatko

import android.app.Activity
import android.content.res.XmlResourceParser
import com.google.android.gms.maps.model.LatLng
import com.pwr.knif.omatko.contact.PersonContact
import com.pwr.knif.omatko.history.Edition
import com.pwr.knif.omatko.map.Place
import com.pwr.knif.omatko.sponsors.Sponsor


class XmlPraser {
    companion object {
        const val TITLE_TAG = "title"
        const val DESCRIPTION_TAG = "description"
        const val LATITUDE_TAG = "latitude"
        const val LONGITUDE_TAG = "longitude"
        const val CATEGORY_TAG = "category"
        const val PLACE_TAG = "place"
        const val EDITION_TAG = "edition"
        const val IMAGE_URL_TAG = "imageUrl"
        const val CONTACT_TAG = "person"
        const val NAME_TAG = "name"
        const val NUMBER_TAG = "phoneNumber"
        const val EMAIL_TAG = "email"
        const val POSITION_TAG = "position"
        const val IMAGE_ID_TAG = "imageId"
    }


    fun getPlacesFromFile(activity: Activity): List<Place> {
        val place = activity.resources.getXml(R.xml.places)
        var eventType = -1
        val places = mutableListOf<Place>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {

            if (eventType == XmlResourceParser.START_TAG && place.name == PLACE_TAG) {

                val title = place.getAttributeValue(null, TITLE_TAG)
                val description = place.getAttributeValue(null, DESCRIPTION_TAG)
                val latitude = place.getAttributeValue(null, LATITUDE_TAG).toDouble()
                val longitude = place.getAttributeValue(null, LONGITUDE_TAG).toDouble()
                val category = place.getAttributeValue(null, CATEGORY_TAG)
                val location = LatLng(latitude, longitude)
                places.add(Place(title, description, location, category))
            }
            eventType = place.next()
        }
        return places
    }

    fun getHistoryFromFile(activity: Activity): List<Edition> {
        val historyXml = activity.resources.getXml(R.xml.history)
        var eventType = -1
        val editions = mutableListOf<Edition>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG && historyXml.name == EDITION_TAG) {

                val title = historyXml.getAttributeValue(null, TITLE_TAG)
                val desc = historyXml.getAttributeValue(null, DESCRIPTION_TAG)
                val imageUrl = historyXml.getAttributeValue(null, IMAGE_URL_TAG)

                editions.add(Edition(title, desc, imageUrl))
            }
            eventType = historyXml.next()
        }
        return editions
    }

    fun getContactsFromFile(activity: Activity): List<PersonContact> {
        val contactsXml = activity.resources.getXml(R.xml.contacts)
        var eventType = -1
        val contacts = mutableListOf<PersonContact>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG && contactsXml.name == CONTACT_TAG) {

                val name = contactsXml.getAttributeValue(null, NAME_TAG)
                val phoneNumber = contactsXml.getAttributeValue(null, NUMBER_TAG)
                val email = contactsXml.getAttributeValue(null, EMAIL_TAG)
                val description = contactsXml.getAttributeValue(null, DESCRIPTION_TAG)
                val position = contactsXml.getAttributeValue(null, POSITION_TAG)
                val imageUrl = contactsXml.getAttributeValue(null, IMAGE_URL_TAG)

                contacts.add(PersonContact(name, position, phoneNumber, email, description, imageUrl))
            }
            eventType = contactsXml.next()
        }
        return contacts
    }

    fun getSponsorsFromFile(activity: Activity, typeOfSponsor: String): List<Sponsor> {
        val sponsorsXml = activity.resources.getXml(R.xml.sponsors)
        var eventType = -1
        val sponsors = mutableListOf<Sponsor>()

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG
                    && sponsorsXml.name == typeOfSponsor.toLowerCase()) {
                val name = sponsorsXml.getAttributeValue(null, NAME_TAG)
                val title = sponsorsXml.getAttributeValue(null, TITLE_TAG)
                val desc = sponsorsXml.getAttributeValue(null, DESCRIPTION_TAG)
                val imageUrl = sponsorsXml.getAttributeValue(null, IMAGE_ID_TAG)

                sponsors.add(Sponsor(name, title, desc, imageUrl))
            }
            eventType = sponsorsXml.next()
        }
        return sponsors
    }
}
