package com.gba.myroutine.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gba.myroutine.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        findNavController(R.id.my_nav_host_fragment)
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
                setOf(
                        R.id.fragmentTarefas,
                        R.id.fragmentLogin
                )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
        setupToolbar()
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentLogin -> {
                    toolbar_principal.visibility = View.GONE
                    toolbar_principal.setTitleTextColor(Color.BLACK)
                    toolbar_principal.setBackgroundColor(Color.TRANSPARENT)
                }
                R.id.fragmentTarefas -> {
                    toolbar_principal.visibility = View.VISIBLE
                    toolbar_principal.setTitleTextColor(Color.BLACK)
                    toolbar_principal.setBackgroundColor(Color.TRANSPARENT)
                }
                R.id.fragmentCadastroTarefas -> {
                    toolbar_principal.visibility = View.VISIBLE
                    toolbar_principal.setTitleTextColor(Color.BLACK)
                    toolbar_principal.setBackgroundColor(Color.TRANSPARENT)
                }
                R.id.fragmentCadastro -> {
                    toolbar_principal.visibility = View.VISIBLE
                    toolbar_principal.setTitleTextColor(Color.WHITE)
                    toolbar_principal.setBackgroundColor(Color.parseColor("#8149aa"))
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_principal)
        toolbar_principal.setupWithNavController(
            navController, appBarConfiguration
        )
    }
}