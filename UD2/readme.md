# Introducción a Dart

## 1. ¿Qué es Dart?

**Dart** es un lenguaje de programación desarrollado por Google y utilizado principalmente para crear aplicaciones con **Flutter**.

Si ya conoces lenguajes como Java, Kotlin, C#, JavaScript o similares, la sintaxis de Dart te resultará bastante familiar.

Por ejemplo:

```dart
void main() {
  print('Hola, mundo!');
}
```

En este tutorial veremos únicamente los aspectos de Dart que necesitaremos para comenzar a trabajar con Flutter.

---

# 2. La función `main()`

Todo programa Dart comienza su ejecución en la función:

```dart
void main() {
  print('Hola, mundo!');
}
```

`main()` es el **punto de entrada** de la aplicación.

También podemos escribir:

```dart
void main() {
  var nombre = 'Alberto';

  print('Hola $nombre');
}
```

Dart permite utilizar **interpolación de cadenas**:

```dart
print('Hola $nombre');
```

En lugar de:

```dart
print('Hola ' + nombre);
```

También podemos evaluar expresiones:

```dart
int edad = 20;

print('El año que viene tendrás ${edad + 1} años');
```

---

# 3. Variables y tipos de datos

Dart es un lenguaje **fuertemente tipado**, aunque permite utilizar inferencia de tipos.

## 3.1. Tipos básicos

Los tipos más habituales son:

```dart
int edad = 20;

double precio = 19.95;

String nombre = 'Ana';

bool activo = true;
```

También existe:

```dart
num numero = 10;
```

`num` puede almacenar tanto valores enteros como decimales:

```dart
num numero = 10;

numero = 10.5;
```

---

## 3.2. Inferencia de tipos con `var`

Podemos dejar que Dart determine automáticamente el tipo:

```dart
var nombre = 'Ana';
var edad = 20;
var precio = 15.95;
```

Dart deduce:

```text
nombre → String
edad   → int
precio → double
```

Una vez determinado el tipo, no podemos cambiarlo:

```dart
var edad = 20;

// ERROR
edad = 'veinte';
```

---

## 3.3. `final`

`final` permite crear una variable cuyo valor solo se puede asignar una vez:

```dart
final nombre = 'Ana';
```

Después no podemos hacer:

```dart
nombre = 'Luis'; // ERROR
```

Es muy habitual utilizar `final` cuando sabemos que un valor no va a cambiar.

---

## 3.4. `const`

`const` se utiliza para valores que son constantes en tiempo de compilación:

```dart
const pi = 3.14159;
```

También podemos indicar explícitamente el tipo:

```dart
const double pi = 3.14159;
```

Diferencia simplificada:

```dart
final fecha = DateTime.now();
```

Es válido porque el valor se calcula durante la ejecución.

En cambio:

```dart
const fecha = DateTime.now();
```

no es válido porque `DateTime.now()` no es una constante de compilación.

---

# 4. Null Safety

Dart incorpora **null safety**.

Una variable normal no puede contener `null`:

```dart
String nombre = 'Ana';
```

Si queremos permitir `null`, utilizamos `?`:

```dart
String? nombre;

nombre = null;
```

Por ejemplo:

```dart
String? nombre;

print(nombre);
```

Podemos comprobar si contiene un valor:

```dart
if (nombre != null) {
  print(nombre);
}
```

También podemos utilizar el operador `??`:

```dart
String? nombre;

print(nombre ?? 'Sin nombre');
```

Si `nombre` es `null`, se utilizará:

```text
Sin nombre
```

---

# 5. List

Una `List` representa una colección ordenada de elementos.

```dart
List<String> alumnos = [
  'Ana',
  'Luis',
  'Marta'
];
```

Podemos acceder mediante índice:

```dart
print(alumnos[0]);
```

Resultado:

```text
Ana
```

Los índices comienzan en `0`.

---

## Añadir elementos

```dart
alumnos.add('Pedro');
```

## Eliminar elementos

