package com.everis.listadecontatos.feature.listacontatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.listadecontatos.R
import com.everis.listadecontatos.feature.contato.ContatoActivity
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.singleton.ContatoSingleton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter:ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ContatoSingleton.lista.add(ContatosVO(1,"Teste 1", "000000"))
        ContatoSingleton.lista.add(ContatosVO(2,"Teste 2", "000000"))
        ContatoSingleton.lista.add(ContatosVO(3,"Teste 3", "000000"))
        adapter = ContatoAdapter(this,ContatoSingleton.lista) {onClickItemRecyclerView(it)}
        recyclerView.adapter = adapter
        fab.setOnClickListener { onClickAdd() }
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
}
