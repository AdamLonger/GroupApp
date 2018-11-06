package com.wanari.meetingtimer.presentation.news

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.GENERAL_PATH
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.NewsPageScreen
import com.wanari.meetingtimer.presentation.utils.PagingProvider
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.news.paging.NewsAdapter
import com.wanari.meetingtimer.presentation.news.paging.NewsDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_news.*
import model.NewsObject
import org.koin.android.ext.android.inject

class NewsScreenFragment : ScreenFragment<NewsScreenView, NewsViewState>(), NewsScreenView {
    override val initialViewState = NewsViewState()
    override val presenter by injectPresenter<NewsPresenter>()
    override val hasToolbar = true
    override val layoutRes = R.layout.fragment_news

    private lateinit var newsAdapter: NewsAdapter
    private val newsProvider by inject<NewsDataProvider>()
    private val navigator by inject<Navigator>()

    private val setSubPathSubject = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()
    private var recyclerInited = false

    override fun getTitle(context: Context): String = "News Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun render(viewState: NewsViewState) {
        AppStateManager.setLoadingState(viewState.loading)
        if (!viewState.loading) {
            if (!viewState.subPathSet) {
                setSubPathSubject.onNext(GENERAL_PATH)
            } else if (!recyclerInited) {
                initRecyclerView()
                recyclerInited = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recyclerInited) {
            disposables.add(newsAdapter.getNewsClickSubject()
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        navigator.navigateTo(NewsPageScreen(it))
                    }
            )
        }
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

        disposables.add(newsAdapter.getNewsClickSubject()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    navigator.navigateTo(NewsPageScreen(it))
                })
    }

    override fun setSubPath(): PublishSubject<String> = setSubPathSubject
}
