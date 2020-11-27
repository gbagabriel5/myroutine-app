package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gba.myroutine.R
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.api.retrofit.RetrofitClient
import com.gba.myroutine.room.model.Tarefa
import com.gba.myroutine.ui.adapter.TarefasAdapter
import com.gba.myroutine.ui.listener.TarefasListener
import com.gba.myroutine.ui.viewmodel.TarefasViewModel
import com.gba.myroutine.ui.viewmodel.TarefasViewModelFactory
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_tarefas.*

class TarefasFragment : Fragment() {

    private val viewModel: TarefasViewModel by lazy {
        val repository = UserRepository(RetrofitClient.userService)
        ViewModelProvider(
                this,
                TarefasViewModelFactory(activity?.application!!, repository)
        ).get(TarefasViewModel::class.java)
    }
    private val tarefaAdapter: TarefasAdapter = TarefasAdapter()
    private lateinit var mListener: TarefasListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_tarefas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerTarefas.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerTarefas.adapter = tarefaAdapter

        mListener = object : TarefasListener {
            override fun onClick(tarefa: Tarefa) {
                val id = viewModel.getUserId()
                tarefa.usuarioId = id
                tarefa.user.id = id
                val action = TarefasFragmentDirections.
                actionFragmentTarefasToFragmentCadastroTarefas(tarefa)
                view.findNavController().navigate(action)
            }
        }
        tarefaAdapter.attachListener(mListener)
        observe()
        floatingActionButton.setOnClickListener {
            val action = TarefasFragmentDirections.
            actionFragmentTarefasToFragmentCadastroTarefas(Tarefa())
            it.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun observe() {
        viewModel.userWithTasks.observe(viewLifecycleOwner, {
            if(it.status == Status.SUCCESS) {
                it.data?.let { user ->
                    tarefaAdapter.updateGuests(user.tasks)
                }
            }
        })
        viewModel.userLogout.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        viewModel.userId.observe(viewLifecycleOwner, {
            if(it == false) {
                Toast.makeText(context,
                        "Não foi possivel pegar o email do usuario do shared preferences!",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuSair -> {
                viewModel.deslogar()
                true
            }
            else -> {
                (NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                        || super.onOptionsItemSelected(item))
            }
        }
    }
}