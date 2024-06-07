package com.example.appcrud.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

// Aqui está sendo definida a estrutura da tabela na qual
// será utilizada, tal como seus campo e chave primaria.
@Entity
data class Pessoa(
    val nome:String,
    val telefone:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
