package com.gba.myroutine.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
class Usuario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0

    @ColumnInfo(name = "nome")
    @NonNull
    var nome: String = ""

    @ColumnInfo(name = "email")
    @NonNull
    var email: String = ""

    @ColumnInfo(name = "senha")
    @NonNull
    var senha: String = ""
}