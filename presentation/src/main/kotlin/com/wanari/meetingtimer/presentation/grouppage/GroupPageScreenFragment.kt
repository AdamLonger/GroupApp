package com.wanari.meetingtimer.presentation.grouppage

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.setVisiblity
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.GroupPageScreen
import com.wanari.meetingtimer.navigation.screens.NewsPageScreen
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.news.paging.NewsAdapter
import com.wanari.meetingtimer.presentation.news.paging.NewsDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_group_page.*
import org.koin.android.ext.android.inject

class GroupPageScreenFragment : ScreenFragment<GroupPageScreenView, GroupPageViewState>(), GroupPageScreenView {
    override val initialViewState = GroupPageViewState()
    override val presenter by injectPresenter<GroupPagePresenter>()
    override val hasToolbar = false
    override val layoutRes = R.layout.fragment_group_page

    private val newsProvider by inject<NewsDataProvider>()
    private val navigator by inject<Navigator>()

    private val loadContentSubject = PublishSubject.create<String>()
    private val setSubPathSubject = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()

    private lateinit var newsAdapter: NewsAdapter
    private var isDescriptionVisible = false
    private var recyclerInited = false

    override fun getTitle(context: Context): String = "Title"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grouppage_description_show_btn.setOnClickListener {
            isDescriptionVisible = !isDescriptionVisible
            grouppage_description.setVisiblity(isDescriptionVisible)
            grouppage_description_arrow.rotation = (grouppage_description_arrow.rotation + 180) % 360

            var animRes = R.anim.abc_fade_out
            if (isDescriptionVisible) animRes = R.anim.abc_fade_in
            grouppage_description.startAnimation(
                    AnimationUtils.loadAnimation(context, animRes)
            )
        }
    }

    override fun render(viewState: GroupPageViewState) {
        if (viewState.loading) {

        } else {
            if (viewState.data == null) {
                loadContentSubject.onNext(screen<GroupPageScreen>().key)
            } else {
                viewState.data.let { data ->
                    grouppage_name?.text = data.name
                    grouppage_description?.text = data.description
                }
            }

            if (!viewState.subPathSet) {
                setSubPathSubject.onNext(screen<GroupPageScreen>().key)
            } else {
                if (!recyclerInited) {
                    initRecyclerView()
                    recyclerInited = true
                }

            }
        }
    }

    private fun initRecyclerView() {
        grouppage_recycler_view.layoutManager = LinearLayoutManager(context)
        grouppage_recycler_view.addItemDecoration(DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL))
        newsAdapter = NewsAdapter(context!!)
        grouppage_recycler_view.adapter = newsAdapter
        newsProvider.getItems()?.observe(this, Observer(newsAdapter::submitList))
        disposables.add(newsAdapter.getNewsClickSubject()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    navigator.navigateTo(NewsPageScreen(it))
                })
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

    override fun loadContent(): PublishSubject<String> = loadContentSubject
    override fun setSubPath(): PublishSubject<String> = setSubPathSubject
}