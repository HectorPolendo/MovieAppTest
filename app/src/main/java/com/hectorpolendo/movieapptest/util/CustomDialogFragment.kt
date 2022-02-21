package com.hectorpolendo.movieapptest.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hectorpolendo.movieapptest.databinding.FragmentDialogBinding
import com.hectorpolendo.movieapptest.ui.view.activities.UserActivity

class CustomDialogFragment: DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)

        binding.btnOk.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }
}