package com.example.experimento

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val TAG_LOG : String = "Mi_debug"
    var _numbers = mutableStateOf(0)
    var estadoDelJuego : MutableLiveData<EstadosJuego> = MutableLiveData(EstadosJuego.INICIO)

    fun crearRandom(){
        estadoDelJuego.value = EstadosJuego.GENERANDO
        _numbers.value = (1..4).random()
        //Datos._number = _numbers.value
        Log.d(TAG_LOG, "Valor random creado --> ${_numbers.value} - Estado --> ${estadoDelJuego.value}")
        pasarRandom()
    }

    fun pasarRandom(){
        Log.d(TAG_LOG,"Pasamos la variable al object Datos - Estado --> ${estadoDelJuego.value}")
        Datos._number = _numbers.value
        estadoDelJuego.value = EstadosJuego.ADIVINANDO
    }

    fun comparar(nComparar: Int): Boolean {
        Log.d(TAG_LOG, "Comprobación de la variable - Estado --> ${estadoDelJuego.value}")
        return if (nComparar.equals(Datos._number)){
            estadoDelJuego.value = EstadosJuego.INICIO
            Log.d(TAG_LOG,"Has acertado - Estado --> ${estadoDelJuego.value}")
            true
        }else{
            estadoDelJuego.value = EstadosJuego.ADIVINANDO
            Log.d(TAG_LOG,"No has acertado - Estado --> ${estadoDelJuego.value}")
            false
        }
    }

}