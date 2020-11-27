package com.gba.myroutine.room.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Usuario")
class Usuario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "nome")
    @NonNull
    @SerializedName("name")
    var nome: String = ""

    @ColumnInfo(name = "email")
    @NonNull
    @SerializedName("email")
    var email: String = ""

    @ColumnInfo(name = "senha")
    @NonNull
    @SerializedName("password")
    var senha: String = ""

    @Ignore
    @SerializedName("tasks")
    var tasks: List<Tarefa> = listOf()
}