# Teoría de Android nativo (Kotlin + XML)

Resumen de todo lo visto en PMDM para el examen de Android: estructura del proyecto, diseño, código, navegación, View Binding, Parcelize, cámara, ciclo de vida, diseño profesional, icono y traducciones.

## Índice

1. [Estructura de un proyecto](#1-estructura-de-un-proyecto)
2. [Gradle: plugins, dependencias y defaultConfig](#2-gradle-plugins-dependencias-y-defaultconfig)
3. [Recursos (res) y cualificadores](#3-recursos-res-y-cualificadores)
4. [AndroidManifest.xml](#4-androidmanifestxml)
5. [Layouts (contenedores)](#5-layouts-contenedores)
6. [Atributos de las vistas](#6-atributos-de-las-vistas)
7. [Del XML al código](#7-del-xml-al-código)
8. [Varias Activities: Intent y putExtra](#8-varias-activities-intent-y-putextra)
9. [Null en Kotlin: `?.`, `?:` y `!!`](#9-null-en-kotlin----y-)
10. [Ciclo de vida de una Activity](#10-ciclo-de-vida-de-una-activity)
11. [View Binding](#11-view-binding)
12. [Pasar objetos: @Parcelize](#12-pasar-objetos-parcelize)
13. [La cámara (SuperHeroes)](#13-la-cámara-superheroes)
14. [Diseño profesional (Dashboard)](#14-diseño-profesional-dashboard)
15. [Icono de la app](#15-icono-de-la-app)
16. [Traducciones](#16-traducciones)
17. [Errores típicos y cómo leerlos](#17-errores-típicos-y-cómo-leerlos)
18. [EdadCanina completa, paso a paso](#18-edadcanina-completa-paso-a-paso)

---

## 1. Estructura de un proyecto

Un proyecto Android tiene **cuatro piezas**:

| Pieza | Dónde | Para qué |
|---|---|---|
| **Código Kotlin** | `app/src/main/java/...` | La **lógica**: qué pasa al pulsar, qué datos se pasan… |
| **Recursos** | `app/src/main/res/` | Todo lo que **no es código**: pantallas, textos, colores, imágenes, icono |
| **AndroidManifest.xml** | `app/src/main/` | **Declara** la app ante el sistema: Activities, cuál se abre primero, permisos, icono, tema |
| **Gradle** | `build.gradle.kts`, `settings.gradle.kts`, `libs.versions.toml` | **Construye** la app: compila, descarga librerías y aplica plugins |

> 🧠 Como una obra de teatro: guion (código), decorados (res), programa de mano (Manifest) y equipo de producción (Gradle). Gradle actúa **antes** de ejecutar, al compilar.

Al crear un proyecto: **New Project → Empty Views Activity** (⚠️ no "Empty Activity", que es Jetpack Compose y no usa XML). Lenguaje Kotlin, Minimum SDK API 24 y Kotlin DSL.

---

## 2. Gradle: plugins, dependencias y defaultConfig

Hay tres ficheros:

- **`gradle/libs.versions.toml`**: el **catálogo de versiones**. Da un nombre corto y una versión a cada librería y plugin.
- **`build.gradle.kts` (raíz)**: declara qué plugins hay disponibles, con `apply false`.
- **`build.gradle.kts` (módulo `app`)**: el importante. Aplica los plugins y lista las dependencias.

### Plugins y dependencias

```kotlin
plugins {
    alias(libs.plugins.android.application)      // máquina que fabrica apps Android
    id("org.jetbrains.kotlin.plugin.parcelize")  // máquina que entiende @Parcelize
}

dependencies {
    implementation(libs.material)            // MaterialCardView, MaterialButton...
    implementation(libs.constraintlayout)    // ConstraintLayout
    implementation(libs.androidx.gridlayout) // GridLayout
}
```

| | Plugin | Dependencia |
|---|---|---|
| Qué es | La **máquina** que fabrica | La **pieza** que va dentro de la app |
| Dónde | `plugins { }` | `dependencies { implementation(...) }` |
| Ejemplo | parcelize | material, gridlayout |

⚠️ **Trampa:** parcelize es un **plugin**. Si lo pones como `implementation(...)`, Gradle lo busca como librería y falla con *Could not find ... kotlin-parcelize*.

Después de tocar Gradle → **Sync Now**.

### La ficha técnica (`android { defaultConfig { } }`)

```kotlin
android {
    namespace = "org.iesch.superheroes"
    compileSdk { version = release(37) }
    defaultConfig {
        applicationId = "org.iesch.superheroes"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures { viewBinding = true }
}
```

| Campo | Significa |
|---|---|
| `applicationId` | Identificador **único** de la app en el móvil y en Google Play. Si lo cambias, el móvil lo trata como **otra app** (se instalan dos) |
| `namespace` | Paquete donde se generan `R` y las clases de binding |
| `minSdk` | Android **más antiguo** donde se puede instalar (24 = Android 7) |
| `targetSdk` | Versión para la que está **pensada y probada** |
| `compileSdk` | Versión con la que se **compila** |
| `versionCode` | Número entero que **sube en cada actualización** (para Google Play) |
| `versionName` | Versión que **ve el usuario** ("1.0") |

> 🧠 `minSdk ≤ targetSdk ≤ compileSdk`

---

## 3. Recursos (res) y cualificadores

| Carpeta | Contiene |
|---|---|
| `layout/` | Pantallas en XML |
| `values/` | `strings.xml`, `colors.xml`, `themes.xml`, `styles.xml` |
| `drawable/` | Imágenes (`perro.jpg`) y gráficos XML (degradados, iconos vectoriales) |
| `mipmap/` | **Solo el icono** de la app |
| `xml/` | Configuraciones (por ejemplo `provider_paths.xml`) |

⚠️ Los nombres de recursos solo pueden llevar **minúsculas, números y `_`**.

### Cualificadores

Una carpeta con un sufijo se usa solo en ciertos casos. Android elige sola:

- `values/` → **por defecto**
- `values-en/` → móvil en inglés
- `values-night/` → modo oscuro
- `mipmap-hdpi/`, `mipmap-xxhdpi/`… → distintas resoluciones

Reglas:
- Si falta un string en una carpeta con cualificador → se usa **el de por defecto** (string a string). La app funciona.
- Si falta en la carpeta **por defecto** → **no compila**.

---

## 4. AndroidManifest.xml

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:icon="@mipmap/ic_launcher"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:label="@string/app_name"
        android:theme="@style/Theme.MiApp">

        <!-- LA PRIMERA: la que tiene el intent-filter -->
        <activity
            android:name=".LoginActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- LAS DEMÁS: solo declaradas -->
        <activity android:name=".HomeActivity" android:exported="false" />
        <activity android:name=".EdadCaninaActivity" android:exported="false" />
    </application>
</manifest>
```

- `MAIN` + `LAUNCHER` = **la que se abre al pulsar el icono**. Solo una Activity las tiene.
- Para cambiar cuál sale primero, se **mueve el `<intent-filter>`** a otra `<activity>`.
- **Todas** las Activities tienen que estar declaradas. Si no: `ActivityNotFoundException`.
- `icon` = icono, `label` = nombre bajo el icono, `theme` = estilo de la app.

> 🧠 **Manifest = la puerta de entrada. Código = los pasillos.** El Manifest dice cuál se abre primero; el código (Intents) dice a cuál se va después.

---

## 5. Layouts (contenedores)

Un layout es una **caja que coloca vistas dentro**. Una pantalla es un **árbol** de vistas (se ve en el **Component Tree**).

- **View**: elemento visible → `TextView`, `EditText`, `Button`, `ImageView`…
- **ViewGroup**: contenedor → `LinearLayout`, `ConstraintLayout`…

| Contenedor | Cómo coloca | Cuándo usarlo |
|---|---|---|
| **LinearLayout** | En fila o columna (`orientation`). **Nunca salta de línea**: lo que no cabe se sale por el borde | Pantallas sencillas en columna (EdadCanina) |
| **ConstraintLayout** | Con **restricciones** ("cuerdas") a los bordes o a otras vistas | Diseño libre (Login, Home) |
| **GridLayout** | En cuadrícula (`columnCount`) | Menú de tarjetas |
| **FrameLayout** | Apiladas, unas encima de otras | Algo encima de otra cosa |
| **ScrollView** | No coloca: permite **deslizar**. **Solo admite un hijo** | Cuando no cabe (Login con teclado) |

```
ScrollView
 └── ConstraintLayout        ← un único hijo
      ├── ImageView
      ├── EditText
      └── Button
```

### ConstraintLayout

- Cada lado (Top, Bottom, Start, End) se ata al `parent` o a otra vista.
- **Regla de oro:** cada vista necesita **al menos una restricción horizontal y una vertical**. Si no, en el móvil se va **arriba a la izquierda** (aunque en el editor se vea bien: eso es `tools:layout_editor_absoluteX`, solo del editor).
- Start **y** End atados → la vista se **centra**.
- `0dp` = "ocupa el espacio entre mis restricciones" (en vez de `match_parent`).
- En el editor: arrastrar los **circulitos** de los lados; **varita mágica** = *Infer Constraints*; aviso **rojo** en el Component Tree = falta una restricción.
- En un **LinearLayout no hay cuerdas**: se ordena arrastrando en el Component Tree.

---

## 6. Atributos de las vistas

| Atributo | Significa |
|---|---|
| `layout_width` / `layout_height` | **Obligatorios**. `match_parent` (todo el padre), `wrap_content` (lo justo) o fijo (`80dp`) |
| `dp` | Unidad para **tamaños y distancias** |
| `sp` | Unidad para **textos** (respeta el tamaño de letra del usuario) |
| `padding` | Espacio **por dentro** (el fondo se ve en él) |
| `layout_margin` | Espacio **por fuera** (transparente) |
| `android:text` | Texto real |
| `tools:text` (🔧) | Texto **solo para el editor**; en la app no aparece |
| `android:hint` | Texto gris de ayuda en un `EditText`; desaparece al escribir |
| `android:inputType` | Teclado: `number`, `textPassword`… |
| `gravity` | Alinea el **contenido** dentro de la vista |
| `layout_gravity` | Alinea la **vista** dentro de su padre |
| `background` | Fondo: `@color/...` o `@drawable/...` |
| `backgroundTint` | Color de fondo de un **botón** Material |
| `adjustViewBounds` | La imagen ajusta su alto a su proporción (sin huecos) |
| `minHeight="48dp"` | Tamaño mínimo para tocar con el dedo (quita el error *Touch target size too small*) |

**Crear strings y colores desde el editor:** en Attributes, botoncito a la derecha del campo → **`+`** → *String Value* / *Color Value*. Se guardan solos en `strings.xml` / `colors.xml`.

**Textos fijos:** si sale *Hardcoded text*, ponte encima y **Alt + Enter** → *Extract string resource*.

---

## 7. Del XML al código

> El **XML dibuja** la pantalla. El **código coge** los elementos por su id y les dice qué hacer.

### `findViewById`

Busca una vista por su id. Se coge una vista cuando el código tiene que **leerla, cambiarla o escucharla** (por eso también se coge el botón: para ponerle el clic).

```kotlin
setContentView(R.layout.activity_main)    // 1º mostrar la pantalla

val etEdad = findViewById<EditText>(R.id.etEdad)
val btnCalcular = findViewById<Button>(R.id.btnCalcular)
val tvResultado = findViewById<TextView>(R.id.tvResultado)
```

⚠️ Antes de `setContentView`, o con un id que no existe → devuelve `null` y peta.

### `setOnClickListener`

```kotlin
btnCalcular.setOnClickListener {
    // se ejecuta CADA VEZ que se pulsa, no al abrir la app
}
```

### Leer y escribir

```kotlin
val edadString = etEdad.text.toString()   // ⚠️ CON .text (lo escrito), no etEdad.toString() (el campo)
tvResultado.text = "lo que sea"
```

### `Toast`

```kotlin
Toast.makeText(this, R.string.texto_toast, Toast.LENGTH_LONG).show()   // ⚠️ sin .show() no sale
```

### `getString` con huecos

```xml
<string name="resultado_texto">Si fueras un perro, tu edad sería de %d años.</string>
<string name="info">%s tiene %d años</string>
```
```kotlin
getString(R.string.resultado_texto, edadPerro)   // %d ← edadPerro
getString(R.string.info, "Ana", 20)              // "Ana tiene 20 años"
```

- Busca el texto en el idioma que toque y **rellena los huecos en orden** (como `String.format` / `printf` de Java).
- `%s` = texto (acepta cualquier cosa), `%d` = **solo enteros**, `%1$s`, `%2$d` = posición fija.
- ⚠️ Parámetros al revés → compila, pero al ejecutar `IllegalFormatConversionException`.

### `@string/nombre`

Significa "el texto está en `strings.xml`". Se hace así para **poder traducir**: el layout siempre dice `@string/calcular` y Android elige "Calcular" o "Calculate" según el idioma.

---

## 8. Varias Activities: Intent y putExtra

### Navegar

```kotlin
val intent = Intent(this, HomeActivity::class.java)   // prepara el viaje: desde aquí → al Home
startActivity(intent)                                 // hace el viaje
```

> 🧠 Como pedir un Uber: el Intent es poner el destino; `startActivity` es confirmar. Sin `startActivity` no pasa nada (y no da error).

### Pasar datos

```kotlin
// ENVÍA (Login)
btnEntrar.setOnClickListener {
    val nombre = etUsuario.text.toString()
    val intent = Intent(this, HomeActivity::class.java)
    intent.putExtra("USUARIO", nombre)        // mete con etiqueta
    startActivity(intent)
    finish()                                  // quita el Login de la pila
}

// RECIBE (Home, dentro de onCreate)
val usuario = intent.getStringExtra("USUARIO") ?: "Invitado"   // saca con la MISMA etiqueta
tvSaludo.text = getString(R.string.saludo_usuario, usuario)    // "Hola %1$s"
```

- `intent` a secas, en el destino = el Intent con el que te abrieron.
- Uno por tipo: `getStringExtra`, `getIntExtra("EDAD", 0)`, `getBooleanExtra("X", false)`…
- ⚠️ Etiqueta distinta → llega `null` (sin error). Mejor usar una **constante** para la clave.

### Bundle

Un `Bundle` es una **caja de datos con etiquetas** (como un `HashMap`). `putExtra` mete en el Bundle del Intent.

```kotlin
val bundle = intent.extras!!                 // forma del profe: la caja entera
val ruta = bundle.getString("path_heroe")
```

El `savedInstanceState: Bundle?` de `onCreate` es otra caja para guardar el estado al girar; solo se pasa a `super.onCreate(...)`.

### La pila de Activities

Las pantallas se **apilan** como platos. Cada `startActivity` pone una encima; **Atrás** o **`finish()`** quita la de arriba.

```
Login → Entrar (con finish) → [Home]                      Atrás desde Home → sales de la app
Login → Entrar (sin finish) → [Home] encima de [Login]    Atrás desde Home → vuelves al Login
```

---

## 9. Null en Kotlin: `?.`, `?:` y `!!`

| Símbolo | Significa | Si es `null`… |
|---|---|---|
| `?.` | "si no es null, sigue" | devuelve `null`, no peta |
| `?:` | "si es null, usa esto otro" (operador **Elvis**) | usa el plan B |
| `!!` | "te prometo que no es null" | **peta** (`NullPointerException`) |

```kotlin
val nombre = superHeroe?.nombre ?: "No hay nombre"
```

---

## 10. Ciclo de vida de una Activity

```
onCreate()  → se crea (setContentView, findViewById, listeners)
onStart()   → se hace visible
onResume()  → en primer plano, se puede usar
onPause()   → pierde el primer plano
onStop()    → ya no se ve
onDestroy() → se destruye
onRestart() → vuelve tras un onStop (luego onStart → onResume)
```

> 🧠 Van por parejas: Create/Destroy, Start/Stop, Resume/Pause.

| Acción | Métodos |
|---|---|
| Abres la app | `onCreate` → `onStart` → `onResume` |
| Abres otra encima | la de abajo: `onPause` → `onStop` (**no** `onDestroy`) |
| Vuelves con Atrás | la de abajo: `onRestart` → `onStart` → `onResume` |
| Cierras (Atrás o `finish()`) | `onPause` → `onStop` → `onDestroy` |
| **Giras el móvil** | se **destruye y se crea** de nuevo |

Orden completo al abrir otra: `Main.onPause → Otra.onCreate → onStart → onResume → Main.onStop`.

### `override` y `super`

```kotlin
class MainActivity : AppCompatActivity() {      // hereda de AppCompatActivity (como extends)
    override fun onPause() {                     // mi versión del método
        super.onPause()                          // primero la del PADRE (obligatorio)
        Log.i("CICLOVIDA", "onPause")            // y luego lo mío (se ve en el Logcat)
    }
}
```

Sin `super` → `SuperNotCalledException`.

---

## 11. View Binding

Genera una clase por layout con una variable por cada vista **con id**. Sustituye a `findViewById`.

**1. Activar** (`build.gradle.kts` de `app` → Sync):
```kotlin
android { buildFeatures { viewBinding = true } }
```

**2. Usar:**
```kotlin
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)   // monta la pantalla desde el XML
        setContentView(binding.root)                             // y la muestra
        binding.tvSaludo.text = "Hola"
        binding.btnEntrar.setOnClickListener { /* ... */ }
    }
}
```

- `inflate` = montar las vistas a partir del XML (el XML son las instrucciones; `inflate` monta el mueble).
- `layoutInflater` = el montador. `binding.root` = la vista de fuera del todo.

**Nombres:**

| De | A |
|---|---|
| `activity_main.xml` | `ActivityMainBinding` |
| `activity_home.xml` | `ActivityHomeBinding` |
| id `btn_guardar` | `binding.btnGuardar` |
| id `hero_name_edit` | `binding.heroNameEdit` |

| `findViewById` | View Binding |
|---|---|
| Una línea por vista | Todo de golpe |
| Id inexistente → peta al ejecutar | Id inexistente → **no compila** |

---

## 12. Pasar objetos: @Parcelize

`putExtra` solo admite tipos sencillos. Para mandar un **objeto entero**, la clase tiene que ser **Parcelable** (empaquetable).

```kotlin
@Parcelize
data class SuperHeroe(
    val nombre: String,
    val alterEgo: String,
    val bio: String,
    val power: Float
) : Parcelable
```

Requiere el **plugin** de parcelize en Gradle.

```kotlin
// Enviar
intent.putExtra("superHero", superHeroe)

// Recibir (desde Android 13 / API 33 se pide la clase)
val superHeroe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    intent.getParcelableExtra("superHero", SuperHeroe::class.java)
} else {
    intent.getParcelableExtra<SuperHeroe>("superHero")
}
```

Sin `@Parcelize` y `: Parcelable`, el error sale en el `putExtra`.

---

## 13. La cámara (SuperHeroes)

Tu app **no hace la foto**: se la pide a la app de cámara y espera la respuesta.

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var heroBitmap: Bitmap? = null
    private var picturePath = ""            // ruta de la foto (variable de CLASE: vive tras volver de la cámara)

    // TROZO 1: quién recibe la respuesta. Las llaves se ejecutan AL VOLVER de la cámara.
    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        // success: true si hizo la foto, false si canceló (lo da la cámara)
        if (success && picturePath.isNotEmpty()) {
            heroBitmap = BitmapFactory.decodeFile(picturePath)   // fichero → Bitmap
            binding.heroImage.setImageBitmap(heroBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.heroImage.setOnClickListener { abrirCamara() }

        binding.btnGuardar.setOnClickListener {
            val superHeroe = SuperHeroe(
                binding.heroNameEdit.text.toString(),
                binding.alterEgoEdit.text.toString(),
                binding.bioEdit.text.toString(),
                binding.power.rating
            )
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("superHero", superHeroe)
            intent.putExtra("path_heroe", picturePath)
            startActivity(intent)
        }
    }

    // TROZO 3: abrir la cámara
    private fun abrirCamara() {
        val imageFile = crearImagenFile()
        val uri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", imageFile)
        getContent.launch(uri)
    }

    // TROZO 2: fichero vacío donde irá la foto
    private fun crearImagenFile(): File {
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("superhero_image", ".jpg", dir)
        picturePath = imageFile.absolutePath   // la apunto para usarla al volver
        return imageFile                       // la devuelvo para crear la uri AHORA
    }
}
```

**Orden:** pulsar imagen → crear fichero (y guardar ruta) → FileProvider da la `uri` → `launch` abre la cámara → al volver, `success` → `decodeFile` → `setImageBitmap`.

### FileProvider (el "portero")

La cámara es otra app y no puede entrar en tus carpetas. El FileProvider le da **permiso temporal** para ese fichero.

**Manifest**, dentro de `<application>`:
```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/provider_paths"/>
</provider>
```

**`res/xml/provider_paths.xml`:**
```xml
<paths>
    <external-files-path name="superhero_images" path="." />
</paths>
```

⚠️ `authorities` del Manifest y `".provider"` del código **tienen que coincidir**.

### Recibir en DetailActivity

```kotlin
val path = intent.getStringExtra("path_heroe")
val bitmap = BitmapFactory.decodeFile(path)
binding.imagenHeroeDetail.setImageBitmap(bitmap)
binding.heroNameTv.text = superHeroe?.nombre ?: "No hay nombre"
```

---

## 14. Diseño profesional (Dashboard)

### Menú: GridLayout + MaterialCardView

```xml
<androidx.gridlayout.widget.GridLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:columnCount="2">

    <com.google.android.material.card.MaterialCardView
        android:id="@+id/cardEdadCanina"
        android:layout_width="0dp"
        android:layout_height="110dp"
        app:layout_columnWeight="1"
        android:layout_margin="6dp"
        app:cardCornerRadius="20dp"
        app:cardElevation="0dp"
        app:strokeWidth="0dp"
        app:cardBackgroundColor="@color/verde_personalizado">

        <!-- dentro: ConstraintLayout con ImageView (icono) + TextView -->

    </com.google.android.material.card.MaterialCardView>
</androidx.gridlayout.widget.GridLayout>
```

- `columnCount`: tarjetas por fila.
- `layout_width="0dp"` + `layout_columnWeight="1"`: cada tarjeta ocupa la misma parte del ancho.
- `cardCornerRadius`: esquinas redondeadas. `cardElevation="0dp"`: sin sombra. `strokeWidth="0dp"`: sin borde.

Dependencias: `libs.androidx.gridlayout` y `libs.material`. En la Palette se buscan con la lupa. Si sale ⬇, falta la dependencia.

```kotlin
binding.cardEdadCanina.setOnClickListener {
    startActivity(Intent(this, EdadCaninaActivity::class.java))
}
```

### Foto redonda: ShapeableImageView + estilo

`res/values/styles.xml`:
```xml
<resources>
    <style name="CircularImageView">
        <item name="cornerSize">50%</item>      <!-- 50% = círculo; 20dp = esquinas suaves -->
    </style>
</resources>
```

```xml
<com.google.android.material.imageview.ShapeableImageView
    android:id="@+id/imgPerfil"
    android:layout_width="80dp"
    android:layout_height="80dp"
    android:scaleType="centerCrop"
    android:src="@drawable/perfil"
    app:shapeAppearanceOverlay="@style/CircularImageView" />
```

⚠️ `tools:srcCompat="@tools:sample/avatars"` solo se ve en el editor.

### Fondo degradado

`res/drawable/fondo_login.xml` (New → Drawable Resource File, root `shape`):
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:startColor="#E0F7FA"
        android:endColor="#B2EBF2"
        android:angle="90" />          <!-- 0 izq→der, 90 abajo→arriba, 270 arriba→abajo -->
    <corners android:radius="20dp" />  <!-- opcional -->
</shape>
```

Uso: `android:background="@drawable/fondo_login"`.

---

## 15. Icono de la app

1. Clic derecho en `res` → **New → Image Asset**.
2. *Launcher Icons (Adaptive and Legacy)*, nombre **`ic_launcher`** (así no hay que tocar el Manifest).
3. *Foreground Layer*: imagen, Clip Art o texto (ajusta con *Resize*). *Background Layer*: color o imagen.
4. *Next → Finish* y aceptar sobrescribir.

Se crean las carpetas `mipmap-*`. El Manifest lo usa con `android:icon="@mipmap/ic_launcher"`.

⚠️ Si sigue saliendo el icono viejo: **desinstalar la app** y volver a ejecutar.

---

## 16. Traducciones

1. **Todos** los textos en `strings.xml` (nunca a mano en el layout o el código).
2. `values/strings.xml` = español (por defecto). `values-en/strings.xml` = inglés.
3. `strings.xml` → **Open editor** (o clic derecho → *Open Translations Editor*) → **globo 🌐** → *English (en)* → rellenar las celdas rojas.
4. El hueco se mantiene en los dos idiomas:
   ```xml
   <!-- values -->     <string name="saludo_usuario">Hola %1$s</string>
   <!-- values-en -->  <string name="saludo_usuario">Hello %1$s</string>
   ```
5. `app_name` también se traduce (nombre bajo el icono).
6. Probar: cambiar el idioma del móvil, o el **globo** de la barra de la vista Design.

---

## 17. Errores típicos y cómo leerlos

**Logcat** (icono del gato 🐱) → buscar `FATAL` → leer la línea **`Caused by`** y la línea con **`MainActivity.kt:NÚMERO`**.

| Error | Causa típica |
|---|---|
| `NumberFormatException: For input string: "androidx...EditText{...}"` | `etEdad.toString()` en vez de `etEdad.text.toString()` |
| `NumberFormatException: For input string: ""` | `toInt()` con el campo vacío (falta el `if isEmpty`) |
| `ActivityNotFoundException` | Activity no declarada en el Manifest |
| `IllegalFormatConversionException` | Parámetros de `getString` en mal orden o tipo (`%d` con un String) |
| `NullPointerException` | `!!` sobre algo `null`, o `findViewById` con id inexistente |
| `SuperNotCalledException` | Falta `super.onXxx()` en un método del ciclo de vida |
| *Could not find ... kotlin-parcelize* | Parcelize puesto como dependencia en vez de plugin |
| *Unresolved reference R.id.main* | El contenedor del layout ya no se llama `main` |
| Vista arriba a la izquierda en el móvil | Faltan restricciones en el ConstraintLayout |
| *No target device found* | No hay emulador ni móvil conectado |
| Al pulsar no pasa nada | Falta `startActivity(intent)` o `.show()` del Toast |

### Checklist antes de entregar

- [ ] La Activity de inicio tiene `MAIN` + `LAUNCHER`; todas las demás están declaradas.
- [ ] Ids con sentido; el contenedor principal sigue siendo `main`.
- [ ] Sin avisos rojos de restricciones en el Component Tree.
- [ ] Todos los textos en `strings.xml` y traducidos en `values-en`.
- [ ] Icono propio con Image Asset.
- [ ] Plugin de parcelize y `viewBinding = true` si se usan.
- [ ] FileProvider en el Manifest + `provider_paths.xml` si hay cámara.
- [ ] Probado en el móvil o emulador.

---

## 18. EdadCanina completa, paso a paso

### Pasos

1. **New Project → Empty Views Activity**, nombre `EdadCanina`, paquete `org.iesch.edadcanina`, API 24.
2. Copiar `perro.jpg` a `res/drawable` (arrastrar, o *Resource Manager → + → Import Drawables*).
3. En `activity_main.xml` (Design): Component Tree → clic derecho en `main` → **Convert view → LinearLayout**, `orientation` = **vertical**. Borrar el "Hello World".
4. Añadir de arriba abajo: `ImageView` (perro), `TextView` (pregunta), `Number` (campo), `Button` y `TextView` (resultado), creando cada texto y color con el botoncito `+` de Attributes.
5. Añadir en `strings.xml` los textos que usa solo el código (`texto_toast`, `resultado_texto`).
6. Programar `MainActivity.kt`.
7. Traducir con `values-en`.

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/imgPerro"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:adjustViewBounds="true"
        app:srcCompat="@drawable/perro" />

    <TextView
        android:id="@+id/tvPregunta"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="24dp"
        android:text="@string/pregunta_edad"
        android:textColor="@color/verde_personalizado"
        android:textSize="24sp"
        android:textStyle="bold" />

    <EditText
        android:id="@+id/etEdad"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="24dp"
        android:ems="10"
        android:gravity="center"
        android:hint="@string/introduce_tu_edad"
        android:inputType="number"
        android:minHeight="48dp" />

    <Button
        android:id="@+id/btnCalcular"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="24dp"
        android:layout_marginVertical="16dp"
        android:backgroundTint="@color/verde_personalizado"
        android:text="@string/calcular"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvResultado"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="24dp"
        android:textSize="18sp"
        android:textStyle="bold"
        tools:text="Si fueras un perro, tendrías 47 años." />

</LinearLayout>
```

### `MainActivity.kt`

```kotlin
package org.iesch.edadcanina

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Cogemos las vistas que el código usa
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // 2. Al pulsar Calcular...
        btnCalcular.setOnClickListener {
            val edadString = etEdad.text.toString()     // lo ESCRITO (con .text)

            if (edadString.isEmpty()) {
                Toast.makeText(this, R.string.texto_toast, Toast.LENGTH_LONG).show()
            } else {
                val edadInt = edadString.toInt()        // texto → número
                val edadPerro = edadInt * 7
                tvResultado.text = getString(R.string.resultado_texto, edadPerro)
            }
        }
    }
}
```

### `values/strings.xml`

```xml
<resources>
    <string name="app_name">Edad Canina</string>
    <string name="pregunta_edad">¿Qué edad tienes?</string>
    <string name="introduce_tu_edad">Introduce tu edad</string>
    <string name="calcular">Calcular</string>
    <string name="texto_toast">Has de introducir tu edad.</string>
    <string name="resultado_texto">Si fueras un perro, tu edad sería de %d años.</string>
</resources>
```

### `values-en/strings.xml`

```xml
<resources>
    <string name="app_name">Dog Age</string>
    <string name="pregunta_edad">How old are you?</string>
    <string name="introduce_tu_edad">Your age</string>
    <string name="calcular">Calculate</string>
    <string name="texto_toast">You must enter your age.</string>
    <string name="resultado_texto">If you were a dog, you would be %d years old.</string>
</resources>
```

### `values/colors.xml` (línea añadida)

```xml
<color name="verde_personalizado">#2E7D32</color>
```
