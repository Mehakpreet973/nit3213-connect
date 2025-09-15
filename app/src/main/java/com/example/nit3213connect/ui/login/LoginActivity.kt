package com.example.nit3213connect.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nit3213connect.databinding.ActivityLoginBinding
import com.example.nit3213connect.ui.dashboard.DashboardActivity
import com.example.nit3213connect.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ---- Campus dropdown (labels -> codes) ----
        data class Campus(val label: String, val code: String)
        val campuses = listOf(
            Campus("Footscray", "footscray"),
            Campus("Sydney",    "sydney"),
            Campus("Brisbane",  "br")
        )
        binding.campusAuto.setSimpleItems(campuses.map { it.label }.toTypedArray())
        binding.campusAuto.setText(campuses.first().label, false)
        vm.updateCampus(campuses.first().code)

        binding.campusAuto.setOnItemClickListener { _, _, pos, _ ->
            vm.updateCampus(campuses[pos].code)
        }
        binding.campusAuto.isCursorVisible = false
        binding.campusAuto.setOnClickListener { binding.campusAuto.showDropDown() }
        binding.campusAuto.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.campusAuto.showDropDown()
        }

        // ---- Inputs ----
        binding.usernameEdit.doAfterTextChanged { vm.updateUsername(it?.toString().orEmpty()) }
        binding.passwordEdit.doAfterTextChanged { vm.updatePassword(it?.toString().orEmpty()) }

        // ---- Login ----
        binding.loginBtn.setOnClickListener { vm.login() }

        // ---- Observe state ----
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { s ->
                    when (val st = s.loginState) {
                        is Resource.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.loginBtn.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.progress.visibility = View.GONE
                            binding.loginBtn.isEnabled = true

                            // *** ensure lowercase keypass ***
                            val key = st.data.trim().lowercase()

                            startActivity(
                                Intent(this@LoginActivity, DashboardActivity::class.java)
                                    .putExtra("keypass", key)
                                    .putExtra("username", s.username)
                            )
                            finish()
                        }
                        is Resource.Error -> {
                            binding.progress.visibility = View.GONE
                            binding.loginBtn.isEnabled = true
                            Toast.makeText(this@LoginActivity, st.message, Toast.LENGTH_LONG).show()
                        }
                        null -> {
                            binding.loginBtn.isEnabled =
                                s.username.isNotBlank() && s.password.isNotBlank()
                        }
                    }
                }
            }
        }
    }
}
