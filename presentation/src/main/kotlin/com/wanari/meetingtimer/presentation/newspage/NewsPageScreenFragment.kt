package com.wanari.meetingtimer.presentation.newspage

import android.content.Context
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.setVisiblity
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
        AppStateManager.setLoadingState(viewState.loading)
        if (!viewState.loading) {
            if (viewState.data == null) {
                loadContentSubject.onNext(screen<NewsPageScreen>().key)
            } else {
                viewState.data.let { data ->
                    newspage_title?.text = data.title
                    newspage_text?.text = data.text
                    newspage_image.setVisiblity(data.image == null)
                    //TODO("Load image with picasso and handle error")
                }
            }
        }
    }

    override fun loadContent(): PublishSubject<String> = loadContentSubject
}
