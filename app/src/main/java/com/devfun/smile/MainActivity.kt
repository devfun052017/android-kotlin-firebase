package com.devfun.smile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.devfun.smile.utils.AppUtils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            loadNews(mNavSource)
        }
    }
    private val mIntentFilter: IntentFilter = IntentFilter(AppUtils.instance.mRefreshData)
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: NewsAdapter? = null
    private var mNavSource: String = "zing";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdsView()
        //register refresh item
        contentMain_swipeRefreshLayout_refresh.setOnRefreshListener { loadNews(mNavSource) }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        //
        setupRecyclerView()
        appBarmain_textView_title.text = "Zing.vn"
        loadNews(mNavSource)
        AppUtils.instance.printHashKey(this)
        //
        handleAddNews()
        registerReceiver(mReceiver, mIntentFilter)
    }

    private fun handleAddNews() {
        appBarMain_imageView_addNews.setOnClickListener {
            startActivity(Intent(applicationContext,
                    PostNewsActivity::class.java))
        }
        if (BuildConfig.DEBUG) {
            appBarMain_imageView_addNews.visibility = View.VISIBLE
        } else {
            appBarMain_imageView_addNews.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById(R.id.contentMain_recyclerView_main) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = NewsAdapter()
        mRecyclerView!!.adapter = mAdapter
    }

    private fun initAdsView() {
        if (BuildConfig.DEBUG) {
            adView.visibility = View.GONE
            return
        }
        val request = AdRequest.Builder()
                .addTestDevice("C96AB204E75D32E0C479250F6DDC97E2")
                .build()
        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: Int) {
                Log.d("admob", "nAdFailedLoad")
            }
        }
        adView.loadAd(request)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        when (id) {
            R.id.nav_zing -> {
                appBarmain_textView_title.text = "Zing.vn"
                loadNews("zing")
            }
            R.id.nav_express -> {
                appBarmain_textView_title.text = "VNExpress.net"
                loadNews("vnexpress")
            }
            R.id.nav_24h -> {
                appBarmain_textView_title.text = "24h.com.vn"
                loadNews("24h")
            }
            R.id.nav_feedback -> {
                AppUtils.instance.sendMail(this)
            }
            R.id.nav_rating -> {
                AppUtils.instance.rating(applicationContext)
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadNews(from: String) {
        mNavSource = from;
        contentMain_swipeRefreshLayout_refresh.isRefreshing = true
        val fReference = FirebaseDatabase.getInstance().reference.child(from)
        fReference.addListenerForSingleValueEvent(mZingListener)
    }

    private val mZingListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val root = object : GenericTypeIndicator<ArrayList<NewsModel>>() {}
            val news = dataSnapshot.getValue(root)
            if (news != null)
                mAdapter!!.setNewsModels(news)
            else
                mAdapter!!.setNewsModels(newsModels = ArrayList())
            contentMain_swipeRefreshLayout_refresh.isRefreshing = false
        }

        override fun onCancelled(databaseError: DatabaseError) {
            contentMain_swipeRefreshLayout_refresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        adView.destroy()
        if (!BuildConfig.DEBUG)
            startActivity(Intent(applicationContext, ShowFullAdsActivity::class.java))
        super.onDestroy()
    }
}
