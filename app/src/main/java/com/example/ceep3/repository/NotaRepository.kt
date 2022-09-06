package com.example.ceep3.repository

import android.content.Context
import com.example.ceep3.database.NotaDatabase
import com.example.ceep3.database.dao.NotaDao
import com.example.ceep3.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotaRepository(context: Context) : NotaDao {

    private val dao = NotaDatabase.getInstance(context).getRoomDatabase()

    override suspend fun salva(nota: Nota) {
        withContext(Dispatchers.Default) {
            dao.salva(nota)
        }
    }

    override suspend fun delete(nota: Nota) {
        withContext(Dispatchers.Default) {
            dao.delete(nota)
        }
    }

    override suspend fun edita(nota: Nota) {
        withContext(Dispatchers.Default) {
            dao.edita(nota)
        }
    }

    override suspend fun todos(): List<Nota> {
        return withContext(Dispatchers.Default) {
            dao.todos()
        }
    }

}