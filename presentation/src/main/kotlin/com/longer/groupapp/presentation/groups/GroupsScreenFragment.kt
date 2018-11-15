package com.longer.groupapp.presentation.groups

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.common.utils.setVisiblity
import com.longer.groupapp.navigation.Navigator
import com.longer.groupapp.navigation.screens.GroupPageScreen
import com.longer.groupapp.presentation.R
import com.longer.groupapp.presentation.groups.paging.GroupsAdapter
import com.longer.groupapp.presentation.groups.paging.GroupsDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_groups.*
import org.koin.android.ext.android.inject

class GroupsScreenFragment : ScreenFragment<GroupsScreenView, GroupsViewState>(), GroupsScreenView {
    override val initialViewState = GroupsViewState()
    override val presenter by injectPresenter<GroupsPresenter>()
    override val hasToolbar = true
    override val layoutRes = R.layout.fragment_groups

    private lateinit var groupsAdapter: GroupsAdapter
    private val groupsProvider by inject<GroupsDataProvider>()
    private val navigator by inject<Navigator>()

    private val disposables = CompositeDisposable()

    override fun getTitle(context: Context): String = "Groups Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun render(viewState: GroupsViewState) {
        AppStateManager.setLoadingState(viewState.loading)
        groups_list_empty_txv.setVisiblity(!viewState.hasChild)
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
        groups_recycler_view.layoutManager = LinearLayoutManager(context)
        groups_recycler_view.addItemDecoration(DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL))
        groupsAdapter = GroupsAdapter(context!!)
        groups_recycler_view.adapter = groupsAdapter
        groupsProvider.getItems()?.observe(this, Observer(groupsAdapter::submitList))
    }
}
