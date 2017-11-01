package com.profilizer.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.profilizer.R
import com.profilizer.personalitytest.adapter.QuestionsAnswerOptionsAdapter
import com.profilizer.personalitytest.model.QuestionType
import kotlinx.android.synthetic.main.fragment_question.answers_options as answerOptions
import kotlinx.android.synthetic.main.fragment_question.btn_next as nextButton

class QuestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViews()
        nextButton.setOnClickListener {
            val adapter = answerOptions.adapter as QuestionsAnswerOptionsAdapter
            Toast.makeText(context, adapter.getUserAnswer(), Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareViews() {
        val options = setOf<String>("not important", "important", "very important")
        answerOptions.layoutManager = LinearLayoutManager(context)
        var type = QuestionType("single_choice", options, null, null)
        answerOptions.adapter = QuestionsAnswerOptionsAdapter(type.options)
    }
}