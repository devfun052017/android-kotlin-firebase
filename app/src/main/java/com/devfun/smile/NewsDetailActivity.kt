package com.devfun.smile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(newsDetail_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val news = intent.extras.getSerializable("news") as NewsModel
        Glide.with(applicationContext)
                .load(news.image)
                .into(newsDetail_imageView_imageHeader)
        newsDetail_textView_title.text = news.title
        newsDetail_textView_posted.text = news.posted
        newsDetail_textView_shortDescription.text = news.short_description
        newsDetail_textView_description.text = Html.fromHtml(news.description)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
