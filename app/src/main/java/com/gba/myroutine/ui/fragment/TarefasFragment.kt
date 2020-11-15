package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gba.myroutine.R
import com.gba.myroutine.ui.adapter.TarefasAdapter
import com.gba.myroutine.ui.listener.TarefasListener
import com.gba.myroutine.ui.viewmodel.TarefasViewModel
import kotlinx.android.synthetic.main.fragment_tarefas.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

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
        // Inflate the layout for this fragment
//        toolbarPrincipal.title = "MyRoutine"
//        setHasOptionsMenu(true)
//        setMenuVisibility(true)
        return inflater.inflate(R.layout.fragment_tarefas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerTarefas.layoutManager = GridLayoutManager(context, 2)
        recyclerTarefas.adapter = tarefaAdapter

        mListener = object : TarefasListener {
            override fun onClick(id: Int) {
                var controller = findNavController()
                val bundle = Bundle()
                bundle.putInt("id", id)
                controller.navigate(R.id.action_tarefasFragment_to_cadastroTarefaFragment, bundle)
            }

            override fun onDelete(id: Int) {
//                viewModel.delete(id)
//                viewModel.load(GuestConstants.FILTER.EMPTY)
            }
        }
        tarefaAdapter.attachListener(mListener)
        observe()
        floatingActionButton.setOnClickListener {
            var controller = findNavController()
            controller.navigate(R.id.action_tarefasFragment_to_cadastroTarefaFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun observe() {
        viewModel.tarefaList.observe(viewLifecycleOwner, Observer {
            tarefaAdapter.updateGuests(it)
        })
        viewModel.usuarioDeslogado.observe(viewLifecycleOwner, Observer {
            var controller = findNavController()
            controller.navigate(R.id.action_tarefasFragment_to_cadastroTarefaFragment)
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater!!.inflate(R.menu.menu_main, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menuSair -> {
//                viewModel.deslogar()
//            } else -> { }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}