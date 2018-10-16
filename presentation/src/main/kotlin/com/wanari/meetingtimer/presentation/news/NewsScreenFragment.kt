package com.wanari.meetingtimer.presentation.news

import android.content.Context
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.presentation.R
import org.koin.android.ext.android.inject

class NewsScreenFragment : ScreenFragment<NewsScreenView, NewsViewState>(), NewsScreenView {
    override val initialViewState = NewsViewState()
    override val presenter by injectPresenter<NewsPresenter>()
    override val layoutRes = R.layout.fragment_news

    private val navigator by inject<Navigator>()

    override fun getTitle(context: Context): String = "News Screen"

    override fun render(viewState: NewsViewState) {

    }
}
