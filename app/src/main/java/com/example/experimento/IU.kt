package com.example.experimento

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

//Composable que es llamada en la main activity
@Composable
fun IU (viewModel: MyViewModel){
    Juego(viewModel = viewModel)
}

//Composable principal que alberga todos los componentes que se ve en la pantalla
@Composable
fun Juego(modifier: Modifier = Modifier, viewModel: MyViewModel){
    //Caja para poder poner en el centro horizontal de todo todos los componentes
    Box(modifier = modifier.fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center){
        //Columna para que se pongan verticalmente centrado
        Column(verticalArrangement = Arrangement.Center){

            //Texto donde se va a ver el valor de las rondas
            TextoRonda(myViewModel = viewModel)
            //Primera fila de botones. Uso rows para que queden dos y dos
            Row(horizontalArrangement = Arrangement.Center) {
                CrearBoton(modifier,viewModel,coloresJuego.ROJO)
                CrearBoton(modifier,viewModel,coloresJuego.AZUL)
            }
            Row(horizontalArrangement = Arrangement.Center) {
                CrearBoton(modifier,viewModel,coloresJuego.VERDE)
                CrearBoton(modifier,viewModel,coloresJuego.GRIS)

            }
        }
        //Columna separada para poner el boton de start bien separado y diferenciado
        Column(modifier = modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp)) {
            CrearBotonStart(modifier,viewModel, coloresJuego.AMARILLO_START)
        }
    }
}

//Composable que se encarga de crear los botones de colores con los que se juegan
@Composable
private fun CrearBoton(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
//Tag diferenciada para poder ver como va el programa
    val TAG_LOG : String = "Mi_debug_IU"
    //Variable que se usa para ver si es el estado es el indicado para habilitar el boton
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.boton_activo) }
    //Observer de la variable. Cualquier modificación hecha en esta vaaraible permitirá la actualización de la vista
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.boton_activo
    }
    //Creación y características del botón
    Button(
        //Dependiendo del estado del observable, está activo o no
        enabled = _activo.value,
        //Función que muestra un log, que este mismo muestra que botón estamos usando y el estado actual del juego, y llama a la función de comparar
        onClick = { Log.d(TAG_LOG,"Dentro del botón ${clase_enum.txt} - Estado --> ${viewModel.estadoDelJuego.value!!.name}")
            viewModel.comparar(clase_enum.valorNumerico) },
        //Modificaciones del botón
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(clase_enum.colorBoton)

    ) {
        //Texto que lleva cada botón
        Text(clase_enum.txt, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color.Black)
    }
}

//Composable que crea el botón start
@Composable
private fun CrearBotonStart(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
//Tag característico del start
    val TAG_LOG = "Mi_debug_IU_start"
    //Variable que se va a observar en la app para ver si es necesario habilitar el botón o no
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.start_activo) }
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.start_activo
    }
    //Variable creada para modificar  el color en la siguiente corrutina
    var color = remember { mutableStateOf(clase_enum.colorBoton) }

    //Corrutina que se ejecuta siempre y cuando la variable observada _activo sea true. Esta hace que mientras sea cierto, se cambie la variable color cada medio segundo
    //Si el valor de activo pasa a false esta corrutina se detiene y si vuelve a true vuelve a empezar
    LaunchedEffect(_activo) {
        while (true){
            color.value = clase_enum.colorBoton
            delay(500)
            color.value = Color.LightGray
            delay(500)
        }
    }

    Button(
        enabled = _activo.value,
        onClick = { Log.d(TAG_LOG,"Dentro del start - Estado --> ${viewModel.estadoDelJuego.value!!.name}")
            viewModel.crearRandom() },
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(color.value)
    ) {
        Text(clase_enum.txt, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color.Black)
    }
}
//Composable que muestra las rondas
@Composable
fun TextoRonda(myViewModel: MyViewModel, modifier: Modifier = Modifier){
//Variable para pillar las rondas
    var ronda by remember { mutableStateOf(0) }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Observa manualmente el LiveData
    LaunchedEffect(Unit) {
        myViewModel.getRondaValue().observe(lifecycleOwner) {
            ronda = it ?: 0
        }
    }
    Text("Ronda $ronda", modifier = modifier.padding(20.dp))
}


@Preview(showBackground = true)
@Composable
fun IUPreview(){
    IU(MyViewModel())
}