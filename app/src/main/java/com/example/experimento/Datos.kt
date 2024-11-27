package com.example.experimento

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData

object Datos {
    var _number: Int = 0
    val rondas : MutableLiveData<Int> = MutableLiveData(1)
}
enum class coloresJuego(val colorBoton: Color, val txt: String, val valorNumerico: Int){
    ROJO(Color.Red, "Rojo",1),
    AZUL(Color.Blue,"Azul",2),
    VERDE(Color.Green,"Verde",3),
    GRIS(Color.Gray,"Gris",4),
    AMARILLO_START(Color.Yellow,"Start",0);
}
enum class EstadosJuego(val start_activo: Boolean, val boton_activo: Boolean){
    INICIO(true,false),
    GENERANDO(false,false),
    ADIVINANDO(false,true)
}
enum class EstadosAux(val txt: String){
    AUX1("AUX1"),
    AUX2("AUX2"),
    AUX3("AUX3");
}