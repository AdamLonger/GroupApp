package com.longer.groupapp.presentation.newspage

import android.content.Context
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.common.utils.setVisiblity
import com.longer.groupapp.navigation.screens.NewsPageScreen
import com.longer.groupapp.presentation.R
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
