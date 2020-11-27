package com.gba.myroutine.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Tarefa")
class Tarefa : Serializable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "titulo")
    @SerializedName("title")
    var titulo: String = ""

    @ColumnInfo(name = "descricao")
    @SerializedName("description")
    var descricao: String = ""

    @ColumnInfo(name = "dtcriacao")
    @SerializedName("data")
    var data: String = ""

    @ColumnInfo(name = "usuarioId")
    @SerializedName("userId")
    var usuarioId: Int = 0

    @Ignore
    @SerializedName("userDTO")
    var user: Usuario = Usuario()
}