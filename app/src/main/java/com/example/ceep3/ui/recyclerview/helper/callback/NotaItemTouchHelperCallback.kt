package com.example.ceep3.ui.recyclerview.helper.callback

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep3.database.NotaDatabase
import com.example.ceep3.database.dao.NotaDao
import com.example.ceep3.model.Nota
import com.example.ceep3.repository.NotaRepository
import com.example.ceep3.repository.coroutine.MainViewModel
import com.example.ceep3.ui.recyclerview.adapter.ListaNotasAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotaItemTouchHelperCallback(private val adapter: ListaNotasAdapter, context: Context) :
    ItemTouchHelper.Callback() {

    private val dao: NotaDao = NotaDatabase.getInstance(context).getRoomDatabase()

    //define para onde movimentar(Esquerda,Direita,Cima,Baixo)
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val marcacoesDeDeslize = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        val marcacoesDeArraste =
            ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        return makeMovementFlags(marcacoesDeArraste, marcacoesDeDeslize)
    }

    //move/ajusta as notas
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val posicaoInicial = viewHolder.adapterPosition
        val posicaoFinal = target.adapterPosition
        trocaNotas(posicaoInicial, posicaoFinal)
        return true
    }

    private fun trocaNotas(posicaoInicial: Int, posicaoFinal: Int) {
//        NotaDAO.troca(posicaoInicial, posicaoFinal)
//        adapter.troca(posicaoInicial, posicaoFinal)
        TODO()
    }

    //Desliza
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val posicaoDaNotaDeslizada = viewHolder.adapterPosition
        removeNota(posicaoDaNotaDeslizada)

    }

    private fun removeNota(posicao: Int) {
        val notaEscolhida: Nota = adapter.getItem(posicao)
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(notaEscolhida)
            withContext(Dispatchers.Main) {
                adapter.remove(posicao)
            }
        }
//        NotaDAO.remove(posicao)
}

}
