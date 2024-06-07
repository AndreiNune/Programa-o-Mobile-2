package com.example.appcrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appcrud.roomDB.Pessoa
import com.example.appcrud.roomDB.PessoaDataBase
import com.example.appcrud.ui.theme.AppCRUDTheme
import com.example.appcrud.viewModel.PessoaViewModel
import com.example.appcrud.viewModel.Repository

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }




    private val viewModel by viewModels<PessoaViewModel>(
        factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCRUDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(viewModel, this)
                }
            }
        }
    }
}


@Composable
fun App(viewModel: PessoaViewModel,  mainActivity: MainActivity) {
    var nome by remember {
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    val pessoa = Pessoa(
        nome,
        telefone
    )

    var pessoaList by remember {
        mutableStateOf(listOf<Pessoa>())
    }

    viewModel.getPessoa().observe(mainActivity) {
        pessoaList = it
    }

    // Da linha 85 até a linha 96 está sendo declarada
    // variaveis e das linhas 98 até 104 foi construida
    // uma varivel que ira listar os resgistros de Pessoa.


    Column(
        Modifier
            .background(Color.White)
    ) {
        Row(
            Modifier
                .background(Color.White)
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(
                text = "App Cadastro",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text(text = "Nome:") }) // Permite o registro do nome na tabela
        }
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text(text = "Telefone:") }) // Permite o registro do telefone na tabela.
        }

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Button(onClick = {
                viewModel.upsertPessoa(pessoa)
            }) {
                Text(text = "Cadastrar") // Botão exectua upsert.
            }
        }

        Divider()
        LazyColumn {
            items(pessoaList) { pessoa ->
                Row(
                    Modifier
                        .clickable {
                            viewModel.deletePessoa(pessoa) // Torna o registro clicavel e executa delete
                        }
                        .fillMaxWidth(),
                    Arrangement.Center
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {
                        Text(text = "${pessoa.nome}") // Chama o registro dentro do campo nome
                    }
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {
                        Text(text = "${pessoa.telefone}") // Chama o registro dentro do campo telefone
                    }
                }
                Divider()
            }
        }
    }
}
