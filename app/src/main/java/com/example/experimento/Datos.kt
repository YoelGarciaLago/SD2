package com.example.experimento

import androidx.compose.ui.graphics.Color

object Datos {
    var _numbers: Int = 0
    enum class coloresJuego(rgb: Int, colorBoton: Color){
        ROJO(1, Color.Red),AZUL(2, Color.Blue),VERDE(3, Color.Green),GRIS(4, Color.Gray);
    }
}