package com.wanari.meetingtimer.presentation.groups.paging

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanari.meetingtimer.presentation.R
import model.GroupObject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_adapter_group.view.*

class GroupsAdapter constructor(
        context: Context
) : PagedListAdapter<GroupObject, GroupsAdapter.GroupViewHolder>(

        object : DiffUtil.ItemCallback<GroupObject>() {
            override fun areItemsTheSame(
                    oldItem: GroupObject?,
                    newItem: GroupObject?): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                    oldItem: GroupObject?,
                    newItem: GroupObject?): Boolean = (oldItem?.name == newItem?.name) &&
                    (oldItem?.description == newItem?.description) &&
                    (oldItem?.image == newItem?.image)&&
                    (oldItem?.objectKey == newItem?.objectKey)
        }) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var groupClickSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = mInflater.inflate(R.layout.item_adapter_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.title?.text = group?.name
        holder.text?.text = group?.description
        group?.objectKey?.let { key ->
            holder.itemView.setOnClickListener { groupClickSubject.onNext(key) }
        }
    }

    class GroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title = itemView?.group_item_title
        var text = itemView?.group_item_text
    }

    fun getGroupClickSubject(): PublishSubject<String> = groupClickSubject
}