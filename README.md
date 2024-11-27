# Simón Dice en Android Studio

## Partes
<br>
<br>
<br>     
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
Como se ve, se usó una función para refactorizar el código y pasar por parámetros los diferentes elementos para crear y diferenciar cada color. Los parámetros de viewModel son necesarios para poder acceder a variables de la clase viewModel y poder usar sus valores para crear el observable que se ve debajo de la declaración de variables.    
Dentro de la creación del botón, se ve que se habilita según el *valor de la variable observada*. El *onClick* muestra mensajes de depuración y llama a un método del viewModel del que hablaré con detalle más adelante.   
La enum pedida por parámetro, como se puede ver, es utilizada para **diferenciar** sus valores que se pueden ver <a name="ValoresEnum">aquí</a>
