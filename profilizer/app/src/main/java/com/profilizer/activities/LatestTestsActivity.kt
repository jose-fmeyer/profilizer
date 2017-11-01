package com.profilizer.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.profilizer.ProfilizerApplication
import com.profilizer.R
import com.profilizer.common.hide
import com.profilizer.common.setTitle
import com.profilizer.common.startActivity
import com.profilizer.common.visible
import com.profilizer.personalitytest.adapter.LatestTestsAdapter
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.di.components.DaggerLatestTestsComponent
import com.profilizer.personalitytest.di.modules.LatestTestsModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.model.PersonalityTest
import kotlinx.android.synthetic.main.layout_app_bar.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_latest_tests.btn_add_test as btnAddTest
import kotlinx.android.synthetic.main.activity_latest_tests.progress_bar as progressBar
import kotlinx.android.synthetic.main.activity_latest_tests.tests_empty as testEmptyMessage
import kotlinx.android.synthetic.main.activity_latest_tests.tests_list as testsList

class LatestTestsActivity : AppCompatActivity(), LatestTestsContract.View {

    @Inject
    lateinit var presenter: LatestTestsContract.Presenter
    private val adapter = LatestTestsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpInjection()

        setContentView(R.layout.activity_latest_tests)
        setTitle(R.string.latest_tests, toolbar)
        prepareViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
        presenter.loadTests()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    private fun setUpInjection() {
        DaggerLatestTestsComponent.builder()
                .applicationComponent(ProfilizerApplication.applicationComponent)
                .personalityTestModule(PersonalityTestModule())
                .latestTestsModule(LatestTestsModule(this))
                .build()
                .inject(this)
    }

    private fun prepareViews() {
        testsList.layoutManager = LinearLayoutManager(this)
        testsList.adapter = adapter
        btnAddTest.setOnClickListener {
            startActivity<PersonalityTestActivity>()
        }
    }

    override fun showPersonalityTestData(tests: List<PersonalityTest>) {
        progressBar.hide()
        adapter.clearAndAddTests(tests)
    }

    override fun showErrorMessage() {
        progressBar.hide()
        Snackbar.make(testsList, R.string.load_error_message, Snackbar.LENGTH_LONG).show()
    }

    override fun showEmptyResultMessage() {
        progressBar.hide()
        testEmptyMessage.visible()
    }

    override fun onStartLoading() {
        progressBar.visible()
    }
}
