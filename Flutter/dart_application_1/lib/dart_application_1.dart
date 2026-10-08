void main() {
  
    List<String> alumnos = [
      'Ana',
      'Luis',
      'Marta'
    ];
    alumnos.add('Pedro');
    for ( var alumno in alumnos ){
      print( alumno );
    }
  }
