package com.everis.listadecontatos.singleton

import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

object ContatoSingleton {
    var lista: MutableList<ContatosVO> = mutableListOf()
}