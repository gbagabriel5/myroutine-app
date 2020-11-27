package com.gba.myroutine.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gba.myroutine.R
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.api.retrofit.RetrofitClient
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.ui.viewmodel.CadastroViewModel
import com.gba.myroutine.ui.viewmodel.CadastroViewModelFactory
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_cadastro.*

class CadastroFragment : Fragment() {

    private val viewModel: CadastroViewModel by lazy {
        val repository = UserRepository(RetrofitClient.userService)
        ViewModelProvider(
                this,
                CadastroViewModelFactory(repository)
        ).get(CadastroViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        cadastrar()
    }

    private fun cadastrar() {
        btnCadastrar.setOnClickListener {view ->
            if (editCadNome.text.toString().isNotBlank()) {
                if (editCadEmail.text.toString().isNotBlank()) {
                    if (editCadSenha.text.toString().isNotBlank()) {
                        if (editCadRepetirSenha.text.toString() == editCadSenha.text.toString()) {
                            val usuario = Usuario().apply {
                                this.nome = editCadNome.text.toString()
                                this.email = editCadEmail.text.toString()
                                this.senha = editCadRepetirSenha.text.toString()
                            }
                            viewModel.save(usuario)
                            progressCadastro.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(context, "As senhas não conincidem!",
                                Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Digite uma senha!",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Digite o seu e-mail!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Digite o seu nome!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun observe() {
        viewModel.userSaved.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {
                progressCadastro.visibility = View.VISIBLE
                Toast.makeText(context, "Usuario cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                findNavController().popBackStack()
            } else if (it.status == Status.ERROR) {
                progressCadastro.visibility = View.VISIBLE
                Toast.makeText(context, "Falha ao Cadastrar Usuario!", Toast.LENGTH_SHORT)
                        .show()
                findNavController().popBackStack()
            }
        })
    }
}