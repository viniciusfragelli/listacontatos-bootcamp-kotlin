package com.everis.listadecontatos

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import com.everis.listadecontatos.helpers.HelperDB
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    var repository: ListaDeContatosRepository? = null
    lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        repository = ListaDeContatosRepository(
            HelperDB(context)
        )
    }

    @Test
    fun testRepository() {
        repository?.requestBuscaListaDeContatos(
            "teste",
            false,
            onSucess = { list ->
                assertNotNull(list)
                assertFalse(list.isEmpty())
                assertEquals(list.size,2)
            },
            onError = {
                fail("Retornou excessão do repositorio!")
            }
        )
    }
}
