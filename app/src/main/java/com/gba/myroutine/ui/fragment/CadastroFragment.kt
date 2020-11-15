package com.gba.myroutine.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gba.myroutine.R
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.ui.viewmodel.CadastroViewModel
import kotlinx.android.synthetic.main.fragment_cadastro.*

class CadastroFragment : Fragment() {

    private lateinit var viewModel: CadastroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cadastrar()
    }

    private fun cadastrar() {
        btnCadastrar.setOnClickListener {view ->
            if (editCadNome.text.toString().isNotBlank()) {
                if (editCadEmail.text.toString().isNotBlank()) {
                    if (editCadSenha.text.toString().isNotBlank()) {
                        if(editCadRepetirSenha.text.toString() == editCadSenha.text.toString()) {
                            val usuario = Usuario().apply {
                                this.nome = editCadNome.text.toString()
                                this.email = editCadEmail.text.toString()
                                this.senha = editCadRepetirSenha.text.toString()
                            }
                            viewModel.save(usuario)
                            progressCadastro.visibility = View.VISIBLE
                            view.findNavController()
                                .navigate(R.id.action_cadastroFragment_to_loginFragment)
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
        viewModel.saveUsuario.observe(this, Observer {
            if(it) {
                progressCadastro.visibility = View.VISIBLE
                Toast.makeText(context, "Usuario cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
            } else {
                progressCadastro.visibility = View.VISIBLE
                Toast.makeText(context, "Falha ao Cadastrar Usuario!", Toast.LENGTH_SHORT)
                        .show()
            }
        })
    }
}