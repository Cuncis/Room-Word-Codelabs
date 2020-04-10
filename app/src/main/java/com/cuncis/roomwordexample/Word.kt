package com.cuncis.roomwordexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "word_table")
data class Word(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "word")
    var word: String
) {
    constructor(word: String): this(0, word)
}