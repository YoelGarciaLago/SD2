package com.example.experimento

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val TAG_LOG : String = "Mi_debug"
    var _numbers = mutableStateOf(0)
    var estadoDelJuego : MutableLiveData<EstadosJuego> = MutableLiveData(EstadosJuego.INICIO)
    val estadoLiveData: MutableLiveData<EstadosJuego?> = MutableLiveData(EstadosJuego.INICIO)
    private val TAG_LOG_CUENTA = "miDebug_Cuenta"
    val cuentaAtras = mutableStateOf("Cuenta atrás :D")


    fun crearRandom(){
        estadoDelJuego.value = EstadosJuego.GENERANDO
        _numbers.value = (1..4).random()
        //Datos._number = _numbers.value
        Log.d(TAG_LOG, "Valor random creado --> ${_numbers.value} - Estado --> ${estadoDelJuego.value}")
        pasarRandom()
        cuentaAtrasFun()
    }

    fun pasarRandom(){
        Log.d(TAG_LOG,"Pasamos la variable al object Datos - Estado --> ${estadoDelJuego.value}")
        Datos._number = _numbers.value
        estadoDelJuego.value = EstadosJuego.ADIVINANDO
    }

    fun comparar(nComparar: Int): Boolean {
        //estadosAuxiliares()

        Log.d(TAG_LOG, "Comprobación de la variable - Estado --> ${estadoDelJuego.value}")
        return if (nComparar.equals(Datos._number)){
            estadoDelJuego.value = EstadosJuego.INICIO
            Datos.rondas.value = Datos.rondas.value?.plus(1)
            Log.d(TAG_LOG,"Valor de ronda: ${Datos.rondas.value}")
            Log.d(TAG_LOG,"Has acertado - Estado --> ${estadoDelJuego.value}")
            true
        }else{
//            estadoDelJuego.value = EstadosJuego.ADIVINANDO
            Log.d(TAG_LOG,"No has acertado - Estado --> ${estadoDelJuego.value}")
            false
        }
    }
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
    fun cuentaAtrasFun(){
        viewModelScope.launch {
            for(i in EstadosAuxiliares.values()){
                Log.d(TAG_LOG_CUENTA, "${i.txt}")
                cuentaAtras.value = i.numeroTxT
                delay(1000)  // Pausa de 1 segundo entre cada cambio de cuenta

                // Cuando llegue a "0", actualiza la cuenta y cambia el estado
                if(cuentaAtras.value.equals("0")){
                    cuentaAtras.value = "Se acabó el tiempo" // Cambia el texto final
                    estadoLiveData.value = EstadosJuego.INICIO
                    Datos.rondas.value = 1// Cambia el estado al finalizar la cuenta
                    break  // Sale del ciclo una vez terminado

                }
            }
        }
    }
    fun getRondaValue(): MutableLiveData<Int>{
        return Datos.rondas
    }
}