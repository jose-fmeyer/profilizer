package com.profilizer.personalitytest.model

import android.os.Parcel
import android.os.Parcelable
import com.profilizer.common.ViewType
import com.profilizer.common.ViewTypeDelegateAdapter
import com.profilizer.common.createParcel
import java.io.Serializable
import java.util.*

data class Category(var name: String?) : Parcelable, ViewType {
    companion object {
        val CREATOR = createParcel { Category(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents() = 0

    override fun getViewType(): Int = ViewTypeDelegateAdapter.HEADER_TYPE
}

data class ConditionPredicate(var exactEquals: List<String>) : Parcelable {
    companion object {
        val CREATOR = createParcel { ConditionPredicate(it) }
    }

    constructor(parcel: Parcel) : this(
            mutableListOf<String>().apply {
                parcel.readStringList(this)
            }) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(exactEquals)
    }

    override fun describeContents() = 0
}

data class PersonalityTest(var owner: String,
                           val id: String? = null,
                           var percentageCompletion: Int = 0,
                           var creationDate: Date = Date(),
                           var answersId: List<String> = emptyList()): Serializable, ViewType {

    override fun getViewType() = ViewTypeDelegateAdapter.LATEST_TESTS
}

data class PersonalityTestQuestions(var id: String,
                                    var questions: List<Question>,
                                    var categories: List<Category>?) : Parcelable {
    companion object {
        val CREATOR = createParcel { PersonalityTestQuestions(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            mutableListOf<Question>().apply {
                parcel.readParcelableArray(PersonalityTestQuestions::class.java.classLoader)
            },
            mutableListOf<Category>().apply {
                parcel.readParcelableArray(PersonalityTestQuestions::class.java.classLoader)
            }) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelableArray(questions.toTypedArray(), flags)
        parcel.writeParcelableArray(categories?.toTypedArray(), flags)
    }

    override fun describeContents() = 0
}

data class Question(var question: String, var category: String, var questionType: QuestionType, var testsId: List<String>? = emptyList()) : Parcelable {
    companion object {
        val CREATOR = createParcel { Question(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Question::class.java.classLoader),
            mutableListOf<String>().apply {
                parcel.readStringList(this)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(category)
        parcel.writeParcelable(questionType, flags)
        parcel.writeStringList(testsId)
    }

    override fun describeContents() = 0
}

data class QuestionCondition(var predicate: ConditionPredicate?, var conditionalQuestion: Question?) : Parcelable {
    companion object {
        val CREATOR = createParcel { QuestionCondition(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(QuestionCondition::class.java.classLoader),
            parcel.readParcelable(QuestionCondition::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(predicate, flags)
        parcel.writeParcelable(conditionalQuestion, flags)
    }

    override fun describeContents() = 0
}

data class QuestionRange(var from: Int = 0, var to: Int = 0) : Parcelable, ViewType {

    companion object {
        val CREATOR = createParcel { QuestionRange(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(from)
        parcel.writeInt(to)
    }

    override fun describeContents() = 0

    override fun getViewType(): Int = ViewTypeDelegateAdapter.QUESTION_TYPE_RANGE

    override fun getTotalItems(): Int = 1
}

data class QuestionType(var type: String,
                        var options: List<String>,
                        var condition: QuestionCondition?,
                        var range: QuestionRange?) : Parcelable {
    companion object {
        val CREATOR = createParcel { QuestionType(it) }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            mutableListOf<String>().apply {
                parcel.readStringList(this)
            },
            parcel.readParcelable(QuestionType::class.java.classLoader),
            parcel.readParcelable(QuestionType::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(options)
    }

    override fun describeContents() = 0
}

data class Answer(var answer: String,
                  var question: String,
                  var category: String,
                  var personalityTestId: String,
                  var creationDate: Date = Date(),
                  val id: String? = null) : ViewType {
    override fun getViewType(): Int = ViewTypeDelegateAdapter.ANSWER_TYPE
}

data class QuestionSingleChoice(var options: List<String>) : ViewType {
    override fun getViewType(): Int = ViewTypeDelegateAdapter.QUESTION_TYPE_SINGLE_CHOICE
    override fun getTotalItems(): Int = options.size
}