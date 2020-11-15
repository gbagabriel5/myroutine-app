package com.gba.myroutine.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tarefa")
class Tarefa {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0

    @ColumnInfo(name = "titulo")
    var titulo: String = ""

    @ColumnInfo(name = "descricao")
    var descricao: String = ""

    @ColumnInfo(name = "dtcriacao")
    var data: String = ""
}