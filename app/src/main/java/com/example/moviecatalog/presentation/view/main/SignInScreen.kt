package com.example.moviecatalog.presentation.view.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.R.*
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.databinding.SignInScreenBinding
import com.example.moviecatalog.presentation.entity.FieldAction
import com.example.moviecatalog.presentation.entity.LoginCredentials
import com.example.moviecatalog.presentation.state.LoginUiState
import com.example.moviecatalog.presentation.view.MainActivity
import com.example.moviecatalog.presentation.view_model.LoginViewModel
import com.example.moviecatalog.presentation.view_model.LoginViewModelFactory
import kotlinx.coroutines.launch

class SignInScreen : Fragment(layout.sign_in_screen) {
    private var binding: SignInScreenBinding? = null
    private lateinit var loginCredentials: LoginCredentials
    private lateinit var loginUIState: LoginUiState
    private lateinit var viewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            this, LoginViewModelFactory()
        )
            .get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SignInScreenBinding.bind(view)
        binding?.mainConstraint?.clipToOutline = true

        binding?.backButton?.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, WelcomeScreen())
                .commit()
        }

        binding?.entry?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.loginUser(requireContext()).join()
                subscribeErrorMessage()
            }
        }

        subscribeLogin(viewModel)
        subscribeLoginUi(viewModel)
        setListeners(viewModel)
        setValidationFields(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun subscribeErrorMessage() {
        binding?.apply {
            exceptionError.visibility =
                loginUIState.exceptionErrorView
            exceptionError.text =
                loginCredentials.exceptionError
        }
    }


    private fun setListeners(viewModel: LoginViewModel) {
        binding?.apply {

            deleteLogin.setOnClickListener {
                login.setText(INITIAL_FIELD_STATE)
                viewModel.setLogin(INITIAL_FIELD_STATE)
                viewModel.changeUIState("login")
                deleteLogin.visibility = loginUIState.loginIconVisibility
            }


            passwordVisibility.setOnClickListener {
                viewModel.changeUIState("password")
                passwordVisibility.setImageResource(loginUIState.passwordCurrentIcon)
                password.inputType = loginUIState.passwordCurrentInputType
            }
        }
    }

    private fun subscribeLogin(viewModel: LoginViewModel) {
        this.lifecycleScope.launch {
            viewModel.login.collect { credentialField: LoginCredentials ->
                loginCredentials = credentialField
            }
        }
    }

    private fun subscribeLoginUi(viewModel: LoginViewModel) {
        this.lifecycleScope.launch {
            viewModel.loginUIState.collect { value: LoginUiState ->
                loginUIState = value
            }
        }
    }

    private fun setValidationFields(viewModel: LoginViewModel) {
        binding?.apply {
            val fieldActions = listOf(
                FieldAction(login, "login") {
                    viewModel.setLogin(it)
                    viewModel.changeUIState("login")
                    deleteLogin.visibility = loginUIState.loginIconVisibility
                    loginError.visibility = loginCredentials.loginErrorView
                    loginCredentials.loginError?.let { error ->
                        loginError.text = error
                    }
                },
                FieldAction(password) {
                    viewModel.setPassword(it)
                    passwordError.visibility = loginCredentials.passwordErrorView
                    loginCredentials.passwordError?.let { error ->
                        passwordError.text = error
                    }
                },
            )
            fieldActions.forEach { (editText, _, action) ->
                setTextChangeListener(editText, action)
            }
        }
    }

    private fun isLoginValid() {
        binding?.apply {
            entry.isEnabled =
                viewModel.loginValid.value.buttonState
            entry.setBackgroundResource(
                viewModel.loginValid.value.buttonStyle
            )
            entry.setTextAppearance(
                viewModel.loginValid.value.buttonTextStyle
            )
        }
    }

    private fun setTextChangeListener(editText: EditText, action: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                action(editText.text.toString())
                isLoginValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}