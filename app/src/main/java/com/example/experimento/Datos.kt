package com.example.experimento

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData

object Datos {
    //Número que se generó random
    var _number: Int = 0
    // Número de la ronda
    val rondas : MutableLiveData<Int> = MutableLiveData(1)
}

// Enum de la info de los botones. Color --> le da el color al boton. txt --> da el texto que tiene el Boton. valorNumerico --> da el valor a los botones, el que se usa para comparar el número que tiene el boton con el generado random
enum class coloresJuego(val colorBoton: Color, val txt: String, val valorNumerico: Int){
    ROJO(Color.Red, "Rojo",1),
    AZUL(Color.Blue,"Azul",2),
    VERDE(Color.Green,"Verde",3),
    GRIS(Color.Gray,"Gris",4),
    AMARILLO_START(Color.Yellow,"Start",0);
}
//Estados por los que pasa el juego
enum class EstadosJuego(val start_activo: Boolean, val boton_activo: Boolean){
    INICIO(true,false),
    GENERANDO(false,false),
    ADIVINANDO(false,true)
}

//Estados que se usaron para la cuenta atrás y para el método de estados auxiliares
enum class EstadosAuxiliares(val txt: String, val numeroTxT: String) {
    AUX5("aux5","5"),
    AUX4("aux4","4"),
    AUX3(txt = "aux3","3"),
    AUX2(txt = "aux2","2"),
    AUX1(txt = "aux1","1"),
    AUX0("aux0","0")
}