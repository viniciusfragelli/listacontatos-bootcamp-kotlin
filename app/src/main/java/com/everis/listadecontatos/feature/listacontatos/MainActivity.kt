package com.everis.listadecontatos.feature.listacontatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.listadecontatos.R
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.contato.ContatoActivity
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.singleton.ContatoSingleton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geraListaDeContatos()
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContatoAdapter(this,ContatoSingleton.lista) {onClickItemRecyclerView(it)}
        recyclerView.adapter = adapter
    }

    private fun geraListaDeContatos(){
        ContatoSingleton.lista.add(ContatosVO(1,"Fulano", "(00) 9900-0001"))
        ContatoSingleton.lista.add(ContatosVO(2,"Ciclano", "(00) 9900-0002"))
        ContatoSingleton.lista.add(ContatosVO(3,"Vinicius", "(00) 9900-0001"))
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        var listaFiltrada: List<ContatosVO> = ContatoSingleton.lista
        if(!busca.isNullOrEmpty()){
            listaFiltrada = ContatoSingleton.lista.filter { contato ->
                if (contato.nome.toLowerCase().contains(busca.toLowerCase())){
                    return@filter true
                }
                return@filter false
            }
        }
        adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
        recyclerView.adapter = adapter
        Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()
    }

}
