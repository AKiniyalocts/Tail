package com.akiniyalocts.tail.database.userIngredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserIngredient(
    @PrimaryKey
    val name: String
)
