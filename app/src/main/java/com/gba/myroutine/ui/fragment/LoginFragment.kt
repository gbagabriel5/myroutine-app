package com.gba.myroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gba.myroutine.R
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.api.retrofit.RetrofitClient
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.ui.viewmodel.LoginViewModel
import com.gba.myroutine.ui.viewmodel.LoginViewModelFactory
import com.gba.myroutine.valuableobjects.Status
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        val repository = UserRepository(RetrofitClient.userService)
        ViewModelProvider(
                this,
                LoginViewModelFactory(activity?.application!!, repository)
        ).get(LoginViewModel::class.java)
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
        observer()
        viewModel.verificaUsuarioLogado()
    }

    private fun observer() {
        viewModel.usuarioLogado.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS)
                findNavController().navigate(R.id.action_loginFragment_to_tarefasFragment)
        })

        viewModel.login.observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {
                viewModel.getUserByEmail(editEmail.text.toString())
            } else if (it.status == Status.ERROR) {
                progressLogin.visibility = View.GONE
                Toast.makeText(context, "Usuario ou senha incorretos!",
                        Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user.status == Status.SUCCESS)
                user.data?.let {viewModel.saveIdToSharedPreferences(it.id.toString())}
            else if (user.status == Status.ERROR)
                Toast.makeText(context, "Não foi possivel encontrar o E-mail!",
                        Toast.LENGTH_SHORT).show()

        })
        viewModel.userId.observe(viewLifecycleOwner, {
            if (it) {
                progressLogin.visibility = View.GONE
                Toast.makeText(context, "Bem Vindo!", Toast.LENGTH_SHORT).show()
                var controller = findNavController()
                controller.navigate(R.id.action_loginFragment_to_tarefasFragment)
            } else {
                progressLogin.visibility = View.GONE
                Toast.makeText(context, "Não foi possivel guardar o usuario!",
                        Toast.LENGTH_SHORT).show()
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
                    viewModel.validateLogin(usuario)
                    progressLogin.visibility = View.VISIBLE
                } else {
                    Toast.makeText(context, "Digite a sua senha!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Digite o seu e-mail!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}