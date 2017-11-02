package com.profilizer.personalitytest.presenter

import android.util.Log
import com.profilizer.personalitytest.contracts.QuestionContract
import com.profilizer.personalitytest.model.Answer
import com.profilizer.personalitytest.services.AnswerService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuestionPresenterImpl @Inject constructor(private val questionView: QuestionContract.View,
                                               private val answerService: AnswerService) : QuestionContract.Presenter {

    private val tag = QuestionPresenterImpl::class.java.canonicalName
    private lateinit var disposable: CompositeDisposable

    override fun onAttach() {
        disposable = CompositeDisposable()
    }

    override fun onDetach() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }

    override fun saveAnswer(answer: String, question: String, category : String, personalityTestId: String) {
        questionView.onStartLoading()
        val userAnswer = Answer(answer, question, category, personalityTestId)
        disposable.add(
                answerService.createAnswer(userAnswer)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ data ->
                            questionView.onFinishSaveAnswer(data)
                        }, {
                            Log.e(tag, "Error on create answer", it)
                            questionView.showErrorMessage()
                        }))
    }
}