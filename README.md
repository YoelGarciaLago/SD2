# Simón Dice en Android Studio  

## Partes  
<br>· ***IU***</a></br>  
<br>· ***Datos***</a></br>  
<br>· ***ViewModel***</a></br>   
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
La enum pedida por parámetro, como se puede ver, es utilizada para **diferenciar** sus valores que se pueden ver más adelante.   

Después vendría el botón de start:
````
@Composable
private fun CrearBotonStart(modifier: Modifier, viewModel: MyViewModel, clase_enum: coloresJuego) {
    val TAG_LOG = "Mi_debug_IU_start"
    var _activo = remember { mutableStateOf(viewModel.estadoDelJuego.value!!.start_activo) }
    viewModel.estadoDelJuego.observe(LocalLifecycleOwner.current) {
        _activo.value = viewModel.estadoDelJuego.value!!.start_activo
    }
    var color = remember { mutableStateOf(clase_enum.colorBoton) }

    LaunchedEffect(_activo) {
        while (true) {
            color.value = clase_enum.colorBoton
            delay(500)
            color.value = Color.LightGray
            delay(500)
        }
    }

    Button(
        enabled = _activo.value,
        onClick = {
            Log.d(TAG_LOG, "Dentro del start - Estado --> ${viewModel.estadoDelJuego.value!!.name}")
            viewModel.crearRandom()
        },
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(color.value)
    ) {
        Text(clase_enum.txt, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = Color.Black)
    }
}

````
Explicado el anterior método, en este no se tiene por qué entrar mucho en detalle, por lo que solo nos centraremos en los detalles que lo diferencian del otro.

- La variable activo, en vez de estar en boton_activo, está en el boton_start, lo que hace que la activación del botón sea diferente *(se entrará en detalle en Datos).*
- Se crea una variable color para asignarle el color al botón, a diferencia de los otros botones que se ponía **directamente con la enum**.
- Se crea una corrutina que cada medio segundo cambia el color del botón
- Dentro del onClick del botón, se llama a un método diferente y el color se le asigna como se mencionó antes.   
   
Finalizando con los elementos gráficos se encuentra la siguiente composable:  
````
@Composable
fun TextoRonda(myViewModel: MyViewModel, modifier: Modifier = Modifier) {
    var ronda by remember { mutableStateOf(0) }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Observa manualmente el LiveData de la ronda
    LaunchedEffect(Unit) {
        myViewModel.getRondaValue().observe(lifecycleOwner) {
            ronda = it ?: 0 
        }
    }
    Text("Ronda $ronda", modifier = modifier.padding(20.dp))
}

````
Fragmentos a destacar:
- **val lifecycleOwner = LocalLifecycleOwner.current** --> se usa para indicar quien es el propietario del ciclo de vida actual *(imprescindible en las corrutinas)*.   
- **myViewModel.getRondaValue().observe(lifecycleOwner)** --> observa los cambios en el valor de la ronda dentro del ViewModel y actualiza **automáticamente** la interfaz de usuario cuando el valor cambia.   
- **ronda = it ?: 0** --> si el valor es **nulo**, lo pone por defecto a 0.  



### Datos
Esta clase es la encargada de albergar las enum y las variables que contienen los valores generados por el ViewModel.    
Enum de estados:   
````
enum class EstadosJuego(val start_activo: Boolean, val boton_activo: Boolean){
    INICIO(true,false),
    GENERANDO(false,false),
    ADIVINANDO(false,true)
}
````
En esta enum, los valores booleanos sirven para activar o desactivar los botones de juego, lo que da una sensación de inicio, juego y fin. Es útil tambien para tratar el flujo del programa y como éste se comporta dependiendo de **X** variable tenga qué valor de esta enum.   
Enum de Estados Auxiliares:   
````
enum class EstadosAuxiliares(val txt: String, val numeroTxT: String) {
    AUX5("aux5","5"),
    AUX4("aux4","4"),
    AUX3(txt = "aux3","3"),
    AUX2(txt = "aux2","2"),
    AUX1(txt = "aux1","1"),
    AUX0("aux0","0")
}

````
Esta enum servía principalmente para una función que creaba una corrutina, los llamaba y hacía esperar al programa. Más adelante, pensé en darles un uso para que hicieran una cuenta atrás y que modificase el comportamiento del programa si este llegaba a 0 o si acertabas a tiempo, sin embargo, por diversas complicaciones a la hora de implementar esta nueva característica al programa, no pude hacerlo y quedó esta enum sin usar.   
Enum de Botones:  
````
enum class coloresJuego(val colorBoton: Color, val txt: String, val valorNumerico: Int){
    ROJO(Color.Red, "Rojo",1),
    AZUL(Color.Blue,"Azul",2),
    VERDE(Color.Green,"Verde",3),
    GRIS(Color.Gray,"Gris",4),
    AMARILLO_START(Color.Yellow,"Start",0);
}
````
Esta alberga qué diferencia a cada color. Parámetros:   
- **colorBoton** --> color correspondiente a cada botón *(el color rojo al botón del rojo por ejemplo)*.
- **txt** --> texto que se pone dentro del botón.
- **valorNumerico** --> valor que tiene cada botón. Esto sirve para ver qué botón que a pulsado el jugador y si es igual al que se generó.

### ViewModel
Esta es la clase que tiene toda la lógica del programa en su interior. Elementos a destacar:  
````
 fun crearRandom(){
        estadoDelJuego.value = EstadosJuego.GENERANDO
        _numbers.value = (1..4).random()
        Log.d(TAG_LOG, "Valor random creado --> ${_numbers.value} - Estado --> ${estadoDelJuego.value}")
        pasarRandom()
    }
````
Crea un random *(dentro de los límites de los botones)*, cambia el estado del juego, manda un mensaje de depuración y llama a una función.  
````
fun pasarRandom(){
        Log.d(TAG_LOG,"Pasamos la variable al object Datos - Estado --> ${estadoDelJuego.value}")
        Datos._number = _numbers.value
        estadoDelJuego.value = EstadosJuego.ADIVINANDO
    }
````
Función que pasa el random a la variable de Datos y cambia de nuevo el estado del juego.  
````
 fun comparar(nComparar: Int): Boolean {
        Log.d(TAG_LOG, "Comprobación de la variable - Estado --> ${estadoDelJuego.value}")
        return if (nComparar.equals(Datos._number)){
            Datos.rondas.value = Datos.rondas.value?.plus(1)
            Log.d(TAG_LOG,"Valor de ronda: ${Datos.rondas.value}")
            Log.d(TAG_LOG,"Has acertado - Estado --> ${estadoDelJuego.value}")
            crearRandom()
            true
        }else{
            estadoDelJuego.value = EstadosJuego.INICIO
            Datos.rondas.value = 1
            Log.d(TAG_LOG,"No has acertado - Estado --> ${estadoDelJuego.value}")
            false
        }
    }
````
Este método es el más complejo de toda la clase, el encargado de *mirar* si el número que pulsaste fué el correcto. Si es igual, aumenta el valor de la variable Datos.rondas a uno, crea otro random y devuelve un true. Si se falla, devuelve un false, pasa el valor de las rondas a 1 y pone el estado del juego a INICIO.   
