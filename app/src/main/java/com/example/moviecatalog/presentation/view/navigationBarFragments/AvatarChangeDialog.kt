package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.AvatarDialogBinding
import com.example.moviecatalog.databinding.ProfileScreenBinding

class AvatarChangeDialog(
    context: Context,
    private val acceptButtonListener: DialogResult
) :
    DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.avatarDialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.avatar_dialog, container, false)
        rootView.findViewById<Button>(R.id.dismissButton).setOnClickListener { dismiss() }

        rootView.findViewById<Button>(R.id.accept).setOnClickListener {
            acceptButtonListener.onDialogResult(
                rootView.findViewById<EditText>(R.id.avatarURL).text.toString()
            )
            dismiss()
        }
        return rootView
    }
}