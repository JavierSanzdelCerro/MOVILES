void main(){

  final wolverine = Heroe(nombre: 'Lobezno', poder: '8500');

  print(wolverine.nombre);
  print(wolverine.poder);

}

class Heroe {
  String nombre;
  String poder;

  Heroe({
    required this.nombre,
    this.poder = 'Sin poder'
  });
}