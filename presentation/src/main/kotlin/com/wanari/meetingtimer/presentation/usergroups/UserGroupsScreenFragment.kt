package com.wanari.meetingtimer.presentation.usergroups

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.GroupPageScreen
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.usergroups.paging.UserGroupsAdapter
import com.wanari.meetingtimer.presentation.usergroups.paging.UserGroupsDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_groups.*
import org.koin.android.ext.android.inject

class UserGroupsScreenFragment : ScreenFragment<UserGroupsScreenView, UserGroupsViewState>(), UserGroupsScreenView {
    override val initialViewState = UserGroupsViewState()
    override val presenter by injectPresenter<UserGroupsPresenter>()
    override val hasToolbar = true
    override val layoutRes = R.layout.fragment_user_groups

    private lateinit var groupsAdapter: UserGroupsAdapter
    private val groupsProvider by inject<UserGroupsDataProvider>()
    private val navigator by inject<Navigator>()

    private val disposables = CompositeDisposable()

    override fun getTitle(context: Context): String = "User Groups Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun render(viewState: UserGroupsViewState) {
        AppStateManager.setLoadingState(viewState.loading)
    }

    override fun onResume() {
        super.onResume()
        disposables.add(groupsAdapter.getGroupClickSubject()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    navigator.navigateTo(GroupPageScreen(it))
                }
        )
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }

    private fun initRecyclerView() {
        usergroups_recycler_view.layoutManager = LinearLayoutManager(context)
        usergroups_recycler_view.addItemDecoration(DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL))
        groupsAdapter = UserGroupsAdapter(context!!)
        usergroups_recycler_view.adapter = groupsAdapter
        groupsProvider.getItems()?.observe(this, Observer(groupsAdapter::submitList))
    }
}
