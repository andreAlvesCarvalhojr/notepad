package com.example.ceep3.ui.recyclerview.adapter.listener

import com.example.ceep3.model.Nota

interface OnItemClickListener {

    fun onItemClick(nota: Nota, posicao: Int)
}