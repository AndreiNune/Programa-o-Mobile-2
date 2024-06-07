package com.example.appcrud.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

// Este código esta abstraindo Pessoa do Banco e
// abstraindo PessoaDao e atribuindo a Pessoa.

@Database(
    entities = [Pessoa::class],
    version = 1
)

abstract class PessoaDataBase: RoomDatabase() {
    abstract fun pessoaDao(): PessoaDao

}