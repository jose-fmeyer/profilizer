package com.profilizer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.profilizer.ProfilizerApplication
import com.profilizer.R
import com.profilizer.common.hide
import com.profilizer.common.visible
import com.profilizer.fragments.FinishTestFragment
import com.profilizer.fragments.QuestionFragment
import com.profilizer.fragments.StartTestFragment
import com.profilizer.personalitytest.contracts.StartPersonalityTestTestContract
import com.profilizer.personalitytest.di.components.DaggerStartPersonalityTestComponent
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.di.modules.StartPersonalityTestModule
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.model.PersonalityTestQuestions
import com.profilizer.personalitytest.model.Question
import com.profilizer.ui.FragmentCallback
import kotlinx.android.synthetic.main.activity_personality_test.progressBarQuiz
import java.util.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_personality_test.btn_close as btnClose
import kotlinx.android.synthetic.main.activity_personality_test.container_test as testContainer
import kotlinx.android.synthetic.main.activity_personality_test.progress_loading as progressLoading

class StartPersonalityTestActivity : AppCompatActivity(), StartPersonalityTestTestContract.View, FragmentCallback {

    companion object {
        val KEY_CURRENT_QUESTION = "questionType"
        val KEY_PERSONALITY_TEST = "personalityTest"
        val KEY_TEST_QUESTIONS = "testQuestions"

        fun buildPersonalityTestIntent(context: Context, personalityTestId: String) : Intent {
            val intent = Intent(context, StartPersonalityTestActivity::class.java)
            intent.putExtra(KEY_PERSONALITY_TEST, personalityTestId)
            return intent
        }
    }

    @Inject
    lateinit var presenter: StartPersonalityTestTestContract.Presenter
    private lateinit var testQuestions: PersonalityTestQuestions
    private var testQuiz: Queue<Question> = LinkedList()

    private var currentQuestion: Question? = null
    private var personalityTestId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personality_test)
        setUpInjection()
        prepareViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
        startTest()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        currentQuestion?.let {
            outState?.apply {
                putParcelable(KEY_CURRENT_QUESTION, currentQuestion)
                putString(KEY_PERSONALITY_TEST, personalityTestId)
                val parcelableQuiz = arrayListOf<Question>()
                parcelableQuiz.addAll(testQuiz)
                putParcelable(KEY_TEST_QUESTIONS, testQuestions)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.apply {
            currentQuestion = getParcelable<Question>(KEY_CURRENT_QUESTION)
            personalityTestId = getString(KEY_PERSONALITY_TEST)
            testQuestions = getParcelable(KEY_TEST_QUESTIONS)
        }
    }

    private fun setUpInjection() {
        DaggerStartPersonalityTestComponent.builder()
                .applicationComponent(ProfilizerApplication.applicationComponent)
                .personalityTestModule(PersonalityTestModule())
                .startPersonalityTestModule(StartPersonalityTestModule(this))
                .build()
                .inject(this)
    }

    private fun prepareViews() {
        intent.extras?.let {
            personalityTestId = intent.getStringExtra(KEY_PERSONALITY_TEST)
        }
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun loadStartFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
                .replace(R.id.container_test, StartTestFragment())
                .commit()
    }

    private fun loadQuestionFragment(question: Question, personalityTestId : String) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right)
                .replace(R.id.container_test, QuestionFragment.createQuestionFragment(question, personalityTestId))
                .commit()
    }

    private fun loadFinishTestFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right)
                .replace(R.id.container_test, FinishTestFragment())
                .commit()
    }

    private fun startTest() {
        progressBarQuiz.visible()
        if(isStartedTest()) {
            presenter.loadTestQuestions()
            return
        }
        if (testQuiz.isEmpty()) {
            loadStartFragment()
        } else {
            progressBarQuiz.max = testQuiz.size
            openNextQuestion()
        }
    }

    override fun onQuestionFinish(answer: Answer) {
        currentQuestion?.let {
            incrementProgress()
            testQuiz.remove(currentQuestion)
            if (shouldShowConditionalQuestionGivenUserAnswer(answer)) {
                openConditionalQuestion(it)
                return
            }
            openNextQuestion()
        }
    }

    private fun openNextQuestion() {
        val nextQuestion = getNextQuestion()
        if (nextQuestion != null) {
            loadQuestionFragment(nextQuestion, personalityTestId)
        } else {
            loadFinishTestFragment()
        }
    }

    private fun openConditionalQuestion(parentQuestion: Question) {
        progressBarQuiz.max += 1
        val questionCondition = parentQuestion.questionType.condition
        val question = questionCondition?.conditionalQuestion
        if (question != null) {
            currentQuestion = question
            loadQuestionFragment(question, personalityTestId)
        }
    }

    override fun onStartFinish(personalityTestId : String) {
        this.personalityTestId = personalityTestId
        progressBarQuiz.visible()
        presenter.loadTestQuestions()
    }

    private fun getNextQuestion() : Question? {
        val question = testQuiz.peek()
        if (question != null) {
            currentQuestion = question
            return question
        }
        return null
    }

    override fun onStartLoading() {
        progressLoading.visible()
    }

    override fun onFinishLoading() {
        progressLoading.hide()
    }

    override fun onFinishLoadingTestQuestions(personalityTestQuestions: PersonalityTestQuestions) {
        progressLoading.hide()
        progressBarQuiz.max = personalityTestQuestions.questions.size
        testQuestions = if (isStartedTest()) {
            filterUnansweredQuestions(personalityTestQuestions)
            personalityTestQuestions
        } else {
            personalityTestQuestions
        }
        testQuiz.addAll(testQuestions.questions)
        setProgressInitialState()
        loadQuestionFragment(getNextQuestion()!!, personalityTestId)
    }

    private fun filterUnansweredQuestions(personalityTestQuestions: PersonalityTestQuestions) {
        personalityTestQuestions.questions = personalityTestQuestions.questions.filter { q -> questionIsAnswered(q)}
    }

    private fun questionIsAnswered(question: Question) : Boolean {
        question.testsId?.let {
            return !it.contains(personalityTestId)
        }
        return true
    }

    override fun showErrorMessage() {
        progressLoading.hide()
        Snackbar.make(testContainer, R.string.load_error_message, Snackbar.LENGTH_LONG).show()

    }

    override fun startLoading() {
        progressLoading.visible()
    }

    private fun incrementProgress() {
        progressBarQuiz.progress += 1
    }

    private fun shouldShowConditionalQuestionGivenUserAnswer(answer: Answer) : Boolean {
        var hasAnswerMatchPredicate : Boolean? = false
        val questionCondition = currentQuestion?.questionType?.condition
        questionCondition?.let {
            val predicate = it.predicate
            hasAnswerMatchPredicate = predicate?.exactEquals?.any {
                value -> value.contentEquals(answer.answer)
            }
        }
        return when {
            hasAnswerMatchPredicate != null -> hasAnswerMatchPredicate!!
            else -> false
        }
    }

    private fun isStartedTest() = personalityTestId.isNotBlank()

    private fun setProgressInitialState() {
        progressBarQuiz.let {
            it.progress = it.max - testQuiz.size
        }
    }
}
