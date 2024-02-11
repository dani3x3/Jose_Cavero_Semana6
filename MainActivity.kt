package com.example.jose_cavero_semana6

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las referencias a los elementos de la interfaz de usuario
        val rutInspectorEditText = findViewById<EditText>(R.id.editTextNumberrut)
        val comercioNameEditText = findViewById<EditText>(R.id.editTextnamecomercio)
        val comercioAddressEditText = findViewById<EditText>(R.id.editTextdircomercio)
        val infractionTextView = findViewById<EditText>(R.id.editTextTextMultiLine)
        val registerButton = findViewById<Button>(R.id.button)

        // Crear una instancia de DatabaseHelper
        val dbHelper = DatabaseHelper(this)

        // Configurar el evento de clic para el botón Registrar Infracción
        registerButton.setOnClickListener {
            val rutInspector = rutInspectorEditText.text.toString()
            val comercioName = comercioNameEditText.text.toString()
            val comercioAddress = comercioAddressEditText.text.toString()
            val infraction = infractionTextView.text.toString()

            // Llamar al método addInfraccion() de DatabaseHelper para guardar la infracción en la base de datos
            dbHelper.addInfraccion(rutInspector, comercioName, comercioAddress, infraction)

            // Obtener el último folio registrado
            val lastFolio = dbHelper.getLastFolio()

            // Mostrar un Toast con el mensaje durante 2 segundos
            val message = "Infracción Registrada, folio: $lastFolio, nombre del comercio: $comercioName"
            showToast(message, 2000)

            // Limpiar los campos de texto después de registrar la infracción
            rutInspectorEditText.text.clear()
            comercioNameEditText.text.clear()
            comercioAddressEditText.text.clear()
            infractionTextView.text.clear()
        }

        // Obtener una referencia al botón "Lista de Infracciones"
        val buttonListInfra = findViewById<Button>(R.id.buttonlistinfra)

// Configurar el evento de clic para el botón "Lista de Infracciones"
        buttonListInfra.setOnClickListener {
            // Crear un Intent para cambiar a la pantalla de lista de infracciones
            val intent = Intent(this, ListaInfraccionesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String, duration: Int) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()

        Handler().postDelayed({
            toast.cancel()
        }, duration.toLong())
    }
}
