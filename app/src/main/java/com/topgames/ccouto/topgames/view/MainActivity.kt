package com.topgames.ccouto.topgames.view

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.topgames.ccouto.topgames.R
import com.topgames.ccouto.topgames.view.adapter.PaginationAdapter
import com.topgames.ccouto.topgames.api.CallBackGeneric
import com.topgames.ccouto.topgames.api.GameService
import com.topgames.ccouto.topgames.base.BaseActivity
import com.topgames.ccouto.topgames.base.Constants
import com.topgames.ccouto.topgames.domain.GameResponse
import com.topgames.ccouto.topgames.domain.Top
import com.topgames.ccouto.topgames.utils.PaginationAdapterCallback
import com.topgames.ccouto.topgames.utils.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), PaginationAdapterCallback {

    private val TAG = "MainActivity"
    private val PAGE_START = 1
    private val TOTAL_PAGES = 10
    private var currentPage = PAGE_START
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        loadFirstPage()
    }

    private lateinit var adapter: PaginationAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private fun initView() {
        initAdapterView()

        errorBtnRetry.setOnClickListener { loadFirstPage() }

        swipeRefreshLayout.setOnRefreshListener {
            Log.d(TAG, "onRefreshListener")

            currentPage = PAGE_START
            isLoading = false
            isLastPage = false

            initAdapterView()

            loadFirstPage()

            swipeRefreshLayout.isRefreshing = false;
        }
    }

    private fun initAdapterView() {
        adapter = PaginationAdapter(this@MainActivity)

        linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        mainRecycler.layoutManager = linearLayoutManager
        mainRecycler.itemAnimator = DefaultItemAnimator()

        mainRecycler.adapter = adapter

        mainRecycler.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override val totalPageCount: Int = TOTAL_PAGES

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                loadNextPage()
            }
        })
    }

    private fun loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage)

        getNextTopGames()
    }

    private fun getNextTopGames() {
        val callBack = object : CallBackGeneric<GameResponse>{
            override fun onSuccess(response: GameResponse) {
                adapter.removeLoadingFooter()
                isLoading = false
                adapter.addAll(response.top!!)

                if (currentPage != TOTAL_PAGES)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true

            }

            override fun onError(response: GameResponse?) {
                adapter.showRetry(true, response!!.msgError)
            }
        }
        GameService.getTopGames(callBack,currentPage*10)
    }

    private fun loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ")

        hideErrorView()

        getFirstTopGames()
    }

    private fun getFirstTopGames() {
        val callBack = object : CallBackGeneric<GameResponse>{
            override fun onSuccess(response: GameResponse) {
                hideErrorView()
                mainProgress.visibility = View.GONE

                adapter.addAll(response.top!!)

                if (currentPage <= TOTAL_PAGES)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true
            }

            override fun onError(response: GameResponse?) {
                showErrorView(response)
            }
        }
        GameService.getTopGames(callBack,10)
    }

    private fun showErrorView(response: GameResponse?) {
        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
            mainProgress.visibility = View.GONE
            errorTxtCause.text = response!!.msgError
        }
    }

    override fun retryPageLoad() {
        loadNextPage()
    }

    override fun onClickItem(top: Top) {
        startActivity<DetalhesActivity>(Constants.PAR_TOP to top)
    }

    private fun hideErrorView() {
        if (errorLayout.visibility == View.VISIBLE) {
            errorLayout.visibility = View.GONE
            mainProgress.visibility = View.VISIBLE
        }
    }
}
