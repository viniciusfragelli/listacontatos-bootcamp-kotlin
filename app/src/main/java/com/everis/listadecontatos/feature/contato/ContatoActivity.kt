package com.everis.listadecontatos.feature.contato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.everis.listadecontatos.R
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.singleton.ContatoSingleton
import kotlinx.android.synthetic.main.activity_contato.*
import kotlinx.android.synthetic.main.activity_contato.toolBar

class ContatoActivity : BaseActivity() {

    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, "Contato",true)
        setupContato()
        btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato(){
        index = intent.getIntExtra("index",-1)
        if (index == -1){
            btnExcluirContato.visibility = View.GONE
            return
        }
        etNome.setText(ContatoSingleton.lista[index].nome)
        etTelefone.setText(ContatoSingleton.lista[index].telefone)
    }

    private fun onClickSalvarContato(){
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            0,
            nome,
            telefone
        )
        if(index == -1) {
            ContatoSingleton.lista.add(contato)
        }else{
            ContatoSingleton.lista.set(index,contato)
        }
        finish()
    }

    fun onClickExcluirContato(view: View) {
        if(index > -1){
            ContatoSingleton.lista.removeAt(index)
            finish()
        }
    }
}
