package com.wanari.meetingtimer.presentation.usergroups.paging

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.groups.paging.GroupsAdapter
import com.wanari.meetingtimer.presentation.model.GroupObject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_adapter_group.view.*

class UserGroupsAdapter constructor(
        context: Context
) : PagedListAdapter<GroupObject, UserGroupsAdapter.UserGroupViewHolder>(

        object : DiffUtil.ItemCallback<GroupObject>() {
            override fun areItemsTheSame(
                    oldItem: GroupObject?,
                    newItem: GroupObject?): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                    oldItem: GroupObject?,
                    newItem: GroupObject?): Boolean = (oldItem?.name == newItem?.name) &&
                    (oldItem?.description == newItem?.description) &&
                    (oldItem?.image == newItem?.image)&&
                    (oldItem?.key == newItem?.key)
        }) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var groupClickSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupViewHolder {
        val view = mInflater.inflate(R.layout.item_adapter_group, parent, false)
        return UserGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserGroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.title?.text = group?.name
        holder.text?.text = group?.description
        group?.key?.let { key ->
            holder.itemView.setOnClickListener { groupClickSubject.onNext(key) }
        }
    }

    class UserGroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title = itemView?.group_item_title
        var text = itemView?.group_item_text
    }

    fun getGroupClickSubject(): PublishSubject<String> = groupClickSubject
}