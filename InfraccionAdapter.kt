package com.example.jose_cavero_semana6
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfraccionAdapter(private val infracciones: List<Infraccion>) : RecyclerView.Adapter<InfraccionAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    // Define una interfaz para el listener de clics
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Método para establecer el listener de clics
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_infraccion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infraccion = infracciones[position]
        holder.bind(infraccion)
    }

    override fun getItemCount(): Int {
        return infracciones.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val folioTextView: TextView = itemView.findViewById(R.id.folioTextView)
        private val comercioNameTextView: TextView = itemView.findViewById(R.id.comercioTextView)
        private val rutInspectorTextView: TextView = itemView.findViewById(R.id.rutTextView)

        init {
            // Configura el listener de clics para los elementos
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }

        fun bind(infraccion: Infraccion) {
            folioTextView.text = infraccion.folio
            comercioNameTextView.text = infraccion.comercioName
            rutInspectorTextView.text = infraccion.rutInspector
        }
    }

    // Método para obtener una infracción en una posición específica
    fun getItem(position: Int): Infraccion {
        return infracciones[position]
    }
}


