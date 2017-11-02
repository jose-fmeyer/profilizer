package com.profilizer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.profilizer.R
import kotlinx.android.synthetic.main.fragment_finish_test.btn_finish_test as btnFinishTest

class FinishTestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_finish_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViews()
    }

    private fun prepareViews() {
        btnFinishTest.setOnClickListener {
            activity?.finish()
        }
    }
}
