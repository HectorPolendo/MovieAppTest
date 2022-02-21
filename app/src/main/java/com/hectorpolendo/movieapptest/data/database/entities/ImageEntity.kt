package com.hectorpolendo.movieapptest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hectorpolendo.movieapptest.domain.models.Images

@Entity(tableName = "Images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val image: String?
)

fun Images.toDatabase() = ImageEntity(
    id,
    image
)