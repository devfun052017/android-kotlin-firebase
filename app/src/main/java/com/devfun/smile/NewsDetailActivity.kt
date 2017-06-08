package com.devfun.smile

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_news_detail.*
import java.util.*

class NewsDetailActivity : AppCompatActivity() {
    var tts: TextToSpeech? = null
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
        newsDetail_textView_title.text = news.title!!.trim().replace("\n", "")
        newsDetail_textView_posted.text = news.posted!!.trim().replace("\n", "")
        newsDetail_textView_shortDescription.text = news.short_description!!.trim().replace("\n", "")
        newsDetail_textView_description.text = Html.fromHtml(news.description!!.trim().replace("\n", ""))
        initTTS()
        newsDetail_imageView_speak.setOnClickListener {
            if (newsDetail_imageView_speak.isSelected) {
                newsDetail_imageView_speak.isSelected = false
                newsDetail_imageView_speak.setImageResource(R.drawable.vector_drawable_ic_volume_up_24px)
                tts?.stop()
            } else {
                newsDetail_imageView_speak.isSelected = true
                newsDetail_imageView_speak.setImageResource(R.drawable.vector_drawable_ic_volume_off_24px)
                speak()
            }
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(applicationContext,
                TextToSpeech.OnInitListener { status ->
                    if (status != TextToSpeech.ERROR) {
                        try {
                            tts?.language = Locale("vi", "VN")
                            tts?.setSpeechRate(0.8f)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                })
    }

    private fun speak() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(utteranceId: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStart(utteranceId: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        val speak = String.format("%s. %s %s",
                newsDetail_textView_title.text.toString(),
                newsDetail_textView_shortDescription.text.toString(),
                newsDetail_textView_description.text.toString())
        tts?.speak(
                speak,
                TextToSpeech.QUEUE_ADD, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        tts?.stop()
        tts?.shutdown()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
