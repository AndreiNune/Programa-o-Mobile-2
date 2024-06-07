package com.example.appcrud.viewModel

import com.example.appcrud.roomDB.Pessoa
import com.example.appcrud.roomDB.PessoaDataBase


// Está classe é uma função que possibilita
// a aplicação dos comandos SQL na entidade
// pessoa.
class Repository(private val db: PessoaDataBase) {
    suspend fun upsertPessoa(pessoa: Pessoa){
        db.pessoaDao().upsertPessoa(pessoa)
    }
    suspend fun deletePessoa(pessoa: Pessoa){
        db.pessoaDao().deletePessoa(pessoa)
    }
    fun getAllPessoa() = db.pessoaDao().getAllPessoa()
}