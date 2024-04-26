package com.example.appcrud.roomDB

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

interface PessoaDao {
    @Upsert
    suspend fun upsertPessoa(pessoa: Pessoa)

    @Delete
    suspend fun deletePessoa(pessoa: Pessoa)

    @Query("SELECT * FROM pessoa")
    fun getAllPessoa()
}