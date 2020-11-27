package com.gba.myroutine.room.model

import androidx.room.Relation
import com.google.gson.annotations.SerializedName

data class TarefaAndUsuario(
        val usuario: Usuario,
        @Relation(
                parentColumn = "id",
                entityColumn = "usuarioId"
        )
        val tarefa: List<Tarefa>
)