```dart
alumnos.remove('Luis');
```

## Número de elementos

```dart
print(alumnos.length);
```

## Recorrer una lista

```dart
for (var alumno in alumnos) {
  print(alumno);
}
```

También podemos utilizar:

```dart
alumnos.forEach((alumno) {
  print(alumno);
});
```

---

# 6. Maps

Un `Map` almacena información en forma de:

```text
clave → valor
```

Es similar a un diccionario en otros lenguajes.

Por ejemplo:

```dart
Map<String, int> edades = {
  'Ana': 20,
  'Luis': 22,
  'Marta': 19
};
```

Podemos acceder a un elemento mediante su clave:

```dart
print(edades['Ana']);
```

Resultado:

```text
20
```

Añadir un elemento:

```dart
edades['Pedro'] = 21;
```

Modificar:

```dart
edades['Ana'] = 21;
```

Eliminar:

```dart
edades.remove('Luis');
```

Comprobar si existe una clave:

```dart
if (edades.containsKey('Ana')) {
  print('Ana existe');
}
```

---

## Recorrer un Map

Podemos recorrer las claves:

```dart
for (var nombre in edades.keys) {
  print(nombre);
}
```

Los valores:

```dart
for (var edad in edades.values) {
  print(edad);
}
```

O ambas cosas:

```dart
for (var entrada in edades.entries) {
  print('${entrada.key}: ${entrada.value}');
}
```

---

# 7. Set

Un `Set` es una colección de elementos **sin duplicados**.

```dart
Set<String> colores = {
  'rojo',
  'verde',
  'azul'
};
```

Si intentamos añadir un elemento que ya existe:

```dart
colores.add('rojo');
```

El elemento no se duplica.

Podemos comprobar si existe:

```dart
if (colores.contains('verde')) {
  print('El color existe');
}
```

Los `Set` son especialmente útiles cuando nos interesa saber si un elemento pertenece a una colección y no queremos elementos repetidos.

---

# 8. Iterables

`Iterable` es una abstracción muy importante en Dart.

Una `List`, un `Set` y otras colecciones pueden recorrerse mediante un `Iterable`.

Por ejemplo:

```dart
List<int> numeros = [1, 2, 3, 4, 5];
```

Podemos obtener los números pares:

```dart
Iterable<int> pares = numeros.where((numero) {
  return numero % 2 == 0;
});
```

Podemos recorrerlos:

```dart
for (var numero in pares) {
  print(numero);
}
```

Resultado:

```text
2
4
```

---

## `map()`

Podemos transformar los elementos:

```dart
List<int> numeros = [1, 2, 3];

Iterable<int> dobles = numeros.map((numero) {
  return numero * 2;
});
```

Resultado:

```text
2
4
6
```

También podemos convertir el resultado en una lista:

```dart
List<int> dobles = numeros
    .map((numero) => numero * 2)
    .toList();
```

---

## `where()`

Filtra elementos:

```dart
List<int> numeros = [1, 2, 3, 4, 5, 6];

List<int> pares = numeros
    .where((numero) => numero % 2 == 0)
    .toList();
```

Resultado:

```text
[2, 4, 6]
```

---

## `firstWhere()`

Permite buscar un elemento:

```dart
var resultado = alumnos.firstWhere(
  (alumno) => alumno == 'Ana'
);
```

---

# 9. Funciones

Una función permite encapsular un comportamiento.

```dart
int sumar(int a, int b) {
  return a + b;
}
```

Podemos utilizarla:

```dart
int resultado = sumar(5, 3);

print(resultado);
```

Resultado:

```text
8
```

---

## Funciones de una sola expresión

Dart permite utilizar la sintaxis `=>`:

```dart
int sumar(int a, int b) => a + b;
```

Es equivalente a:

```dart
int sumar(int a, int b) {
  return a + b;
}
```

Esta sintaxis se utiliza muchísimo en Dart y Flutter.

Por ejemplo:

