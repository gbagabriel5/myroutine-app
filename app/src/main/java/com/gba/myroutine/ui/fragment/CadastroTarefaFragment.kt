package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.gba.myroutine.R
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.ui.viewmodel.CadastroTarefaViewModel
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_cadastro_tarefa.*

class CadastroTarefaFragment : Fragment() {

    val args: CadastroTarefaFragmentArgs by navArgs()

    private lateinit var viewModel: CadastroTarefaViewModel

    private var mGuestId = 0
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CadastroTarefaViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (args.id > 0)
            setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_cadastro_tarefa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe()
        loadData()
        salvar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun salvar() {
        btnSalvar.setOnClickListener{
            if (!editTitulo.text.toString().isNullOrBlank()) {
                val titulo = editTitulo.text.toString()
                val desc = editDesc.text.toString()
                viewModel.getUser()
                val tarefa = Tarefa().apply {
                    this.id = mGuestId
                    this.titulo = titulo
                    this.descricao = desc
                    this.usuarioId = userId
                }
                viewModel.saveOrUpdate(tarefa)
            } else {
                Toast.makeText(context, "Titulo em Branco!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        mGuestId = args.id
        if(mGuestId > 0) viewModel.load(mGuestId)
    }

    private fun observe() {
        viewModel.tarefaSalva.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> Toast.makeText(context,"Sucesso!", Toast.LENGTH_SHORT).show()
                Status.ERROR -> Toast.makeText(context,"Falha!", Toast.LENGTH_SHORT).show()
            }
            findNavController().popBackStack()
        })
        viewModel.tarefa.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { tarefa ->
                        editTitulo.setText(tarefa.titulo)
                        editDesc.setText(tarefa.descricao)
                    }
                }
                Status.ERROR ->
                    Toast.makeText(
                        context,
                        "Erro ao preencher campos com dados ja existentes! "+it.exception,
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })
        viewModel.tarefaRemovida.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> Toast.makeText(
                    context,
                    "Tarefa removida com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                Status.ERROR -> Toast.makeText(
                    context,
                    "Falha ao remover Tarefa!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            findNavController().popBackStack()
        })

        viewModel.usuario.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> it.data?.let { user -> userId = user.id }
                Status.ERROR -> Toast.makeText(
                    context,
                    "Usuario não encontrado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_form, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuDelete -> {
                viewModel.delete(args.id)
                true
            }
            else -> {
                (NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                        || super.onOptionsItemSelected(item))
            }
        }
    }
}