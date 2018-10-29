package com.wanari.meetingtimer.presentation.news

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.NewsPageScreen
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.news.paging.NewsAdapter
import com.wanari.meetingtimer.presentation.news.paging.NewsDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject

class NewsScreenFragment : ScreenFragment<NewsScreenView, NewsViewState>(), NewsScreenView {
    override val initialViewState = NewsViewState()
    override val presenter by injectPresenter<NewsPresenter>()
    override val hasToolbar = true
    override val layoutRes = R.layout.fragment_news

    private lateinit var newsAdapter: NewsAdapter
    private val newsProvider by inject<NewsDataProvider>()
    private val navigator by inject<Navigator>()

    private val disposables = CompositeDisposable()

    override fun getTitle(context: Context): String = "News Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun render(viewState: NewsViewState) {
    }

    override fun onResume() {
        super.onResume()
        disposables.add(newsAdapter.getNewsClickSubject()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    navigator.navigateTo(NewsPageScreen(it))
                }
        )
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }

    private fun initRecyclerView() {
        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.addItemDecoration(DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL))
        newsAdapter = NewsAdapter(context!!)
        news_recycler_view.adapter = newsAdapter
        newsProvider.getItems()?.observe(this, Observer(newsAdapter::submitList))
    }
}
