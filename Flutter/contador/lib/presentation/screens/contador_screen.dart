import 'package:contador/presentation/widgets/boton_personalizado.dart';
import 'package:flutter/material.dart';

class ContadorScreen extends StatefulWidget {
  const new({super.key});

  @override
  State<ContadorScreen> createState() => _ContadorScreenState();
}

class _ContadorScreenState extends State<ContadorScreen> {
  int contador = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Contador App'), centerTitle: true),
      body: Center(
        child: Column(
          mainAxisAlignment: .center,
          children: [
            Text(
              '$contador',
              style: TextStyle(fontSize: 160, fontWeight: .w100),
            ),
            Text('Clicks', style: TextStyle(fontSize: 25)),
          ],
        ),
      ),
      floatingActionButton: Column(
        mainAxisAlignment: .end,
        children: [
          BotonPersonalizado(icono: Icons.refresh_rounded,),
          SizedBox(height: 10,),
          BotonPersonalizado(icono: Icons.plus_one,),
          SizedBox(height: 10,),
          BotonPersonalizado(icono: Icons.exposure_minus_1,),
        ],
      ),
    );
  }
}

