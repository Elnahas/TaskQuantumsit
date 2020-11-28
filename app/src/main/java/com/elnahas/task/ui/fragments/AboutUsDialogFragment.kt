package com.elnahas.task.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.elnahas.task.R
import com.elnahas.task.util.Constants.KEY_TITLE
import com.elnahas.task.ui.MainActivity
import com.elnahas.task.util.Resource
import com.elnahas.task.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about_us_dialog.*
import kotlinx.android.synthetic.main.fragment_about_us_dialog.view.*

@AndroidEntryPoint
class AboutUsDialogFragment : DialogFragment() {

    lateinit var mainViewModel: MainViewModel


    companion object {

        const val TAG = "AboutUsDialogFragment"

        fun newInstance(): AboutUsDialogFragment {
            val fragment = AboutUsDialogFragment()
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us_dialog, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webview.settings.javaScriptEnabled = true

        setupClickListeners(view)

        mainViewModel =  (activity as MainActivity).mainViewModel
        mainViewModel.getAboutUsData()
        mainViewModel.aboutUsLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { aboutResponse ->

                        webview.loadDataWithBaseURL(null, aboutResponse.innerData[0].content, "text/html", "utf-8", null);
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message , Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }



    private fun setupClickListeners(view: View) {

        view.btnClose.setOnClickListener {
            dismiss()
        }
    }

}