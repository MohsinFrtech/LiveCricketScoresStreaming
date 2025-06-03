package com.cricgenix.live.cricket.appmodels

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VenuModel(
    var id: Int?,
    var venue_id: Int?,
    var ground: String?,
    var city: String?,
    var timezone: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
) : Parcelable

