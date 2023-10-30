package com.example.ceep3

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ceep3.listener.ContextProvider
import com.example.ceep3.model.Nota
import com.example.ceep3.repository.NotaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val contextProvider: ContextProvider = TestContextProvider()
    private val repository = NotaRepository(appContext)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertEquals("com.example.ceep3", appContext.packageName)
    }

    @Test
    fun add_note() {
        val note = Nota("A", "B")
        CoroutineScope(Dispatchers.IO).launch {
            repository.salva(note)
            val notes = repository.todos()
            assertEquals(2, notes.size)
            assertEquals(note, notes[1])
        }
    }

    @Test
    fun update_note() {
        val note = Nota("A", "B")
        CoroutineScope(Dispatchers.IO).launch {
            note.descricao = "Brazil"
            repository.edita(note)
            val notes = repository.todos()
            assertEquals(2, notes.size)
            assertEquals(note, notes[1])
        }
    }
}