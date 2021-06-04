package com.akiniyalocts.tail.database.ingredient

import androidx.room.*

@Entity
data class LocalIngredient(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
)