package com.hectorpolendo.movieapptest.domain.models

import com.hectorpolendo.movieapptest.data.database.entities.ImageEntity

data class Images(
    var id: Int?,
    var image: String?
)


fun ImageEntity.toDomain() = Images(
    id,
    image)