package com.example.experimento

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    //Tag para Debuguear
    val TAG_LOG : String = "Mi_debug"
    //Varaible para que genere un random y se lo pase a Datos
    var _numbers = mutableStateOf(0)
    //Variable que controla el estado del juego
    var estadoDelJuego : MutableLiveData<EstadosJuego> = MutableLiveData(EstadosJuego.INICIO)


//Nombre bastante descriptivo
    fun crearRandom(){
        //Pasa el estado a Generando
        estadoDelJuego.value = EstadosJuego.GENERANDO
    //Crea el random entre los límites de los valores de los botones
        _numbers.value = (1..4).random()
        //Log para poder ver como va el programa
        Log.d(TAG_LOG, "Valor random creado --> ${_numbers.value} - Estado --> ${estadoDelJuego.value}")
        pasarRandom()
    }

    //Función que pasa el random creado en la variable de esta clase para la varaible de datos
    fun pasarRandom(){
        Log.d(TAG_LOG,"Pasamos la variable al object Datos - Estado --> ${estadoDelJuego.value}")
        Datos._number = _numbers.value
        estadoDelJuego.value = EstadosJuego.ADIVINANDO
    }

    fun comparar(nComparar: Int): Boolean {

        Log.d(TAG_LOG, "Comprobación de la variable - Estado --> ${estadoDelJuego.value}")

        //Return que tiene condiciones. Esta es condición que mira si es igual
        return if (nComparar.equals(Datos._number)){
            //Suma una ronda
            Datos.rondas.value = Datos.rondas.value?.plus(1)
            Log.d(TAG_LOG,"Valor de ronda: ${Datos.rondas.value}")
            Log.d(TAG_LOG,"Has acertado - Estado --> ${estadoDelJuego.value}")
            crearRandom()
            //devuelve true para que se actualice en la IU
            true
        }else{
            //Pasa el juego al inicio
            estadoDelJuego.value = EstadosJuego.INICIO
            //Reinicia las rondas
            Datos.rondas.value = 1
            Log.d(TAG_LOG,"No has acertado - Estado --> ${estadoDelJuego.value}")
            false
        }
    }

    //Método auxiliar para utilizado anteriormente
    fun estadosAuxiliares(){
        viewModelScope.launch(){
            var estadosAux : EstadosAuxiliares = EstadosAuxiliares.AUX1
            Log.d(TAG_LOG,"estado auxiliar ${estadosAux.txt}")
            delay(1500)
            estadosAux = EstadosAuxiliares.AUX2
            Log.d(TAG_LOG,"estado auxiliar ${estadosAux.txt}")
            delay(1500)
            estadosAux = EstadosAuxiliares.AUX3
            Log.d(TAG_LOG,"estado auxiliar ${estadosAux.txt}")
            delay(1500)
            estadosAux = EstadosAuxiliares.AUX4
            Log.d(TAG_LOG,"estado auxiliar ${estadosAux.txt}")
            delay(1500)
            estadosAux = EstadosAuxiliares.AUX5
            Log.d(TAG_LOG,"estado auxiliar ${estadosAux.txt}")
            delay(1500)

        }
    }

    //Método para poder pasarle el valor de las rondas a la IU
    fun getRondaValue(): MutableLiveData<Int>{
        return Datos.rondas
    }
}