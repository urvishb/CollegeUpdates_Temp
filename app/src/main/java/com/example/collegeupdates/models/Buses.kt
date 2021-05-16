package com.example.collegeupdates.models

import com.google.firebase.firestore.PropertyName

data class Buses(
    @get:PropertyName("bus_name") @set:PropertyName("bus_name") var busName: String = "",
    var route : String? = null,
    var expandable: Boolean = false
    )
