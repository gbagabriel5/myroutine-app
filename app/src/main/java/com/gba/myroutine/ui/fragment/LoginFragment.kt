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
import com.gba.myroutine.ui.viewmodel.LoginViewModel
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

    fun logar() {
        btnLogar.setOnClickListener { view ->
            if(editEmail.text.toString().isNotBlank()) {
                if(editSenha.text.toString().isNotBlank()) {
                    val usuario = Usuario().apply {
                        this.email = editEmail.text.toString()
                        this.senha = editSenha.text.toString()
                    }
                    viewModel.load(usuario)
                    viewModel.usuario.observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            Toast.makeText(context, "Bem Vindo!", Toast.LENGTH_SHORT).show()
                            view.findNavController().navigate(R.id.action_loginFragment_to_tarefasFragment)
                        } else {
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