package com.profilizer.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.profilizer.ProfilizerApplication
import com.profilizer.R
import com.profilizer.common.CategoryType
import com.profilizer.common.QuestionTypeOption
import com.profilizer.common.ViewType
import com.profilizer.personalitytest.adapter.QuestionsAnswerOptionsAdapter
import com.profilizer.personalitytest.contracts.QuestionContract
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.QuestionModule
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.model.Question
import com.profilizer.personalitytest.model.QuestionRange
import com.profilizer.personalitytest.model.QuestionSingleChoice
import com.profilizer.ui.FragmentCallback
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_question.answers_options as answerOptions
import kotlinx.android.synthetic.main.fragment_question.btn_next as nextButton
import kotlinx.android.synthetic.main.fragment_question.category_image as categoryImage
import kotlinx.android.synthetic.main.fragment_question.txt_question as textQuestion

class QuestionFragment : Fragment(), QuestionContract.View {

    companion object {
        val KEY_QUESTION = "questionType"
        val KEY_PERSONALITY_TEST = "personalityTest"
        val KEY_SELECTED_ANSWER_POSITION = "selectedAnswerPosition"

        fun createQuestionFragment(question: Question, personalityTestId: String) : Fragment {
            val questionFragment = QuestionFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_QUESTION, question)
            bundle.putString(KEY_PERSONALITY_TEST, personalityTestId)
            questionFragment.arguments = bundle
            return questionFragment
        }
    }

    @Inject
    lateinit var presenter: QuestionContract.Presenter
    private lateinit var callback: FragmentCallback
    private lateinit var questionElement: Question
    private lateinit var personalityTestId: String
    private var selectedAnswerPosition: Int = RadioGroup.NO_ID

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as FragmentCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInjection()
        restoreState(arguments, savedInstanceState)
        prepareViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val adapter = answerOptions.adapter as QuestionsAnswerOptionsAdapter
        outState.putInt(KEY_SELECTED_ANSWER_POSITION, adapter.getSelectedPosition())
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    private fun restoreState(arguments: Bundle?, savedInstanceState: Bundle?) {
        arguments?.apply {
            questionElement = getParcelable<Question>(KEY_QUESTION)
            personalityTestId = getString(KEY_PERSONALITY_TEST)
        }
        savedInstanceState?.apply {
            selectedAnswerPosition = getInt(KEY_SELECTED_ANSWER_POSITION)
        }
    }

    private fun setUpInjection() {
        ProfilizerApplication.applicationComponent
                .provideQuestionComponent(QuestionModule(this), PersonalityTestModule())
                .inject(this)
    }

    private fun prepareViews() {
        textQuestion.text = questionElement.question
        setQuestionImage()
        answerOptions.layoutManager = LinearLayoutManager(context)
        answerOptions.adapter = QuestionsAnswerOptionsAdapter(getAdapterViewType())
        nextButton.setOnClickListener {
            onNextClick()
        }
    }

    private fun onNextClick() {
        val adapter = answerOptions.adapter as QuestionsAnswerOptionsAdapter
        val answer = adapter.getUserAnswer()
        if (answer == null || answer.isBlank()) {
            Toast.makeText(context, getString(R.string.answer_required), Toast.LENGTH_LONG).show()
        } else {
            val question = questionElement.question
            val category = questionElement.category
            presenter.saveAnswer(answer, question, category, personalityTestId)
        }
    }

    private fun setQuestionImage() {
        val categoryType = CategoryType.getCategoryTypeByCategory(questionElement.category)
        categoryImage.setImageDrawable(ContextCompat.getDrawable(context!!, categoryType.imageResource))
    }

    override fun getViewContext(): Context {
        return context!!
    }

    override fun showErrorMessage() {
        callback.onFinishLoading()
        Toast.makeText(context, R.string.load_error_message, Toast.LENGTH_LONG).show()
    }

    override fun showNoNetworkMessage() {
        callback.onFinishLoading()
        Toast.makeText(context, R.string.no_network_error_message, Toast.LENGTH_LONG).show()
    }

    override fun onStartLoading() {
        callback.onStartLoading()
    }

    override fun onFinishSaveAnswer(answer: Answer) {
        callback.onFinishLoading()
        callback.onQuestionFinish(answer)
    }

    private fun getAdapterViewType() : ViewType {
        val questionCategoryOption = QuestionTypeOption.getQuestionTypeByType(questionElement.questionType.type)
        val questionType = questionElement.questionType
        if (questionCategoryOption == QuestionTypeOption.NUMBER_RANGE) {
            val range = questionType.range
            if (range != null) {
                return QuestionRange(range.from, range.to)
            }
            throw IllegalArgumentException("Invalid question range")
        } else {
            return QuestionSingleChoice(questionElement.questionType.options)
        }
    }
}