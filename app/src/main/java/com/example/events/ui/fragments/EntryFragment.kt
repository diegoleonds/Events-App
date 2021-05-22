package com.example.events.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.events.R
import com.example.events.ui.viewmodel.EntryViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.getSharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EntryFragment : Fragment() {
    val viewModel: EntryViewModel by viewModel()

    lateinit var emailEditTxt: TextInputEditText
    lateinit var nameEditTxt: TextInputEditText

    lateinit var emailLayoutTxt: TextInputLayout
    lateinit var nameLayoutTxt: TextInputLayout

    lateinit var enterBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        getDataFromSharedPreferences()
        enterBtnClick(view)
    }


    private fun inflateViews(view: View) {
        emailEditTxt = view.findViewById(R.id.email_edit_txt)
        nameEditTxt = view.findViewById(R.id.name_edit_txt)

        emailLayoutTxt = view.findViewById(R.id.email_txt_inputlayout)
        nameLayoutTxt = view.findViewById(R.id.name_txt_inputlayout)

        enterBtn = view.findViewById(R.id.enter_btn)
    }

    private fun enterBtnClick(view: View) {
        enterBtn.setOnClickListener {
            val name = nameEditTxt.text.toString()
            val email = emailEditTxt.text.toString()

            val nameError = viewModel.isNameInvalid(name)
            val emailError = viewModel.isEmailInvalid(email)

            setTxtLayoutError(nameError, nameLayoutTxt, getString(R.string.invalid_name_error))
            setTxtLayoutError(emailError, emailLayoutTxt, getString(R.string.invalid_email_error))

            if (!nameError && !emailError) {
                storeDataIntoSharedPreferences(name, email)
                Navigation.findNavController(view)
                    .navigate(R.id.action_entryFragment_to_eventsListFragment)
            }
        }
    }

    private fun setTxtLayoutError(
        errorCondition: Boolean,
        txtLayout: TextInputLayout,
        errorMessage: String
    ) {
        txtLayout.isErrorEnabled = errorCondition
        if (errorCondition)
            txtLayout.error = errorMessage
    }

    private fun storeDataIntoSharedPreferences(name: String, email: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.user_name), name)
            putString(getString(R.string.user_email), email)
            commit()
        }
    }

    private fun getDataFromSharedPreferences() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        sharedPref?.let {
            nameEditTxt.setText(it.getString(getString(R.string.user_name), ""))
            emailEditTxt.setText(it.getString(getString(R.string.user_email), ""))
        }
    }
}