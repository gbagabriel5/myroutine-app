package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gba.myroutine.R
import com.gba.myroutine.ui.adapter.TarefasAdapter
import com.gba.myroutine.ui.listener.TarefasListener
import com.gba.myroutine.ui.viewmodel.TarefasViewModel
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_tarefas.*

class TarefasFragment : Fragment() {

    private lateinit var viewModel: TarefasViewModel
    private val tarefaAdapter: TarefasAdapter = TarefasAdapter()
    private lateinit var mListener: TarefasListener

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(TarefasViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

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
            override fun onClick(id: Int) {
                val action = TarefasFragmentDirections.
                actionFragmentTarefasToFragmentCadastroTarefas(id)
                view.findNavController().navigate(action)
            }
        }
        tarefaAdapter.attachListener(mListener)
        observe()
        floatingActionButton.setOnClickListener {
            val action = TarefasFragmentDirections.
            actionFragmentTarefasToFragmentCadastroTarefas(0)
            it.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun observe() {
        viewModel.tarefaList.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { tarefas ->
                        tarefaAdapter.updateGuests(tarefas)
                    }
                }
            }
        })

        viewModel.deslogarUsuario.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })

        viewModel.userByEmail.observe(viewLifecycleOwner, {
            when(it.status) {
                Status.ERROR -> Toast.makeText(
                    context,
                    "Usuario não encontrado por email de login",
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