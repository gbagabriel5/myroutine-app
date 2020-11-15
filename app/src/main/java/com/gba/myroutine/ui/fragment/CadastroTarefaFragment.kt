package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gba.myroutine.R
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.ui.viewmodel.CadastroTarefaViewModel
import kotlinx.android.synthetic.main.fragment_cadastro_tarefa.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class CadastroTarefaFragment : Fragment() {

    private lateinit var viewModel: CadastroTarefaViewModel

    private var mGuestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CadastroTarefaViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        toolbarPrincipal.title = "Tarefa"
//        setHasOptionsMenu(true)
//        setMenuVisibility(true)
        return inflater.inflate(R.layout.fragment_cadastro_tarefa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe()
        loadData()
        salvar()
        super.onViewCreated(view, savedInstanceState)
    }

    fun salvar() {
        btnSalvar.setOnClickListener{
            if (!editTitulo.text.toString().isNullOrBlank()) {
                val titulo = editTitulo.text.toString()
                if (!editDesc.text.toString().isNullOrBlank()) {
                    val desc = editDesc.text.toString()
                    val tarefa = Tarefa().apply {
                        this.id = mGuestId
                        this.titulo = titulo
                        this.descricao = desc
                    }
                    viewModel.save(tarefa)
                } else {
                    Toast.makeText(context, "Titulo em Branco!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Descrição vazia!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        arguments?.let {
            var id = TarefasFragmentArgs.fromBundle(it).id
            id.let {
                viewModel.load(mGuestId)
            }
        }
    }

    private fun observe() {
        viewModel.tarefaSalva.observe(viewLifecycleOwner, Observer {
            if(it)
                Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Falha!", Toast.LENGTH_SHORT).show()
            var controller = findNavController()
            controller.navigate(R.id.action_cadastroTarefaFragment_to_tarefasFragment)
        })
        viewModel.tarefa.observe(viewLifecycleOwner, Observer {
            editTitulo.setText(it.titulo)
            editDesc.setText(it.descricao)
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater!!.inflate(R.menu.menu_form, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menuSalvar -> {
//
//            } else -> { }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}