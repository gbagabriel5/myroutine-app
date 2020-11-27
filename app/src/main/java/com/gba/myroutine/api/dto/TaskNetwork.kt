package com.gba.myroutine.api.dto

import com.gba.myroutine.room.model.Usuario
import com.google.gson.annotations.SerializedName

class TaskNetwork(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val titulo: String,
    @SerializedName("description") val descricao: String,
    @SerializedName("data") val data: String,
    @SerializedName("userDTO") val userDTO: Usuario
)