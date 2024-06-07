package com.example.appcrud.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


// Aqui está sendo configurada uma classe que chama pessoa e
// atribui as funções de upsert, delete e select a pessoa.

@Dao
interface PessoaDao {
    @Upsert
    suspend fun upsertPessoa(pessoa: Pessoa)

    @Delete
    suspend fun deletePessoa(pessoa: Pessoa)

    @Query("SELECT * FROM pessoa")
    fun getAllPessoa(): Flow<List<Pessoa>>
}