package com.everis.listadecontatos

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.view.ListaDeContatosActivity
import com.everis.listadecontatos.feature.listacontatos.viewmodel.ListaDeContatosViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import java.lang.Exception


@RunWith(AndroidJUnit4::class)
class TelaTest {

    @get:Rule
    var activity: ActivityTestRule<ListaDeContatosActivity> = ActivityTestRule(ListaDeContatosActivity::class.java,true,false);

    @Mock
    lateinit var viewModel: ListaDeContatosViewModel

    @Before
    fun setUp() {
    }

    fun setupMockOnSucesso(){
        viewModel = Mockito.mock(ListaDeContatosViewModel::class.java)
        Mockito.`when`(viewModel.doBuscarListaDeContatos(
            Mockito.anyString(),
            MockitoHelper.anyObject(),
            MockitoHelper.anyObject()
        )).thenAnswer {
            val onSucesso = it.arguments[1] as ((List<ContatosVO>)->Unit)
            var list = mutableListOf<ContatosVO>()
            list.add(ContatosVO(1,"Teste 1","Teste 2"))
            onSucesso(list)
            ""
        }
    }

    fun setupMockOnError(){
        viewModel = Mockito.mock(ListaDeContatosViewModel::class.java)
        Mockito.`when`(viewModel.doBuscarListaDeContatos(
            Mockito.anyString(),
            MockitoHelper.anyObject(),
            MockitoHelper.anyObject()
        )).thenAnswer {
            val onError = it.arguments[2] as ((Exception)->Unit)
            onError(Exception("Erro ao processar sua solcitação!"))
            ""
        }
    }

    @Test
    fun telaSucessoTest() {
        setupMockOnSucesso()
        activity = ActivityTestRule(ListaDeContatosActivity::class.java,true,false);
        var intent = Intent()
        intent.putExtra("isMock",true)
        activity.launchActivity(intent)
        activity.activity.viewModel = viewModel
        activity.activity.onClickBuscar()
        while (!activity.activity.isDestroyed){
            Thread.sleep(2000)
        }
    }

    @Test
    fun telaErrorTest() {
        setupMockOnError()
        activity = ActivityTestRule(ListaDeContatosActivity::class.java,true,false);
        var intent = Intent()
        intent.putExtra("isMock",true)
        activity.launchActivity(intent)
        activity.activity.viewModel = viewModel
        activity.activity.onClickBuscar()
        while (!activity.activity.isDestroyed){
            Thread.sleep(2000)
        }
    }
}

object MockitoHelper {
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T =  null as T
}