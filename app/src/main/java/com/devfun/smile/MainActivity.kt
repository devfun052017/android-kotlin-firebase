package com.devfun.smile

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.devfun.smile.utils.AppUtils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: NewsAdapter? = null
    private var mNavSource: String = "zing";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        setTitle("Zing.vn")
        loadNews(mNavSource)
        AppUtils.instance.printHashKey(this)
    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById(R.id.contentMain_recyclerView_main) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = NewsAdapter()
        mRecyclerView!!.adapter = mAdapter
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
                setTitle("Zing.vn")
                loadNews("zing")
            }
            R.id.nav_express -> {
                setTitle("VNExpress.net")
                loadNews("vnexpress")
            }
            R.id.nav_24h -> {
                setTitle("24h.com.vn")
                loadNews("24h")
            }
            R.id.nav_feedback -> {
                AppUtils.instance.sendMail(this)
            }
            R.id.nav_share -> {
                AlertDialog.Builder(this)
                        .setMessage("Coming soon!!!!")
                        .setNegativeButton("OK", null)
                        .create()
                        .show()
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
}
