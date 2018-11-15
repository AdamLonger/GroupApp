package com.longer.groupapp.presentation.grouppage

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.common.utils.nullIfEmpty
import com.longer.groupapp.common.utils.setVisiblity
import com.longer.groupapp.navigation.Navigator
import com.longer.groupapp.navigation.screens.GroupPageScreen
import com.longer.groupapp.navigation.screens.NewsPageScreen
import com.longer.groupapp.presentation.R
import com.longer.groupapp.presentation.news.paging.NewsAdapter
import com.longer.groupapp.presentation.news.paging.NewsDataProvider
import com.squareup.picasso.Picasso
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
    private val subscribeSubject = PublishSubject.create<String>()
    private val unsubscribeSubject = PublishSubject.create<String>()
    private val seenSubject = PublishSubject.create<String>()
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
        }
    }

    override fun render(viewState: GroupPageViewState) {
        AppStateManager.setLoadingState(viewState.loading)

        if (viewState.loading) {

        } else {
            if (!viewState.seenUpdated) {
                seenSubject.onNext(screen<GroupPageScreen>().key)
            } else if (viewState.data == null) {
                loadContentSubject.onNext(screen<GroupPageScreen>().key)
            } else {
                grouppage_list_empty_txv.setVisiblity(!viewState.hasChild)
                viewState.data.let { data ->
                    grouppage_name?.text = data.name
                    grouppage_description?.text = data.description
                    data.image?.nullIfEmpty()?.let {
                        Picasso.get().load(it)
                                .resize(grouppage_image.width, 0)
                                .into(grouppage_image)
                    }
                    grouppage_image.setVisiblity(data.image != null)

                    if (data.isSubscribed) {
                        grouppage_subscribe_button.text = getString(R.string.unsubscribe_text)
                    } else {
                        grouppage_subscribe_button.text = getString(R.string.subscribe_text)
                    }

                    grouppage_subscribe_button.setOnClickListener {
                        if (data.isSubscribed) {
                            unsubscribeSubject.onNext(screen<GroupPageScreen>().key)
                        } else {
                            subscribeSubject.onNext(screen<GroupPageScreen>().key)
                        }
                    }
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
    override fun subscribe(): PublishSubject<String> = subscribeSubject
    override fun unsubscribe(): PublishSubject<String> = unsubscribeSubject
    override fun updateSeen(): PublishSubject<String> = seenSubject
}
