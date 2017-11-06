package com.profilizer.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.profilizer.ProfilizerApplication
import com.profilizer.R
import com.profilizer.common.*
import com.profilizer.personalitytest.adapter.LatestTestsAdapter
import com.profilizer.personalitytest.contracts.LatestTestsContract
import com.profilizer.personalitytest.di.components.DaggerLatestTestsComponent
import com.profilizer.personalitytest.di.modules.LatestTestsModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.model.PersonalityTest
import com.profilizer.ui.OnItemClickListener
import kotlinx.android.synthetic.main.layout_app_bar.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_latest_tests.btn_add_test as btnAddTest
import kotlinx.android.synthetic.main.activity_latest_tests.progress_bar as progressBar
import kotlinx.android.synthetic.main.activity_latest_tests.tests_empty as testEmptyMessage
import kotlinx.android.synthetic.main.activity_latest_tests.tests_empty as textEmpty
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
        testsList.addOnItemClickListener(itemClickListener)
        btnAddTest.setOnClickListener {
            startActivity<StartPersonalityTestActivity>()
        }
    }

    override fun getViewContext(): Context {
        return this
    }

    override fun showPersonalityTestData(tests: List<PersonalityTest>) {
        progressBar.hide()
        if (tests.isNotEmpty()) {
            textEmpty.hide()
        }
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

    override fun showNoNetworkMessage() {
        progressBar.hide()
        Snackbar.make(testsList, R.string.no_network_error_message, Snackbar.LENGTH_LONG).show()
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View) {
            val adapter = testsList.adapter as LatestTestsAdapter
            val test = adapter.getItemAt(testsList.getChildAdapterPosition(view))
            if (test.percentageCompletion == 100) {
                startActivity(ListCompletedTestsActivity.buildCompletedTestIntent(
                        this@LatestTestsActivity, test.id!!))
            } else {
                startActivity(StartPersonalityTestActivity.buildPersonalityTestIntent(
                        this@LatestTestsActivity, test.id!!))
            }
        }
    }
}
