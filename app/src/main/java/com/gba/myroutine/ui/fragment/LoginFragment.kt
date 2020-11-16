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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.verificaUsuarioLogado()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logar()
        txtCadastreSe.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }

    }

    private fun logar() {
        viewModel.usuarioLogado.observe(viewLifecycleOwner, Observer {
            if (it) {
                var controller = findNavController()
                controller.navigate(R.id.action_loginFragment_to_tarefasFragment)
            }
        })
        btnLogar.setOnClickListener { view ->
            if(editEmail.text.toString().isNotBlank()) {
                if(editSenha.text.toString().isNotBlank()) {
                    val usuario = Usuario().apply {
                        this.email = editEmail.text.toString()
                        this.senha = editSenha.text.toString()
                    }

                    viewModel.load(usuario)
                    progressLogin.visibility = View.VISIBLE
                    viewModel.usuario.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            progressLogin.visibility = View.GONE
                            Toast.makeText(context, "Bem Vindo!", Toast.LENGTH_SHORT).show()
                            view.findNavController().navigate(R.id.action_loginFragment_to_tarefasFragment)
                        } else {
                            progressLogin.visibility = View.GONE
                            Toast.makeText(context, "Usuario ou senha incorretos!",
                                    Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(context, "Digite a sua senha!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Digite o seu e-mail!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}