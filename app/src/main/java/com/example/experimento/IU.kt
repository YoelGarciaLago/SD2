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

@Composable
fun IU (viewModel: MyViewModel){
    Juego(viewModel = viewModel)
}
@Composable
fun Juego(modifier: Modifier = Modifier, viewModel: MyViewModel){
    Box(modifier = modifier.fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center){
        Column(verticalArrangement = Arrangement.Center){
            TextoRonda(myViewModel = viewModel)
            Row(horizontalArrangement = Arrangement.Center) {
                CrearBoton(modifier,viewModel,coloresJuego.ROJO)
                CrearBoton(modifier,viewModel,coloresJuego.AZUL)
            }
            Row(horizontalArrangement = Arrangement.Center) {
                CrearBoton(modifier,viewModel,coloresJuego.VERDE)
                CrearBoton(modifier,viewModel,coloresJuego.GRIS)

            }
        }
        Column(modifier = modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp)) {
            //CrearBoton(modifier,viewModel, coloresJuego.AMARILLO_START)
            CrearBotonStart(modifier,viewModel, coloresJuego.AMARILLO_START)
        }
    }
}

@Composable
private fun CrearBoton(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
    val TAG_LOG : String = "Mi_debug_IU"
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.boton_activo) }
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.boton_activo
    }
    Button(
        enabled = _activo.value,
        onClick = { Log.d(TAG_LOG,"Dentro del botón ${clase_enum.txt} - Estado --> ${viewModel.estadoDelJuego.value!!.name}")
            viewModel.comparar(clase_enum.valorNumerico) },
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(clase_enum.colorBoton)

    ) {
        Text(clase_enum.txt, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color.Black)
    }
}

@Composable
private fun CrearBotonStart(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
    val TAG_LOG = "Mi_debug_IU"
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.start_activo) }
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.start_activo
    }
    var color = remember { mutableStateOf(clase_enum.colorBoton) }

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
@Composable
fun TextoRonda(myViewModel: MyViewModel, modifier: Modifier = Modifier){
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