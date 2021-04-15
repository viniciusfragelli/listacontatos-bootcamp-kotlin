package com.everis.listadecontatos.feature.listacontatos.repository

import com.everis.listadecontatos.bases.BaseRepository
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.helpers.HelperDB
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_ID
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_NOME
import com.everis.listadecontatos.helpers.HelperDB.Companion.COLUMNS_TELEFONE
import com.everis.listadecontatos.helpers.HelperDB.Companion.TABLE_NAME
import java.sql.SQLDataException

class ListaDeContatosRepository(
    var helperDBPar: HelperDB? = null
) : BaseRepository(helperDBPar) {
    fun requestBuscaListaDeContatos(
        busca: String,
        isBuscaPorID: Boolean = false,
        onSucess: ((List<ContatosVO>)->Unit),
        onError: ((Exception)->Unit)
    ){
        try {
            val db = readableDatabase
            var lista = mutableListOf<ContatosVO>()
            var where: String? = null
            var args: Array<String> = arrayOf()
            if (isBuscaPorID) {
                where = "$COLUMNS_ID = ?"
                args = arrayOf("$busca")
            } else {
                where = "$COLUMNS_NOME LIKE ?"
                args = arrayOf("%$busca%")
            }
            var cursor = db?.query(TABLE_NAME, null, where, args, null, null, null)
            if (cursor == null) {
                db?.close()
                onError(SQLDataException("Não foi possível fazer sua busca"))
                return
            }
            while (cursor.moveToNext()) {
                var contato = ContatosVO(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
                )
                lista.add(contato)
            }
            db?.close()
            onSucess(lista)
        }catch (ex: Exception){
            onError(ex);
        }
    }
}