```dart
var numeros = [1, 2, 3, 4];

var dobles = numeros
    .map((numero) => numero * 2)
    .toList();
```

---

# 10. Parámetros opcionales

Podemos definir parámetros opcionales utilizando `[]`.

```dart
void saludar(String nombre, [String? apellido]) {
  print('Hola $nombre $apellido');
}
```

Podemos llamar a la función:

```dart
saludar('Ana');
```

O:

```dart
saludar('Ana', 'García');
```

---

# 11. Parámetros con nombre

Una característica especialmente importante de Dart son los **parámetros con nombre**.

Se indican utilizando `{}`:

```dart
void crearUsuario({
  required String nombre,
  required int edad,
}) {
  print('$nombre tiene $edad años');
}
```

La llamada sería:

```dart
crearUsuario(
  nombre: 'Ana',
  edad: 20,
);
```

Esto hace que el código sea mucho más legible.

---

## `required`

La palabra `required` indica que el parámetro es obligatorio:

```dart
void mostrarUsuario({
  required String nombre,
  required int edad,
}) {
  print(nombre);
  print(edad);
}
```

No podremos hacer:

```dart
mostrarUsuario(nombre: 'Ana');
```

porque falta `edad`.

---

## Parámetros con nombre opcionales

También podemos definir valores por defecto:

```dart
void configurarUsuario({
  String idioma = 'es',
  bool oscuro = false,
}) {
  print(idioma);
  print(oscuro);
}
```

Podemos llamar:

```dart
configurarUsuario();
```

O:

```dart
configurarUsuario(
  idioma: 'en',
  oscuro: true,
);
```

Los parámetros con nombre son especialmente importantes en **Flutter**, donde aparecen constantemente:

```dart
Text(
  'Hola',
  style: TextStyle(
    fontSize: 20,
  ),
);
```

---

# 12. Clases

Dart permite utilizar programación orientada a objetos.

Una clase sencilla:

```dart
class Persona {
  String nombre;
  int edad;

  Persona(this.nombre, this.edad);

  void saludar() {
    print('Hola, soy $nombre');
  }
}
```

Podemos crear objetos:

```dart
void main() {
  Persona persona = Persona('Ana', 20);

  persona.saludar();
}
```

---

## Constructor con parámetros con nombre

También podemos utilizar parámetros con nombre:

```dart
class Persona {
  String nombre;
  int edad;

  Persona({
    required this.nombre,
    required this.edad,
  });
}
```

Ahora:

```dart
Persona persona = Persona(
  nombre: 'Ana',
  edad: 20,
);
```

Esta forma es muy habitual en Flutter.

---

# 13. Getters

Un `getter` permite acceder a una propiedad calculada como si fuera un atributo.

Por ejemplo:

```dart
class Persona {
  String nombre;
  int edad;

  Persona(this.nombre, this.edad);

  String get informacion {
    return '$nombre tiene $edad años';
  }
}
```

Podemos utilizarlo así:

```dart
Persona persona = Persona('Ana', 20);

print(persona.informacion);
```

No utilizamos:

```dart
persona.informacion();
```

porque `informacion` se comporta como una propiedad.

También podemos utilizar la sintaxis corta:

```dart
String get informacion => '$nombre tiene $edad años';
```

---

# 14. Setters

Un `setter` permite controlar cómo se modifica una propiedad.

```dart
class Persona {
  String nombre;
  int _edad;

  Persona(this.nombre, this._edad);

  int get edad => _edad;

  set edad(int valor) {
    if (valor >= 0) {
      _edad = valor;
    }
  }
}
```

Podemos hacer:

```dart
Persona persona = Persona('Ana', 20);

persona.edad = 25;

print(persona.edad);
```

El setter permite controlar los valores que puede recibir la propiedad.

---

# 15. Futures

Cuando una operación tarda un tiempo en terminar, Dart utiliza normalmente un `Future`.

Un `Future` representa un valor que estará disponible **en el futuro**.

