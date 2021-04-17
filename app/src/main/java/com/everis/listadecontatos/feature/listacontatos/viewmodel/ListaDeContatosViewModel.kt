package com.everis.listadecontatos.feature.listacontatos.viewmodel

import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import com.everis.listadecontatos.helpers.HelperDB

class ListaDeContatosViewModel(
    var helperDB: HelperDB? = null,
    var repository: ListaDeContatosRepository? = ListaDeContatosRepository(helperDB)
) {
    fun doBuscarListaDeContatos(
        busca: String,
        onSucess: ((List<ContatosVO>)->Unit),
        onError: ((Exception)->Unit)
    ){
        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<ContatosVO> = mutableListOf()
            repository?.requestBuscaListaDeContatos(
                busca,
                onSucess = {
                    onSucess(it)
                },
                onError = {

                    onError(it)
                }
            )
        }).start()
    }
}