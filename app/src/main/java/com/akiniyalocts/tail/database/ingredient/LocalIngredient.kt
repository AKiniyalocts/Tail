package com.akiniyalocts.tail.database.ingredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalIngredient(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
)