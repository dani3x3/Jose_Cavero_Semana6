package com.example.jose_cavero_semana6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarInfraccionActivity : AppCompatActivity() {

    private lateinit var rutEditText: EditText
    private lateinit var comercioNameEditText: EditText
    private lateinit var comercioAddressEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var guardarButton: Button
    private lateinit var buttonShared: Button

    private lateinit var infraccion: Infraccion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_infraccion)

        // Inicializar las vistas
        rutEditText = findViewById(R.id.rutEditText)
        comercioNameEditText = findViewById(R.id.comercioNameEditText)
        comercioAddressEditText = findViewById(R.id.comercioAddressEditText)
        descripcionEditText = findViewById(R.id.descripcionEditText)
        guardarButton = findViewById(R.id.guardarButton)
        buttonShared = findViewById(R.id.buttonshared)

        // Obtener la infracción del Intent
        infraccion = intent.getSerializableExtra("infraccion") as Infraccion

        // Establecer los valores de la infracción en los campos de entrada
        rutEditText.setText(infraccion.rutInspector)
        comercioNameEditText.setText(infraccion.comercioName)
        comercioAddressEditText.setText(infraccion.comercioAddress)
        descripcionEditText.setText(infraccion.descripcion)

        // Configurar el evento de clic para el botón de guardar
        guardarButton.setOnClickListener {
            // Obtener los valores actualizados de los campos de entrada
            val rut = rutEditText.text.toString()
            val comercioName = comercioNameEditText.text.toString()
            val comercioAddress = comercioAddressEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()

            // Actualizar la infracción en la base de datos
            val databaseHelper = DatabaseHelper(this)
            databaseHelper.updateInfraccion(infraccion.folio, rut, comercioName, comercioAddress, descripcion)

            // Volver a la pantalla anterior
            finish()
        }

        // Configurar el evento de clic para el botón de compartir datos
        buttonShared.setOnClickListener {
            // Lógica para compartir los datos
            shareData()
        }
    }

    private fun shareData() {
        // Obtener los datos de los EditText
        val rut = rutEditText.text.toString()
        val comercioName = comercioNameEditText.text.toString()
        val comercioAddress = comercioAddressEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()

        // Crear el texto a compartir
        val text = "RUT: $rut\nNombre del comercio: $comercioName\nDirección del comercio: $comercioAddress\nDescripción: $descripcion"

        // Crear un Intent para compartir los datos
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        // Iniciar la actividad para compartir los datos
        startActivity(Intent.createChooser(shareIntent, "Compartir datos con"))
    }
}
