package com.example.ceep3.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep3.R
import com.example.ceep3.database.NotaDatabase
import com.example.ceep3.database.dao.NotaDao
import com.example.ceep3.model.Nota
import com.example.ceep3.repository.NotaRepository
import com.example.ceep3.repository.coroutine.MainViewModel
import com.example.ceep3.ui.activity.NotaActivityConstants.CHAVE_NOTA
import com.example.ceep3.ui.activity.NotaActivityConstants.CHAVE_POSICAO
import com.example.ceep3.ui.activity.NotaActivityConstants.CODIGO_REQUISICAO_ALTERA_NOTA
import com.example.ceep3.ui.activity.NotaActivityConstants.CODIGO_REQUISICAO_INSERE_NOTA
import com.example.ceep3.ui.activity.NotaActivityConstants.POSICAO_INVALIDA
import com.example.ceep3.ui.recyclerview.adapter.ListaNotasAdapter
import com.example.ceep3.ui.recyclerview.adapter.listener.OnItemClickListener
import com.example.ceep3.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback

class MainActivity : AppCompatActivity() {

    private val TITULO_APPBAR: String = "Notas"
    private lateinit var adapter: ListaNotasAdapter
    private lateinit var dao: NotaDao
    private lateinit var botaoInsereNota: TextView
    private lateinit var listaNota: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = TITULO_APPBAR
        initView()
        configuraRecycleView()
        configuraBotaoInsereNota()

        val db = NotaDatabase.getInstance(this)
        dao = db.getRoomDatabase()

        mainViewModel = ViewModelProvider(
            this,
            MainViewModel.MainViewModelFacotory(NotaRepository(this))
        )[MainViewModel::class.java]
    }

    private fun initView() {
        botaoInsereNota = findViewById(R.id.lista_notas_insere_notas)
        listaNota = findViewById(R.id.lista_notas_recyclerview)
    }

    private fun obsNewNota() {
        mainViewModel.notaLiveData.observe(this) {
            it?.run {
                adapter.atualiza(it)
            }
        }
    }

    private fun obsUpdateNota(posicao: Int) {
        mainViewModel.mNota.observe(this) {
            it?.run {
                adapter.altera(posicao, it)
            }
        }
    }

    private fun configuraBotaoInsereNota() {
        botaoInsereNota.setOnClickListener {
            vaiParaFormularioNotaActivityInsere()
        }
    }

    private fun vaiParaFormularioNotaActivityInsere() {
        val iniciaFormularioNota = Intent(this@MainActivity, FormularioNotaActivity::class.java)
        // Envia Requisicao - 1
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA)
    }

    //Identifica se a requisicao foi atendida - 3
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                val notaRecebida = data!!.getSerializableExtra(CHAVE_NOTA) as Nota
                mainViewModel.salva(notaRecebida)
                obsNewNota()
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                if (data != null) {
                    val notaRecebida: Nota = data.getSerializableExtra(CHAVE_NOTA) as Nota
                    val posicaoRecebida: Int = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA)
                    if (ehPosicaoValida(posicaoRecebida)) {
                        mainViewModel.edita(notaRecebida)
                        obsUpdateNota(posicaoRecebida)
                    } else {
                        Toast.makeText(
                            this,
                            "Ocorreu um problema na alteracao da Nota",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

//    private fun altera(posicao: Int, nota: Nota) {
//        mainViewModel.edita(nota)
////        NotaDAO.altera(posicao, nota)
//        obsUpdateNota(posicao)
//    }


    private fun ehPosicaoValida(posicaoRecebida: Int) = posicaoRecebida > POSICAO_INVALIDA

    private fun ehResultadoAlteraNota(
        requestCode: Int,
        data: Intent?
    ): Boolean {
        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data)
    }

    private fun ehCodigoRequisicaoAlteraNota(requestCode: Int) =
        requestCode == CODIGO_REQUISICAO_ALTERA_NOTA

//    private fun adiciona(notaRecebida: Nota) {
//        mainViewModel.salva(notaRecebida)
////        NotaDAO.insere(notaRecebida)
//        obsNewNota()
//    }

    private fun ehResultadoInsereNota(
        requestCode: Int,
        data: Intent?
    ): Boolean {
        return ehCodigoRequisicaoInsereNota(requestCode) && temNota(
            data
        )
    }

    private fun temNota(data: Intent?): Boolean {
        return (data != null) && data.hasExtra(CHAVE_NOTA);
    }

    private fun resultadoOk(resultCode: Int): Boolean {
        return resultCode == Activity.RESULT_OK
    }

    private fun ehCodigoRequisicaoInsereNota(requestCode: Int): Boolean {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA
    }

    private fun configuraRecycleView() {
        configuraAdapter(listaNota)
        configuraItemTouchHelper(listaNota)
    }

    //Animacao - Deslizar para esquerda e direita
    private fun configuraItemTouchHelper(listaNotas: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(NotaItemTouchHelperCallback(adapter, this))
        itemTouchHelper.attachToRecyclerView(listaNotas)
    }

    private fun configuraAdapter(listaNotas: RecyclerView) {
        adapter = ListaNotasAdapter(this)
        listaNotas.adapter = adapter
        //abrir Formulario
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(nota: Nota, posicao: Int) {
                //Abre Formulario com nota
                vaiParaFormularioNotaActivityAltera(nota, posicao)
            }
        })
    }

    private fun vaiParaFormularioNotaActivityAltera(nota: Nota, posicao: Int) {
        val abreFormularioComNota =
            Intent(this@MainActivity, FormularioNotaActivity::class.java)
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota)
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao)
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.todos()
        obsNewNota()
    }
}