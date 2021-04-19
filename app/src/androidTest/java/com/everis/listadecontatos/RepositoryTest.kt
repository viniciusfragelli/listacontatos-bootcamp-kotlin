package com.everis.listadecontatos

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import com.everis.listadecontatos.helpers.HelperDB
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTest {

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
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.everis.listadecontatos", appContext.packageName)
    }

    @Test
    fun testRepository() {
        val lock = CountDownLatch(1);
        var lista: List<ContatosVO>? = null
        repository?.requestBuscaListaDeContatos(
            "teste",
            onSucess = { list ->
                lista = list
                lock.countDown()
            },
            onError = {
                fail("Retornou excessão do repositorio!")
            }
        )
        lock.await(2000, TimeUnit.MILLISECONDS)
        assertNotNull(lista)
        assertFalse(lista!!.isEmpty())
        assertEquals(lista!!.size,2)
    }

    @Test
    fun testViewModel() {
        val lock = CountDownLatch(1);
        var lista: List<ContatosVO>? = null
        repository?.requestBuscaListaDeContatos(
            "teste",
            onSucess = { list ->
                lista = list
                lock.countDown()
            },
            onError = {
                fail("Retornou excessão do repositorio!")
            }
        )
        lock.await(2000, TimeUnit.MILLISECONDS)
        assertNotNull(lista)
        assertFalse(lista!!.isEmpty())
        assertEquals(lista!!.size,2)
    }
}
