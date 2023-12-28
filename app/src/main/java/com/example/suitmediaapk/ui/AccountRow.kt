package com.example.suitmediaapk.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediaapk.R
import com.example.suitmediaapk.adapter.UsersAdapter
import com.example.suitmediaapk.api.APIClient
import com.example.suitmediaapk.dataclass.UserResponse
import com.example.suitmediaapk.dataclass.data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRow : AppCompatActivity(), UsersAdapter.OnItemClickListener {


    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var currentPage = 1
    private val perPage = 10

    companion object {
        const val SELECTED_USER_NAME = "SELECTED_USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_row)

        val customActionBar = layoutInflater.inflate(R.layout.action_bar_layout_third, null)
        val params = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        supportActionBar?.apply {
            setCustomView(customActionBar, params)
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        recyclerView = findViewById(R.id.rv_account)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        emptyStateTextView = findViewById(R.id.emptyStateTextView) // Initialize TextView


        usersAdapter = UsersAdapter(this, emptyList(), this)
        recyclerView.adapter = usersAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            fetchData()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!swipeRefreshLayout.isRefreshing && currentPage < totalItemCount / perPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        currentPage++
                        fetchData()
                    }
                }
            }
        })

        fetchData()
    }

    override fun onItemClick(data: data) {
        val resultIntent = Intent()
        resultIntent.putExtra(SELECTED_USER_NAME, "${data.first_name} ${data.last_name}")
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun fetchData() {
        swipeRefreshLayout.isRefreshing = true

        val call = APIClient.api.getUsers(currentPage, perPage)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    if (currentPage == 1) {
                        usersAdapter.setData(users)
                    } else {
                        val currentData = usersAdapter.getData().toMutableList()
                        currentData.addAll(users)
                        usersAdapter.setData(currentData)
                    }
                    showEmptyStateIfNeeded()
                }
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
    private fun showEmptyStateIfNeeded() {
        if (usersAdapter.itemCount == 0) {
            emptyStateTextView.visibility = View.VISIBLE
        } else {
            emptyStateTextView.visibility = View.GONE
        }
    }
}