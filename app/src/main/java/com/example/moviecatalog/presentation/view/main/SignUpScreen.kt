package com.example.moviecatalog.presentation.view.main

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.FEMALE
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.MALE
import com.example.moviecatalog.databinding.SignUpScreenBinding
import com.example.moviecatalog.presentation.entity.FieldAction
import com.example.moviecatalog.presentation.entity.RegistrationCredentials
import com.example.moviecatalog.presentation.state.RegistrationUIState
import com.example.moviecatalog.presentation.view_model.RegistrationViewModel
import com.example.moviecatalog.presentation.view_model.RegistrationViewModelFactory
import kotlinx.coroutines.launch

class SignUpScreen:Fragment(R.layout.sign_up_screen) {
    private var binding:SignUpScreenBinding? = null
    private lateinit var viewModel:RegistrationViewModel
    private lateinit var registrationCredentials:RegistrationCredentials
    private lateinit var registrationUIState: RegistrationUIState
    private val calendar = Calendar.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
         viewModel = ViewModelProvider(this,RegistrationViewModelFactory())
            .get(RegistrationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpScreenBinding.bind(view)

        setFieldsListeners()
        subscribeRegistration(viewModel)
        subscribeUIState(viewModel)
        setValidationFields(viewModel)

        binding?.constraintLayout2?.clipToOutline = true
        binding?.backButton?.setOnClickListener{
            parentFragment?.parentFragmentManager?.popBackStack()}
        binding?.register?.setOnClickListener{
           lifecycleScope.launch {
               viewModel.registerUser(requireContext()).join()
               subscribeRegistrationError()
           }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun isRegistrationValid(){
        binding?.apply {
            register.isEnabled =
                viewModel.registrationValid.value.buttonState
            register.setBackgroundResource(
                viewModel.registrationValid.value.buttonStyle)
            register.setTextAppearance(
                viewModel.registrationValid.value.buttonTextStyle)
        }
    }

    private fun subscribeRegistrationError(){
        binding?.apply {
            registrationError.visibility =
                registrationUIState.registrationError
            registrationError.text =
                registrationCredentials.exceptionError
        }
    }

    private fun setFieldsListeners(){
        binding?.apply {

            deleteLogin.setOnClickListener{
                login.setText(INITIAL_FIELD_STATE)
                viewModel.setLogin(login.text.toString())
                viewModel.changeUIState("login")
                deleteLogin.visibility = registrationUIState.loginIconVisibility}

            deleteName.setOnClickListener{
                name.setText(INITIAL_FIELD_STATE)
                viewModel.setName(INITIAL_FIELD_STATE)
                viewModel.changeUIState("name")
                deleteName.visibility = registrationUIState.nameIconVisibility}

            deleteEmail.setOnClickListener{
                email.setText(INITIAL_FIELD_STATE)
                viewModel.setEmail(INITIAL_FIELD_STATE)
                viewModel.changeUIState("email")
                deleteEmail.visibility = registrationUIState.emailIconVisibility}

            passwordVisibility.setOnClickListener{
                viewModel.changeUIState("password")
                password.inputType = registrationUIState.passwordCurrentInputType
                passwordVisibility.setImageResource(registrationUIState.passwordCurrentIcon)
            }
            passwordConfirmVisibility.setOnClickListener{
                viewModel.changeUIState("passwordConfirm")
                passwordConfirm.inputType = registrationUIState.passwordConfirmCurrentInputType
                passwordConfirmVisibility.setImageResource(registrationUIState.
                passwordConfirmCurrentIcon)
            }
            male.setOnClickListener { if (male.isPressed)
                male.setBackgroundResource(R.drawable.gender_button_primary)
                female.setBackgroundResource(R.color.DarkFaded)
                viewModel.setGender(MALE)
            }
            female.setOnClickListener { if (female.isPressed)
                female.setBackgroundResource(R.drawable.gender_button_primary)
                male.setBackgroundResource(R.color.DarkFaded)
                viewModel.setGender(FEMALE)
            }
            changeDate.setOnClickListener{pickDate()}
        }
    }

    private  fun pickDate(){
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val minDate = Calendar.getInstance()
        minDate.set(1940,0,1)
        val maxDate = Calendar.getInstance()
        maxDate.set(year,month,day)

       val datePicker = DatePickerDialog(requireContext(),{
            _:DatePicker,selectedYear:Int,selectedMonth:Int,selectedDay:Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear,selectedMonth,selectedDay)
            viewModel.setBirthdate(selectedDate)
            binding?.changeDate?.setImageResource(R.drawable.calender_on)
            binding?.birthDate?.text = registrationCredentials.birthDate
            isRegistrationValid()
        },year,month,day)

        datePicker.datePicker.maxDate = maxDate.timeInMillis
        datePicker.datePicker.minDate = minDate.timeInMillis
        datePicker.show()
    }

   private fun subscribeRegistration(viewModel: RegistrationViewModel){
       this.lifecycleScope.launch {
           viewModel.registration.collect { registrationField:RegistrationCredentials ->
               registrationCredentials = registrationField
           }
       }
   }

    private fun subscribeUIState(viewModel: RegistrationViewModel){
        this.lifecycleScope.launch {
            viewModel.registrationUI.collect{currentstate:RegistrationUIState ->
                registrationUIState = currentstate
            }
        }
    }


    private fun setValidationFields(viewModel: RegistrationViewModel) {
        binding?.apply {
            val fieldActions = listOf(
                FieldAction(login, "login") {
                    viewModel.setLogin(it)
                    viewModel.changeUIState("login")
                    deleteLogin.visibility = registrationUIState.loginIconVisibility
                    loginError.visibility = registrationCredentials.loginErrorView
                    registrationCredentials.loginError?.let { error ->
                        loginError.setText(error)
                    }
                },
                FieldAction(email, "email") {
                    viewModel.setEmail(it)
                    viewModel.changeUIState("email")
                    deleteEmail.visibility = registrationUIState.emailIconVisibility
                    emailError.visibility = registrationCredentials.emailErrorView
                    registrationCredentials.emailError?.let { error ->
                        emailError.setText(error)
                    }
                },
                FieldAction(name, "name") {
                    viewModel.setName(it)
                    viewModel.changeUIState("name")
                    deleteName.visibility = registrationUIState.nameIconVisibility
                    nameError.visibility = registrationCredentials.nameErrorView
                    registrationCredentials.nameError?.let { error ->
                        nameError.setText(error)
                    }
                },
                FieldAction(password) {
                    viewModel.setPassword(it)
                    passwordError.visibility = registrationCredentials.passwordErrorView
                    registrationCredentials.passwordError?.let { error ->
                        passwordError.setText(error)
                    }
                },
                FieldAction(passwordConfirm) {
                    viewModel.setPasswordConfirm(it, password.text.toString())
                    passwordConfirmError.visibility =
                        registrationCredentials.passwordConfirmErrorView
                    registrationCredentials.passwordConfirmError?.let { error ->
                        passwordConfirmError.setText(error)
                    }
                }
            )
            fieldActions.forEach { (editText, _, action) ->
                setTextChangeListener(editText, action)
            }
        }
    }

    private fun setTextChangeListener(editText: EditText, action: (String) -> Unit) {
        editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int) {
                action(editText.text.toString())
                isRegistrationValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}