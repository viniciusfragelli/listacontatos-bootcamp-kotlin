package com.everis.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.activity_contato.*
import kotlinx.android.synthetic.main.activity_contato.toolBar

class ContatoActivity : BaseActivity() {

    private var idContato: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, "Contato",true)
        setupContato()
        btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato(){
        idContato = intent.getIntExtra("index",-1)
        if (idContato == -1){
            btnExcluirContato.visibility = View.GONE
            return
        }
        var lista = ContatoApplication.instance.helperDB?.buscarContatos("$idContato",true) ?: return
        var contato = lista.getOrNull(0) ?: return
        etNome.setText(contato.nome)
        etTelefone.setText(contato.telefone)
    }

    private fun onClickSalvarContato(){
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        if(idContato == -1) {
            ContatoApplication.instance.helperDB?.salvarContato(contato)
        }else{
            ContatoApplication.instance.helperDB?.updateContato(contato)
        }
        finish()
    }

    fun onClickExcluirContato(view: View) {
        if(idContato > -1){
            ContatoApplication.instance.helperDB?.deletarCoontato(idContato)
            finish()
        }
    }
}
