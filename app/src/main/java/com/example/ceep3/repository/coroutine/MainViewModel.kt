package com.example.ceep3.repository.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.ceep3.model.Nota
import com.example.ceep3.repository.NotaRepository
import kotlinx.coroutines.launch

open class MainViewModel(private val notaRepository: NotaRepository) : ViewModel() {

    val notaLiveData = MutableLiveData<List<Nota>>()
    val mNota = MutableLiveData<Nota>()

    fun salva(nota: Nota) {
        viewModelScope.launch {
            notaRepository.salva(nota)
            mNota.value = nota
        }
    }

    fun delete(nota: Nota) {
        viewModelScope.launch {
            notaRepository.delete(nota)
            mNota.value = nota
        }
    }

    fun edita(nota: Nota) {
        viewModelScope.launch {
            notaRepository.edita(nota)
            mNota.value = nota
        }
    }

    fun todos() {
        viewModelScope.launch {
            notaLiveData.value = notaRepository.todos()
        }
    }

    class MainViewModelFacotory(private val notaRepository: NotaRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(notaRepository) as T

        }
    }

}