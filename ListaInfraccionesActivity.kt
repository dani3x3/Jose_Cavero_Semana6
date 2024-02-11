package com.example.jose_cavero_semana6

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaInfraccionesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var infraccionesAdapter: InfraccionAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        // Inicializar la base de datos
        databaseHelper = DatabaseHelper(this)

        // Inicializar la lista de infracciones
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        infraccionesAdapter = InfraccionAdapter(ArrayList(databaseHelper.getAllInfracciones()))
        recyclerView.adapter = infraccionesAdapter

        // Configurar el listener de clics en el adaptador
        infraccionesAdapter.setOnItemClickListener(object : InfraccionAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Obtener la infracción seleccionada
                val infraccion = infraccionesAdapter.getItem(position)

                // Crear un Intent para abrir la actividad de edición de infracciones
                val intent = Intent(this@ListaInfraccionesActivity, EditarInfraccionActivity::class.java)

                // Pasar la infracción seleccionada como un parámetro extra en el Intent
                intent.putExtra("infraccion", infraccion)

                // Iniciar la actividad de edición de infracciones
                startActivity(intent)
            }
        })
    }
}


