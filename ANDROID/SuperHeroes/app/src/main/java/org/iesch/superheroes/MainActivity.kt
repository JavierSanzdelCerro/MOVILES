package org.iesch.superheroes

import android.content.Intent
import android.health.connect.datatypes.units.Power
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.iesch.superheroes.databinding.ActivityMainBinding
import org.iesch.superheroes.model.SuperHeroe

class MainActivity : AppCompatActivity() {

    // 1 - Creamos la variable lateinit porque la vamos a inicializar luego
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 2 - Inicializamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 3 - Usamos el binding para inflar la vista
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // A partir de aqui introduzo el codigo necesario
        // val botonGuardar = findViewById<Button>(R.id.btn_guardar)
        binding.btnGuardar.setOnClickListener {
            // Obtenemos los valores al momento de hacer click
            val superHeroName = binding.heroNameEdit.text.toString()
            val alterEgo = binding.alterEgoEdit.text.toString()
            val bio = binding.alterEgoEdit.text.toString()
            val power = binding.power.rating
            // 2 - Me creo el objeto SuperHeroe
            val superHeroe = SuperHeroe(superHeroName,alterEgo,bio,power)

            // Qué quiero hacer cuando pulso el Boton Guardar
            irADetailActivity(superHeroe)
        }




    }

    fun irADetailActivity(superHeroe: SuperHeroe) {
        // Creamos el objeto Intent
        val intent = Intent(this, DetailActivity::class.java)
        // Añadimos todos los campos con el metodo putExtra
        //intent.putExtra("superHeroName", superHeroName)
        //intent.putExtra("alterEgo", alterEgo)
        //intent.putExtra("bio", bio)
        //intent.putExtra("power",power)
        intent.putExtra( "superHero", superHeroe )
        // De esta manera, todos estos datos se enviarán al DetailActivity
        // Iniciamos la nueva actividad
        startActivity(intent)
    }


}