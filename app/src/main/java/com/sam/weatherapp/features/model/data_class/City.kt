package com.sam.weatherapp.features.model.data_class

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "city")
data class City (
    @PrimaryKey
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("state")
    val state: String = "",
    @SerialName("country")
    val country: String = "",
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is City)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}