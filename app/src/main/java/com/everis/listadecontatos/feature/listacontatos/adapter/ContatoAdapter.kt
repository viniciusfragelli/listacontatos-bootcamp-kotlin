package com.everis.listadecontatos.feature.listacontatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everis.listadecontatos.R
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.item_contato.view.*

class ContatoAdapter(
    private val context: Context,
    private val lista: List<ContatosVO>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ContatoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contato,parent,false)
        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = lista[position]
        with(holder.itemView){
            tvNome.text = contato.nome
            tvTelefone.text = contato.telefone
            llItem.setOnClickListener { onClick(contato.id) }
        }
    }

}

class ContatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)