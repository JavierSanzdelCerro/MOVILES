void main() {
  // Los Maps son pares de valores
  final Map<String, dynamic> pokemon = {
    'nombre': 'Ditto',
    'hp': 100,
    'isAlive': true,
    'sprites': {1: 'ditto/front.jpg', 2: 'ditto/back.jpg'},
  };

  print(pokemon);
  print('Nombre: ${pokemon['nombre']}');
  print('Nombre: ${pokemon['sprites']}');

  print('Back: ${ pokemon['sprites'][2]}');
  print('Back: ${ pokemon['sprites'][1]}');
}