Por ejemplo:

```dart
Future<String> obtenerNombre() {
  return Future.delayed(
    Duration(seconds: 2),
    () => 'Ana',
  );
}
```

La función no devuelve inmediatamente un `String`.

Devuelve:

```dart
Future<String>
```

Podemos imaginarlo como:

```text
Ahora                  Dentro de 2 segundos

Future<String>  --->   "Ana"
```

Los `Future` son fundamentales en Flutter porque muchas operaciones son asíncronas:

- Consultas a Internet.
- Bases de datos.
- Lectura de archivos.
- Peticiones HTTP.
- Acceso a determinados dispositivos o servicios.

---

# 16. `async` y `await`

Normalmente no queremos trabajar directamente con el `Future`.

Podemos utilizar `async` y `await`.

```dart
Future<String> obtenerNombre() async {
  return 'Ana';
}
```

Y:

```dart
void main() async {
  String nombre = await obtenerNombre();

  print(nombre);
}
```

`await` significa, aproximadamente:

> Espera a que termine esta operación asíncrona.

---

## Ejemplo más realista

```dart
Future<String> descargarDatos() async {
  await Future.delayed(
    Duration(seconds: 2),
  );

  return 'Datos descargados';
}
```

Podemos utilizarla:

```dart
void main() async {
  print('Comenzando...');

  String resultado = await descargarDatos();

  print(resultado);

  print('Fin');
}
```

Resultado:

```text
Comenzando...
Datos descargados
Fin
```

---

# 17. `try`, `on` y `catch`

Las operaciones pueden producir excepciones.

Podemos controlarlas mediante:

```dart
try {
  // Código que puede producir una excepción
} catch (e) {
  // Tratamiento del error
}
```

Por ejemplo:

```dart
try {
  int resultado = 10 ~/ 0;
  print(resultado);
} catch (e) {
  print('Se ha producido un error');
}
```

---

## Capturar un tipo concreto de excepción

Podemos utilizar `on`:

```dart
try {
  int resultado = 10 ~/ 0;
} on IntegerDivisionByZeroException {
  print('No se puede dividir entre cero');
} catch (e) {
  print('Otro error: $e');
}
```

La estructura es:

```dart
try {
  // Código
} on TipoDeError {
  // Tratamiento específico
} catch (e) {
  // Otros errores
}
```

---

# 18. Excepciones en operaciones asíncronas

También podemos utilizar `try-catch` con `async` y `await`.

```dart
Future<String> obtenerDatos() async {
  throw Exception('Error al obtener los datos');
}
```

Podemos controlarlo:

```dart
void main() async {
  try {
    String datos = await obtenerDatos();

    print(datos);
  } catch (e) {
    print('Error: $e');
  }
}
```

Esto será muy habitual cuando trabajemos con APIs o bases de datos en Flutter.

---

# 19. `async*` y `Stream`

Hasta ahora hemos visto:

```dart
Future
```

Un `Future` representa **un único resultado futuro**.

Pero ¿qué ocurre si queremos recibir varios resultados a lo largo del tiempo?

Para eso podemos utilizar un:

```dart
Stream
```

Por ejemplo:

```text
Future

-----> Resultado


Stream

-----> Resultado
-----> Resultado
-----> Resultado
-----> Resultado
```

---

## Crear un Stream con `async*`

Utilizamos:

```dart
async*
```

Por ejemplo:

```dart
Stream<int> contador() async* {
  for (int i = 1; i <= 5; i++) {
    await Future.delayed(
      Duration(seconds: 1),
    );

    yield i;
  }
}
```

La palabra `yield` devuelve un valor al `Stream`.

Pero la función continúa ejecutándose posteriormente.

---

# 20. Escuchar un Stream

Podemos utilizar `await for`:

```dart
void main() async {
  await for (var numero in contador()) {
    print(numero);
  }
}
```

Obtendremos:

```text
1
2
3
4
5
```

Los valores se reciben uno detrás de otro.

---

