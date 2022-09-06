package com.example.ceep3.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep3.R
import com.example.ceep3.model.Nota
import com.example.ceep3.ui.recyclerview.adapter.listener.OnItemClickListener
import java.util.*
import kotlin.collections.ArrayList

class ListaNotasAdapter(private var context: Context) :
    RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder>() {

    private val notas: ArrayList<Nota> = ArrayList()

    //abrir formulario
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaNotasAdapter.NotaViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ListaNotasAdapter.NotaViewHolder, position: Int) {
        val nota: Nota = notas[position]
        holder.vincula(nota)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    inner class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView
        private val descricao: TextView
        private lateinit var nota: Nota

        init {
            titulo = itemView.findViewById(R.id.item_nota_titulo)
            descricao = itemView.findViewById(R.id.item_nota_descricao)

            //abrir formulario
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(nota, adapterPosition)
            }
        }

        fun vincula(nota: Nota) {
            this.nota = nota
            preencheCampos(nota)
        }

        private fun preencheCampos(nota: Nota) {
            titulo.text = nota.titulo
            descricao.text = nota.descricao
        }

    }

    //adiciona itens no adapter
    fun adiciona(nota: Nota) {
        notas.add(nota)
        notifyDataSetChanged()
    }

    fun altera(posicao: Int, nota: Nota) {
        notas[posicao] = nota
        notifyDataSetChanged()
    }

    fun remove(posicao: Int) {
        notas.removeAt(posicao)
        notifyItemRemoved(posicao)
    }

    fun atualiza(nota: List<Nota>){
        this.notas.clear()
        this.notas.addAll(nota)
        notifyDataSetChanged()
    }

    fun troca(posicaoInicial: Int, posicaoFinal: Int) {
        Collections.swap(notas, posicaoInicial, posicaoFinal)
        notifyItemMoved(posicaoInicial, posicaoFinal)
    }

    fun getItem(posicao: Int) =
        this.notas[posicao]
}