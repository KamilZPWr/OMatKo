package com.pwr.knif.omatko

import com.google.android.gms.maps.model.LatLng

data class Place(
        var title: String,
        var description: String,
        var location: LatLng
)