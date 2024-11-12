package com.example.experimento

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val TAG_LOG : String = "Mi_debug"
    var _numbers = mutableStateOf(0)
    var texto: MutableState<String> = mutableStateOf("Numero generado")
    var isCorrect: MutableState<Boolean?> = mutableStateOf(null)

    fun crearRandomBoton() {
        val randomButtonIndex = (1..4).random()
        Datos._numbers = randomButtonIndex
        Log.d(TAG_LOG, "Random: $randomButtonIndex") // Muestra en el Log el número aleatorio
    }

    fun compararColores(numColor: Int): Boolean {
        val resultado = numColor == Datos._numbers
        isCorrect.value = resultado  // Actualiza isCorrect y Compose detectará el cambio
        Log.d(TAG_LOG,"Resultado: " + resultado)
        // Actualiza el texto según el resultado
        texto.value = if (resultado) "Correcto" else "Incorrecto"
        return resultado
    }
}