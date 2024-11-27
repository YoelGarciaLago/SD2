#Simón Dice en Android Studio

## Partes

### IU
La IU está compuesta por cinco botones y un texto.   
Cuatro de los botones son los que se utilizan para jugar e intentar adivinar el número. El siguiente código es el que se utiliza para crear esos botones:   
````
@Composable
private fun CrearBoton(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
    val TAG_LOG: String = "Mi_debug_IU"
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.boton_activo) }
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.boton_activo
    }
    Button(
        enabled = _activo.value,
        onClick = {
            Log.d(TAG_LOG, "Dentro del botón ${clase_enum.txt} - Estado --> ${viewModel.estadoDelJuego.value!!.name}")
            viewModel.comparar(clase_enum.valorNumerico)
        },
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(clase_enum.colorBoton)
    ) {
        Text(clase_enum.txt, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color.Black)
    }
}

````
