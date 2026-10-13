void main() {
  
  final pokemon = {
    'nombre' : 'Ditto',
    'hp'     : 780,
    'isAlive': true,
    'sprites': {
      1 : 'ditto/front.jpg',
      2 : 'ditto/back.jpg'
    }
  };
  
  print(pokemon);
  print('nombre : ${pokemon['nombre']}');
  print('nombre : ${pokemon['sprites']}');
  
  print('Back : ${pokemon['sprites']}');
}
