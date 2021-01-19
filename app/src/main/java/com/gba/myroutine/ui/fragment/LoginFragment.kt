package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gba.myroutine.R
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.ui.viewmodel.LoginViewModel
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logar()
        txtCadastreSe.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.verificaUsuarioLogado()
        observer()
    }

    private fun observer() {
        viewModel.usuarioLogado.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentTarefas)
                }
            }
        })

        viewModel.usuario.observe(viewLifecycleOwner, {
            when(it.status) {

                Status.LOADING -> progressLogin.visibility = View.VISIBLE

                Status.SUCCESS -> {
                    Toast.makeText(context, "Bem Vindo!", Toast.LENGTH_SHORT).show()
                    progressLogin.visibility = View.GONE
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentTarefas)
                    editEmail.setText("")
                    editSenha.setText("")
                    viewModel.setUndefinedOnLogin()
                }

                Status.ERROR -> {
                    Toast.makeText(
                        context,
                        "Usuario ou senha incorretos!",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressLogin.visibility = View.GONE
                }
            }
        })
    }
    private fun logar() {
        btnLogar.setOnClickListener {
            if(editEmail.text.toString().isNotBlank()) {
                if(editSenha.text.toString().isNotBlank()) {
                    val usuario = Usuario().apply {
                        this.email = editEmail.text.toString()
                        this.senha = editSenha.text.toString()
                    }
                    viewModel.load(usuario)
                } else {
                    Toast.makeText(context, "Digite a sua senha!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Digite o seu e-mail!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}