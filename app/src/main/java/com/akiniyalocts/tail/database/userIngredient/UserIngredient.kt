package com.akiniyalocts.tail.database.userIngredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserIngredient(
    @ColumnInfo(name = "id", defaultValue = "0")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String
)
