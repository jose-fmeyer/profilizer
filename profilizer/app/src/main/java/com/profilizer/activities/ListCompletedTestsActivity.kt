package com.profilizer.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.profilizer.ProfilizerApplication
import com.profilizer.R
import com.profilizer.common.hide
import com.profilizer.common.setTitle
import com.profilizer.common.tintDrawable
import com.profilizer.common.visible
import com.profilizer.personalitytest.adapter.ListCompletedTestAdapter
import com.profilizer.personalitytest.contracts.ListCompletedTestsContract
import com.profilizer.personalitytest.di.components.DaggerListCompletedTestComponent
import com.profilizer.personalitytest.di.modules.ListCompletedTestModule
import com.profilizer.personalitytest.di.modules.PersonalityTestModule
import com.profilizer.personalitytest.model.Answer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_app_bar.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_completed_test.answers_list as answersList
import kotlinx.android.synthetic.main.activity_completed_test.progress_bar as progressBar

class ListCompletedTestsActivity : AppCompatActivity(), ListCompletedTestsContract.View {

    companion object {
        val KEY_PERSONALITY_TEST = "personalityTest"

        fun buildCompletedTestIntent(context: Context, personalityTestId: String) : Intent {
            val intent = Intent(context, ListCompletedTestsActivity::class.java)
            intent.putExtra(KEY_PERSONALITY_TEST, personalityTestId)
            return intent
        }
    }

    @Inject
    lateinit var presenter: ListCompletedTestsContract.Presenter
    private val adapter = ListCompletedTestAdapter()
    private lateinit var itemSearch: MenuItem
    private var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpInjection()

        setContentView(R.layout.activity_completed_test)
        setTitle(R.string.latest_tests, toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prepareViews()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
        intent.extras?.let {
            presenter.loadCompletedTestData(it.getString(KEY_PERSONALITY_TEST))
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        this.itemSearch = menu.findItem(R.id.action_searchable_activity)
        this.itemSearch.icon = this.tintDrawable(R.color.icons, R.drawable.ic_search_black_24dp)
        initSearchAnswers()
        bind()
        return true
    }

    private fun bind() {
        createTextChangeObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { onStartLoading() }
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    progressBar.hide()
                    adapter.filter.filter(it)
                }
    }

    private fun setUpInjection() {
        DaggerListCompletedTestComponent.builder()
                .applicationComponent(ProfilizerApplication.applicationComponent)
                .personalityTestModule(PersonalityTestModule())
                .listCompletedTestModule(ListCompletedTestModule(this))
                .build()
                .inject(this)
    }

    private fun prepareViews() {
        answersList.layoutManager = LinearLayoutManager(this)
        answersList.adapter = adapter
    }

    override fun getViewContext(): Context {
        return this
    }

    override fun showCompletedTestData(answers: List<Answer>) {
        progressBar.hide()
        adapter.clearAndAddAnswers(answers)
    }

    override fun showErrorMessage() {
        progressBar.hide()
        Snackbar.make(answersList, R.string.load_error_message, Snackbar.LENGTH_LONG).show()
    }

    override fun onStartLoading() {
        progressBar.visible()
    }

    override fun showNoNetworkMessage() {
        progressBar.hide()
        Snackbar.make(answersList, R.string.no_network_error_message, Snackbar.LENGTH_LONG).show()
    }

    private fun initSearchAnswers() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = getSearchView(itemSearch)
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setIconifiedByDefault(true)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        val searchAutoComplete = searchView.findViewById<SearchView.SearchAutoComplete>(android.support.v7.appcompat.R.id.search_src_text) as SearchView.SearchAutoComplete
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color.white))
        searchAutoComplete.setTextColor(Color.WHITE)
    }

    private fun getSearchView(item: MenuItem): SearchView {
        return item.actionView as SearchView
    }

    private fun createTextChangeObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->

            val queryListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        emitter.onNext(newText)
                    } else {
                        emitter.onNext("")
                    }
                    return true
                }
            }
            getSearchView(itemSearch).setOnQueryTextListener(queryListener)

            emitter.setCancellable {
                getSearchView(itemSearch).setOnQueryTextListener(null)
            }
        }
        return textChangeObservable
                .debounce(1000, TimeUnit.MILLISECONDS)
    }
}