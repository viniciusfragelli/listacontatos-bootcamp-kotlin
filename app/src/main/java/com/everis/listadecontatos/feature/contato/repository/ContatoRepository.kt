package com.everis.listadecontatos.feature.contato.repository

import android.content.ContentValues
import com.everis.listadecontatos.bases.BaseRepository
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.helpers.HelperDB
import java.sql.SQLDataException

class ContatoRepository(helperDB: HelperDB?) : BaseRepository(helperDB) {

    fun getContato(
        idContato: Int,
        onSucesso: (ContatosVO) -> Unit,
        onError: (Exception) -> Unit
    ){
        try {
            val db = readableDatabase
            var lista = mutableListOf<ContatosVO>()
            var where: String? = null
            var args: Array<String> = arrayOf()
            where = "${HelperDB.COLUMNS_ID} = ?"
            args = arrayOf("$idContato")
            var cursor = db?.query(HelperDB.TABLE_NAME, null, where, args, null, null, null)
            if (cursor == null) {
                db?.close()
                onError(SQLDataException("Não foi possível concluir sua solicitação ao banco de dados"))
                return
            }
            while (cursor.moveToNext()) {
                var contato = ContatosVO(
                    cursor.getInt(cursor.getColumnIndex(HelperDB.COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(HelperDB.COLUMNS_NOME)),
                    cursor.getString(cursor.getColumnIndex(HelperDB.COLUMNS_TELEFONE))
                )
                lista.add(contato)
            }
            db?.close()
            var contato = lista.getOrNull(0)
            if (contato != null) onSucesso(contato)
            else onError(SQLDataException("Não conseguimos resgastas o contato!"))
        }catch (ex: Exception){
            ex.printStackTrace()
            onError(SQLDataException("Não foi possível completar sua solicitação!"))
        }
    }

    fun saveContato(
        contato: ContatosVO,
        onSucesso: () -> Unit,
        onError: (Exception) -> Unit
    ){
        try {
            val db = writableDatabase
            if (db == null) onError(SQLDataException("Bão foi possível se conectar ao base de dados"))
            var content = ContentValues()
            content.put(HelperDB.COLUMNS_NOME, contato.nome)
            content.put(HelperDB.COLUMNS_TELEFONE, contato.telefone)
            db?.insert(HelperDB.TABLE_NAME, null, content)
            db?.close()
            onSucesso()
        }catch (ex: Exception){
            ex.printStackTrace()
            onError(SQLDataException("Não foi possível completar sua solicitação!"))
        }
    }

    fun updateContato(
        contato: ContatosVO,
        onSucesso: () -> Unit,
        onError: (Exception) -> Unit
    ){
        try {
            val db = writableDatabase
            if (db == null) onError(SQLDataException("Bão foi possível se conectar ao base de dados"))
            val sql =
                "UPDATE ${HelperDB.TABLE_NAME} SET ${HelperDB.COLUMNS_NOME} = ?, ${HelperDB.COLUMNS_TELEFONE} = ? WHERE ${HelperDB.COLUMNS_ID} = ?"
            val arg = arrayOf(contato.nome, contato.telefone, contato.id)
            db?.execSQL(sql, arg)
            db?.close()
            onSucesso()
        }catch (ex: Exception){
            ex.printStackTrace()
            onError(SQLDataException("Não foi possível completar sua solicitação!"))
        }
    }

    fun deleteContato(
        id: Int,
        onSucesso: () -> Unit,
        onError: (Exception) -> Unit
    ){
        try {
            val db = writableDatabase
            if (db == null) onError(SQLDataException("Bão foi possível se conectar ao base de dados"))
            val sql = "DELETE FROM ${HelperDB.TABLE_NAME} WHERE ${HelperDB.COLUMNS_ID} = ?"
            val arg = arrayOf("$id")
            db?.execSQL(sql, arg)
            db?.close()
            onSucesso()
        }catch (ex: Exception){
            ex.printStackTrace()
            onError(SQLDataException("Não foi possível completar sua solicitação!"))
        }
    }
}