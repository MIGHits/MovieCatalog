package com.example.moviecatalog.presentation.view.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.databinding.AvatarDialogBinding
import java.net.URL

class AvatarChangeDialog(
    context: Context,
    private val acceptButtonListener: DialogResult
) : DialogFragment() {

    private var _binding: AvatarDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.avatarDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AvatarDialogBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.dismissButton.setOnClickListener {
            binding.avatarURL.setText(INITIAL_FIELD_STATE)
            dismiss()
        }

        binding.accept.setOnClickListener {
            val avatarUrl = binding.avatarURL.text.toString()
            acceptButtonListener.onDialogResult(

                if (isValidUrl(avatarUrl)) {
                    avatarUrl
                } else {
                    INITIAL_FIELD_STATE
                }
            )
            binding.avatarURL.setText(INITIAL_FIELD_STATE)
            dismiss()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidUrl(urlString: String): Boolean {
        return try {
            URL(urlString)
            true
        } catch (e: Exception) {
            false
        }
    }
}