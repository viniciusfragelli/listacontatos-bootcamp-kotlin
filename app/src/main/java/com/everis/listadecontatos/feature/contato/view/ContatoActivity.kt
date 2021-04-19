package com.everis.listadecontatos.feature.contato.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.everis.listadecontatos.R
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.contato.viewmodel.ContatoViewModel
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.helpers.HelperDB
import kotlinx.android.synthetic.main.activity_contato.*

class ContatoActivity : BaseActivity() {

    private var idContato: Int = -1
    var viewModel: ContatoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        viewModel = ContatoViewModel(
            helperDB = HelperDB(this)
        )
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
        progress.visibility = View.VISIBLE
        viewModel?.getContato(
            idContato,
            onSucesso = { contato ->
                runOnUiThread {
                    etNome.setText(contato.nome)
                    etTelefone.setText(contato.telefone)
                    progress.visibility = View.GONE
                }
            },onError = {
                showError("Não foi possível completar sua solicitação tente novamente mais tarde!")
            }
        )

    }

    private fun onClickSalvarContato(){
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        progress.visibility = View.VISIBLE
        val isUpdate = idContato != -1
        viewModel?.saveContato(
            contato,
            isUpdate,
            onSucesso = {
                runOnUiThread {
                    progress.visibility = View.GONE
                    finish()
                }
            }, onError = {
                showError("Não foi possível salvar o contato!")
            }
        )
    }

    private fun showError(msg: String) {
        runOnUiThread {
            progress.visibility = View.GONE
            var alert = AlertDialog
                .Builder(this)
                .setTitle("Atenção")
                .setCancelable(false)
                .setMessage(msg)
                .setPositiveButton("OK") { dialog, k ->
                    dialog.dismiss()
                    finish()
                }
                .show()
        }
    }

    fun onClickExcluirContato(view: View) {
        if(idContato > -1){
            progress.visibility = View.VISIBLE
            viewModel?.deleteContato(
                idContato,
                onSucesso = {
                    runOnUiThread {
                        progress.visibility = View.GONE
                        finish()
                    }
                },onError = {
                    showError("Não foi possível excluir o contato!")
                }
            )
        }
    }
}
