package com.example.ceep3.dao

import com.example.ceep3.model.Nota
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList

//object NotaDAO {
//
//    private val notas: ArrayList<Nota> = ArrayList()
//
//    fun todos(): List<Nota> {
//        return notas.clone() as List<Nota>
//    }
//
//    fun insere(nota: Nota) {
//        this.notas.addAll(listOf(nota))
//    }
//
//    fun altera(posicao: Int, nota: Nota) {
//        notas[posicao] = nota
//    }
//
//    fun remove(posicao: Int) {
//        notas.removeAt(posicao)
//    }
//
//    fun troca(posicaoInicio: Int, posicaoFim: Int) {
//        Collections.swap(notas, posicaoInicio, posicaoFim)
//    }
//
//    fun removeTodos() {
//        notas.clear()
//    }
//}