package com.example.appcrud.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcrud.roomDB.Pessoa
import kotlinx.coroutines.launch


// Aqui está sendo criada uma class que detem as
// funções de upsert e delete aplicadas em Pessoa.
class PessoaViewModel(private val repository: Repository): ViewModel() {
    fun getPessoa() = repository.getAllPessoa().asLiveData(viewModelScope.coroutineContext)

    fun upsertPessoa(pessoa: Pessoa){
        viewModelScope.launch {
            repository.upsertPessoa(pessoa)
        }
    }

    fun deletePessoa(pessoa: Pessoa){
        viewModelScope.launch {
            repository.deletePessoa(pessoa)
        }
    }
}