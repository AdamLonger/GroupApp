package com.wanari.meetingtimer.presentation.newspage

import android.content.Context
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.screens.NewsPageScreen
import com.wanari.meetingtimer.presentation.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_news_page.*

class NewsPageScreenFragment : ScreenFragment<NewsPageScreenView, NewsPageViewState>(), NewsPageScreenView {
    override val initialViewState = NewsPageViewState()
    override val presenter by injectPresenter<NewsPagePresenter>()
    override val hasToolbar = false
    override val layoutRes = R.layout.fragment_news_page

    private val loadContentSubject = PublishSubject.create<String>()

    override fun getTitle(context: Context): String = "Title"

    override fun render(viewState: NewsPageViewState) {
        if (viewState.data == null) {
            loadContentSubject.onNext(screen<NewsPageScreen>().key)
        } else {
            newspage_title?.text = viewState.data.title
            newspage_text?.text = viewState.data.text
        }
    }

    override fun loadContent(): PublishSubject<String> = loadContentSubject
}
