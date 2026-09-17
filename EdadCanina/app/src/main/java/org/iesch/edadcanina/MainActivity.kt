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

        // Aqui la pantalla está creada


        // 1- Tomamos el control de todos los elementos de la parte de la UI

        val resultText = findViewById<TextView>(R.id.texto_respuesta)
        val calculateButton = findViewById<Button>(R.id.boton_calcular)
        val ageEdit = findViewById<EditText>(R.id.edad_perro)

        // 2- Los botones tienen la propiedad setOnClickListener al pulsarlo
        calculateButton.setOnClickListener {
            // Aqui metemos lo que queremos hacer al pulsar el boton
            val edadString = ageEdit.text.toString()

            if (edadString.isEmpty()){
                // 3- Mostramos un mensaje de tipo Toast
                val mensajeToast =Toast.makeText(this, "Imbecil introduce una edad", Toast.LENGTH_LONG)
                mensajeToast.show()
            }else{
                // Necesitamos pasar el valor a entero
                val edadInt = edadString.toInt()
                val dogAge = edadInt * 7

                // println(dogAge)


            }


        }
    }


}