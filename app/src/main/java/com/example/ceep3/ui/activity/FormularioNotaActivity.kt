package com.example.ceep3.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.ceep3.R
import com.example.ceep3.model.Nota
import com.example.ceep3.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import com.example.ceep3.ui.activity.NotaActivityConstantes.CHAVE_POSICAO
import com.example.ceep3.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA

class FormularioNotaActivity : AppCompatActivity() {

    private val TITULO_APP_BAR_INSERE = "Insere Nota"
    private val TITULO_APPBAR_ALTERA = "Altera Nota "
    private var posicaoRecebida = POSICAO_INVALIDA
    private lateinit var titulo: TextView
    private lateinit var descricao: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)

        title = TITULO_APP_BAR_INSERE
        inicializaCampos()

        // Recebe dados da Nota
        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            title = TITULO_APPBAR_ALTERA
            val notaRecebida: Nota = dadosRecebidos
                .getSerializableExtra(CHAVE_NOTA) as Nota
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA)
            preencheCampos(notaRecebida)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota_salva, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (ehMenuSalvaNota(item)) {
            val notaCriada: Nota = criaNota()
            retornaNota(notaCriada)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun preencheCampos(notaRecebida: Nota) {
        titulo.text = notaRecebida.titulo
        descricao.text = notaRecebida.descricao
    }

    private fun inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo)
        descricao = findViewById(R.id.formulario_nota_descricao)
    }

    //resultado da Requisicao - 2
    private fun retornaNota(nota: Nota) {
        val resultadoIncersao = Intent()
        resultadoIncersao.putExtra(CHAVE_NOTA, nota)
        resultadoIncersao.putExtra(CHAVE_POSICAO, posicaoRecebida)
        setResult(Activity.RESULT_OK, resultadoIncersao)
    }

    private fun ehMenuSalvaNota(item: MenuItem): Boolean {
        return item.itemId == R.id.menu_formulario_nota_ic_salva

    }

    private fun criaNota(): Nota {
        return Nota(titulo.text.toString(), descricao.text.toString())
    }
}