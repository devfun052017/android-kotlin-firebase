package com.devfun.smile

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_news_item.view.*

/**
 * *******************************************
 * * Created by Simon on 5/9/17.            **
 * * Copyright (c) 2017 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */
internal class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var mNewsModels: ArrayList<NewsModel>? = null

    fun setNewsModels(newsModels: ArrayList<NewsModel>) {
        mNewsModels = newsModels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val fView = LayoutInflater.from(parent.context.applicationContext)
                .inflate(R.layout.adapter_news_item, parent, false)
        return NewsViewHolder(fView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position);
        holder.bindData(item)
        holder.itemView.setOnClickListener { startNewsDetail(holder) }
    }

    fun getItem(position: Int): NewsModel {
        return mNewsModels!![position]
    }

    override fun getItemCount(): Int {
        return if (mNewsModels == null) 0 else mNewsModels!!.size
    }

    internal class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(newsModel: NewsModel) {
            Glide.with(itemView.context)
                    .load(newsModel.image)
                    .into(itemView.newsItems_imageView_image)
            itemView.newsItems_textView_title.text = newsModel.title
            itemView.newsItems_textView_createdDate.text = newsModel.posted
            itemView.newsItems_textView_shortDescription.text = newsModel.short_description
        }
    }

    private fun startNewsDetail(holder: NewsViewHolder) {
        val position = holder.adapterPosition;
        val intent = Intent(holder.itemView.context, NewsDetailActivity::class.java);
        val bundle = Bundle()
        bundle.putSerializable("news", getItem(position))
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        holder.itemView.context.startActivity(intent)
    }
}
