package com.pwr.knif.omatko.map

import com.google.android.gms.maps.model.LatLng

interface MapOpener {
    fun openMap(location: LatLng, title: String)
}