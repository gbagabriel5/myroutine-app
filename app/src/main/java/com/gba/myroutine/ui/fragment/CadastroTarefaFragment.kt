package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.gba.myroutine.R
import com.gba.myroutine.api.repository.TaskRepository
import com.gba.myroutine.api.retrofit.RetrofitClient
import com.gba.myroutine.room.model.Tarefa
import com.gba.myroutine.ui.viewmodel.CadastroTarefaViewModel
import com.gba.myroutine.ui.viewmodel.CadastroTarefaViewModelFactory
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_cadastro_tarefa.*
import java.text.SimpleDateFormat
import java.util.*

class CadastroTarefaFragment : Fragment() {

    val args: CadastroTarefaFragmentArgs by navArgs()

    private var mGuestId = 0

    private val viewModel: CadastroTarefaViewModel by lazy {
        val repository = TaskRepository(RetrofitClient.taskService)
        ViewModelProvider(
                this,
                CadastroTarefaViewModelFactory(activity?.application!!, repository)
        ).get(CadastroTarefaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (args.tarefaArg.id > 0)
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
                val date = Calendar.getInstance().time
                val formatter = SimpleDateFormat.getDateTimeInstance()
                val data = formatter.format(date)
                val tarefa = Tarefa().apply {
                    this.id = mGuestId
                    this.titulo = titulo
                    this.descricao = desc
                    this.data = data
                }
                viewModel.saveOrUpdate(tarefa)
            } else {
                Toast.makeText(context, "Titulo em Branco!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        mGuestId = args.tarefaArg.id
        viewModel.load(mGuestId)
    }

    private fun observe() {
        viewModel.savedTask.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {
                it.data?.let { tarefa ->
                    tarefa.usuarioId = args.tarefaArg.usuarioId
                    viewModel.saveOrUpdateRoom(tarefa)
                }
            } else if (it.status == Status.ERROR) {
                Toast.makeText(context, "Falha!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.tarefaSalva.observe(viewLifecycleOwner, {
            if(it.status == Status.SUCCESS) {
                Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show()
            }
            else if(it.status == Status.ERROR) {
                Toast.makeText(context, "Falha!", Toast.LENGTH_SHORT).show()
            }
            findNavController().popBackStack()
        })

        viewModel.tarefa.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {
                it.data?.let { tarefa ->
                    editTitulo.setText(tarefa.titulo)
                    editDesc.setText(tarefa.descricao)
                }
            }
        })

        viewModel.tarefaRemovida.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS)
                Toast.makeText(context, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show()
            else if (it.status == Status.ERROR)
                Toast.makeText(context, "Falha ao remover Tarefa!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })

        viewModel.usuario.observe(viewLifecycleOwner, {
            if(it == null) {
                Toast.makeText(context, "Usuario não encontrado", Toast.LENGTH_SHORT).show()
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
                viewModel.delete(args.tarefaArg.id)
                true
            }
            else -> {
                (NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                        || super.onOptionsItemSelected(item))
            }
        }
    }
}