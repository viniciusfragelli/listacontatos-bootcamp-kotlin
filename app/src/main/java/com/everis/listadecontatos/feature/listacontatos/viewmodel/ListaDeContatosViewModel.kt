package com.everis.listadecontatos.feature.listacontatos.viewmodel

import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import com.everis.listadecontatos.helpers.HelperDB

open class ListaDeContatosViewModel(
    var helperDB: HelperDB? = null,
    var repository: ListaDeContatosRepository? = ListaDeContatosRepository(helperDB)
) {
    open fun doBuscarListaDeContatos(
        busca: String,
        onSucess: ((List<ContatosVO>)->Unit),
        onError: ((Exception)->Unit)
    ){
        Thread(Runnable {
            Thread.sleep(1500)
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