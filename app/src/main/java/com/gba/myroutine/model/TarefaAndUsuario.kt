package com.gba.myroutine.model

import androidx.room.Embedded
import androidx.room.Relation

data class TarefaAndUsuario(
        @Embedded val usuario: Usuario,
        @Relation(
                parentColumn = "id",
                entityColumn = "usuarioId"
        )
        val tarefa: Tarefa
)