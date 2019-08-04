package com.mind.coolest.url_checker.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mind.coolest.url_checker.R
import com.mind.coolest.url_checker.UrlCheckerApplication
import com.mind.coolest.url_checker.data.converter.urlEntityToUrlItemViewModel
import com.mind.coolest.url_checker.data.repository.URLRepository
import com.mind.coolest.url_checker.databinding.ActivityMainBinding
import com.mind.coolest.url_checker.ui.main.adapter.SwipeToDeleteCallback
import com.mind.coolest.url_checker.ui.main.adapter.UrlAdapter
import com.mind.coolest.url_checker.ui.main.dialog.UrlSortDialog
import com.mind.coolest.url_checker.ui.main.viewmodel.ClickHandler
import com.mind.coolest.url_checker.ui.main.viewmodel.MainViewModel
import com.mind.coolest.url_checker.utils.MyExecutors
import com.mind.coolest.url_checker.ui.main.viewmodel.ViewModelProviderFactory

class MainActivity : AppCompatActivity(), UrlSortDialog.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var urlListRecyclerView: RecyclerView
    private lateinit var urlSearch: EditText
    private val adapter = UrlAdapter()

    private val viewModel: MainViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this,
            ViewModelProviderFactory(MainViewModel::class) {
                MainViewModel(
                    URLRepository(
                        UrlCheckerApplication.instance!!.urlDao,
                        UrlCheckerApplication.instance!!.urlSortPrefs
                    ),
                    MyExecutors()
                )
            }).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        initView()
    }

    override fun onUrlSortInteraction(sortType: String) {
        viewModel.setUrlSortType(sortType)
        viewModel.dataUrlEntity.removeObservers(this)
        addUrlDataObserver()
    }

    private fun initView() {
        urlListRecyclerView = findViewById(R.id.recV_urls)
        urlSearch = findViewById(R.id.et_search)
        urlListRecyclerView.layoutManager = LinearLayoutManager(this)
        urlListRecyclerView.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(urlListRecyclerView)

        urlSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    private fun setUp() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.handler = ClickHandler()
        binding.lifecycleOwner = this

        addUrlDataObserver()

        viewModel.getUrlSortedPickerDialog().observe(this, Observer { display ->
            if (display) {
                val urlSortDialog = UrlSortDialog.newInstance(viewModel.getUrlSortType())
                urlSortDialog.show(supportFragmentManager, "sort_dialog")
            }
        })
    }

    private fun addUrlDataObserver() {
        viewModel.dataUrlEntity.observe(this, Observer {
            adapter.updateData(urlEntityToUrlItemViewModel(it))
        })
    }
}
