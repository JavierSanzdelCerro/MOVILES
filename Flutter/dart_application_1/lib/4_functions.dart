void main() {
  print( saludar()   );

  print( sumar(2, 7));

  print( sumarOpcional(5, 7));


  print( saludoCompleto( 
    mensaje: 'Que te den! ' , 
    nombre: 'Javi!!'
    ) );
}

String saludar() => 'Hola Alberto!';
int sumar(int a, int b) => a + b;

int sumarOpcional( int a, [ int b = 0 ]){
  return a + b;
}
// Parámetros con nombre
String saludoCompleto({ required String nombre, String mensaje = 'HOLA!!, ' }){
    return '$mensaje $nombre !';
}
