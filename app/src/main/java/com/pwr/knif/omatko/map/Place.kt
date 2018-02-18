package com.pwr.knif.omatko.map

import com.google.android.gms.maps.model.LatLng

data class Place(
        var title: String,
        var description: String,
        var location: LatLng,
        var category: String
)