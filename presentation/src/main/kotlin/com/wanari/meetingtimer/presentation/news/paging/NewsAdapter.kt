package com.wanari.meetingtimer.presentation.news.paging

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanari.meetingtimer.presentation.R
import model.NewsObject

import kotlinx.android.synthetic.main.item_adapter_news.view.*

class NewsAdapter constructor(
        context: Context
) : PagedListAdapter<NewsObject, NewsAdapter.NewsViewHolder>(

        object : DiffUtil.ItemCallback<NewsObject>() {
            override fun areItemsTheSame(
                    oldItem: NewsObject?,
                    newItem: NewsObject?): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                    oldItem: NewsObject?,
                    newItem: NewsObject?): Boolean = oldItem?.description == newItem?.description
        }) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = mInflater.inflate(R.layout.item_adapter_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.title?.text = news?.name
        holder.text?.text = news?.description
    }

    class NewsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title = itemView?.news_item_title
        var text = itemView?.news_item_text
    }
}