# 21. Diferencia entre `Future` y `Stream`

Esta diferencia es fundamental para trabajar con Flutter.

### Future

Representa **un resultado** que llegará en el futuro:

```dart
Future<String> obtenerUsuario() async {
  return 'Ana';
}
```

Conceptualmente:

```text
Petición
   ↓
   ↓
   ↓
Resultado
```

---

### Stream

Representa **una secuencia de resultados**:

```dart
Stream<int> contador() async* {
  yield 1;
  yield 2;
  yield 3;
}
```

Conceptualmente:

```text
Petición
   ↓
Resultado
   ↓
Resultado
   ↓
Resultado
```

Un ejemplo típico sería recibir continuamente información de un sensor, mensajes de un servidor o cambios en una base de datos.

---

# 22. Ejemplo completo

Vamos a juntar varios conceptos.

```dart
class Alumno {
  String nombre;
  List<double> notas;

  Alumno({
    required this.nombre,
    required this.notas,
  });

  double get media {
    if (notas.isEmpty) {
      return 0;
    }

    return notas.reduce((a, b) => a + b) / notas.length;
  }

  set añadirNota(double nota) {
    if (nota >= 0 && nota <= 10) {
      notas.add(nota);
    }
  }
}

Future<String> obtenerCurso() async {
  await Future.delayed(
    Duration(seconds: 1),
  );

  return 'DAM 2º';
}

void main() async {
  Alumno alumno = Alumno(
    nombre: 'Ana',
    notas: [7, 8, 9],
  );

  print('Alumno: ${alumno.nombre}');
  print('Media: ${alumno.media}');

  alumno.añadirNota = 10;

  print('Nueva media: ${alumno.media}');

  try {
    String curso = await obtenerCurso();

    print('Curso: $curso');
  } catch (e) {
    print('Error: $e');
  }
}
```

En este ejemplo hemos utilizado:

- Clases.
- Constructor con parámetros con nombre.
- `required`.
- `List`.
- Getter.
- Setter.
- Funciones.
- `Future`.
- `async`.
- `await`.
- `try-catch`.

---

# 23. Resumen rápido

| Concepto | Ejemplo |
|---|---|
| Punto de entrada | `main()` |
| Variable | `var nombre = 'Ana';` |
| Tipo | `int edad = 20;` |
| Constante | `final nombre = 'Ana';` |
| Constante de compilación | `const pi = 3.14;` |
| Lista | `List<String>` |
| Diccionario | `Map<String, int>` |
| Conjunto | `Set<String>` |
| Colección iterable | `Iterable<int>` |
| Función | `int sumar(int a, int b)` |
| Parámetro con nombre | `{required String nombre}` |
| Clase | `class Persona {}` |
| Getter | `String get nombreCompleto` |
| Setter | `set edad(int valor)` |
| Operación asíncrona | `Future<T>` |
| Código asíncrono | `async` |
| Esperar resultado | `await` |
| Control de excepciones | `try / on / catch` |
| Secuencia asíncrona | `Stream<T>` |
| Generador asíncrono | `async*` |
| Emitir valores | `yield` |

---

# 24. Lo más importante para empezar con Flutter

No es necesario memorizar toda la sintaxis de Dart antes de comenzar con Flutter.

Para las primeras aplicaciones será especialmente importante dominar:

```dart
main()
```

```dart
var / final / const
```

```dart
List
```

```dart
Map
```

```dart
Set
```

```dart
Iterable
```

```dart
funciones
```

```dart
parámetros con nombre
```

```dart
class
```

```dart
getter / setter
```

```dart
Future
```

```dart
async / await
```

```dart
try / on / catch
```

y, posteriormente:

```dart
Stream
async*
yield
```

Una vez dominados estos conceptos, la sintaxis de Flutter resultará mucho más sencilla, ya que gran parte del código de una aplicación Flutter consiste precisamente en **clases, funciones, parámetros con nombre, colecciones y programación asíncrona**.

