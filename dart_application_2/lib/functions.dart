void main(){
  print(saludar());

  print(saludoCompleto(nombre: 'Alberto', mensaje: 'Que te den'));
}

String Saludar(){
  return 'Hola Javier';
}

// Arrow function
String saludar() => 'Hola Alberto';

// Metoedo con valore opcionales
int sumarOpcional(int a, [int b = 0]){
  return a + b;
}

// Parametros con nombre
String saludoCompleto({required String nombre, String mensaje = 'HOLA!!'}){
  return '$mensaje Javier';
}