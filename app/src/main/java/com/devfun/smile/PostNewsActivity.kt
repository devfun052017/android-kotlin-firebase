package com.devfun.smile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_post_news.*

class PostNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_news)
        setSupportActionBar(activityPostNews_toolbar_topBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activityPostNews_button_cancel.setOnClickListener { onBackPressed() }
        activityPostNews_button_submit.setOnClickListener { saveNews() }
    }

    fun saveNews() {
        if (validate()) {
            val item = NewsModel()
            item.title = activityPostNews_editText_title.text.toString()
            item.image = activityPostNews_editText_imageUrl.text.toString()
            item.posted = activityPostNews_editText_postDate.text.toString()
            item.short_description = activityPostNews_editText_description.text.toString()
            item.description = activityPostNews_editText_fullDescription.text.toString()
            val fromSource = activityPostNews_spinner_source.selectedItem.toString()
            val source = FirebaseDatabase.getInstance().reference.child(fromSource)
            source.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    val lastItem = p0?.childrenCount
                    source.child(String.format("%d", lastItem))
                            .setValue(item) { p0, p1 ->
                                if (p0 != null) {
                                    Toast.makeText(applicationContext,
                                            "Can not create this news. Please try again.",
                                            Toast.LENGTH_LONG).show()
                                }else{
                                    clearText()
                                }
                            }

                }
            })
        }
    }

    private fun clearText() {
        activityPostNews_editText_title.setText("")
        activityPostNews_editText_imageUrl.setText("")
        activityPostNews_editText_postDate.setText("")
        activityPostNews_editText_description.setText("")
        activityPostNews_editText_fullDescription.setText("")
    }

    fun validate(): Boolean {
        var flag = true
        if (TextUtils.isEmpty(activityPostNews_editText_title.text)) {
            activityPostNews_editText_title.error = "Please input news title."
            flag = false;
        }

        if (TextUtils.isEmpty(activityPostNews_editText_imageUrl.text)) {
            activityPostNews_editText_imageUrl.error = "Please input news image URL."
            flag = false;
        }

        if (TextUtils.isEmpty(activityPostNews_editText_postDate.text)) {
            activityPostNews_editText_imageUrl.error = "Please input news posted date."
            flag = false;
        }
        if (TextUtils.isEmpty(activityPostNews_editText_description.text)) {
            activityPostNews_editText_imageUrl.error = "Please input news short description."
            flag = false;
        }
        if (TextUtils.isEmpty(activityPostNews_editText_fullDescription.text)) {
            activityPostNews_editText_imageUrl.error = "Please input news full description."
            flag = false;
        }

        return flag
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
