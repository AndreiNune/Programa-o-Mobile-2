package com.example.aulafirebase26_07

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aulafirebase26_07.ui.theme.AulaFirebase26_07Theme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(db)
        }
    }
}

@Composable
fun App(db : FirebaseFirestore) {
    var nome by remember {
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    var clientes by remember { mutableStateOf<List<Map<String, String>>>(emptyList()) }


    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){

        }

        Row(
            Modifier
                .fillMaxWidth(),
                Arrangement.Center) {
            Text(text = "App Firebase Firestore")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

        }

        Row (
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
            ) {
                Text(text = "Nome:")
            }
            Column() {
                TextField(value = nome, onValueChange = { nome = it}
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
            ) {
                Text(text = "Telefone:")
            }
            Column(

            ) {
                TextField(
                    value = telefone,
                    onValueChange = { telefone = it}
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            
        }
        Row(
            Modifier 
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Button(onClick = {
                val pessoas = hashMapOf(
                    "nome" to nome,
                    "telefone" to telefone
                )

                db.collection("clientes").add(pessoas)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}" ) }
                    .addOnFailureListener { e ->
                        Log.w(TAG,"Error adding document", e) }
            }) {
                Text(text = "Cadastrar")
            }
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

            }
            Row (
                Modifier
                    .fillMaxWidth()
            ){
                Column (
                    Modifier
                        .fillMaxWidth(0.3f)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(clientes) { client ->
                            ClientRow(client)
                        }
                    }
                }
            }
            Row (
                Modifier
                    .fillMaxWidth()
            ){
                Column() {
                    db.collection("clientes")
                        .get()
                        .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val lista = hashMapOf(
                                "nome" to "${document.data.get("nome")}",
                                "telefone" to "${document.data.get("telefone")}"
                            )
                            Log.d(TAG, "${document.id} => ${document.data}")
                        }

                    }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents: ", exception)
                        }
                }
                LaunchedEffect(Unit) {
                    Listar(db) { updatedClientes ->
                        clientes = updatedClientes
                    }
                }
            }
        }
    }
}

fun Listar (db: FirebaseFirestore, onResult: (List<Map<String, String>>) -> Unit) {
    db.collection("clientes")
        .get()
        .addOnSuccessListener { documents ->
            val lista = documents.map { document -> mapOf(
                "nome" to (document.getString("nome") ?: ""),
                "telefone" to (document.getString("telefone") ?: "")
                )
            }
            onResult(lista)
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
            onResult(emptyList())
        }
}

@Composable
fun ClientRow(client: Map<String, String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = "Nome: ${client["nome"]}", fontSize = 16.sp)
        Text(text = "Telefone: ${client["telefone"]}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
    }
}