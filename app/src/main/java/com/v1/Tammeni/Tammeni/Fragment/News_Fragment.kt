package com.v1.Tammeni.Fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.Activities.NewsWebViewActivity
import com.v1.Tammeni.Adapters.NewsActivityAdapter
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.v1.Tammeni.data.News.News
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.SwipeRefresh

import okhttp3.*
import java.io.IOException

class News_Fragment() : Fragment() {

    lateinit var LinearLayout: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    var xy = "connected"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LinearLayout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView = recycler_view_news_main
        recyclerView.layoutManager = LinearLayout
        recycler_view_news_main.setHasFixedSize(true)

        if (xy == "connected") {

            val Nconnection = NetworkConnection(requireActivity().applicationContext)

            Nconnection.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {
                    fetchNewsJson()
                }
            })

        }

        isRefresh()

    }


    private fun isRefresh() {

        SwipeRefresh.setOnRefreshListener {

            activity?.applicationContext?.let { NetworkConnection(it) }
                ?.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->

                    if (isConnected) {
                        fetchNewsJson()
                    } else {
                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )
                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                        SwipeRefresh.setRefreshing(false)


                    }
                })
        }
    }

    private fun fetchNewsJson() {
        val url = "https://coronavirus-smartable.p.rapidapi.com/news/v1/global/"

        val request = Request.Builder().url(url)
            .header("x-rapidapi-host", "coronavirus-smartable.p.rapidapi.com")
            .header("x-rapidapi-key", "123cdd7f54msh4bc26e5c43d8fa0p1bcfb1jsn114d28b1cf60")
            .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val News = gson.fromJson(body, News::class.java)

                    activity?.runOnUiThread {

                        var index = -1
                        val b = News.news?.size?.minus(1)
                        var h1 = News.news?.get(0)?.heat
                        var h2 = b?.let { News.news?.get(it)?.heat }

                        val ass = arrayListOf<Int>()
                        val ass2 = arrayListOf<String>()
                        val ass3 = java.util.ArrayList<News>()
                        for (i in News.news?.indices!!) {
                            if (News.news!![i].images?.get(0)?.url != null && News.news!![i].provider.images?.get(
                                    0
                                )?.url != null
                            ) {
                                ass.add(News.news!![i].heat)
                                ass3.addAll(listOf(News))
                            } else {
                                News.news!![i].images?.get(0)?.url?.let { ass2.add(it) }
                                ass3.addAll(listOf(News))

                            }

                        }
                        val a = ass.max()
                        println("max heat = $a")
                        var imgUrl = ""
//                        if (ass2.size > 0) {
//                            imgUrl = ass2[1]
//
//                        } else {
//                            imgUrl = ""
//                        }


                        for (i in News.news?.indices!!) {
                            if (News.news!![i].heat == a || News.news!![i].images?.get(0)?.url.equals(
                                    imgUrl
                                ) || News.news!![i].provider.images?.get(0)?.url.equals(imgUrl)
                            ) {
                                index = i
                                break
                            } else {
                                index = 0
                            }
                        }
                        val news = News.news!![index]
                        text_title_news.text = news.title
                        trending_news_author.text = news.provider.name
                        if (news.images?.get(0)?.url != null) {
                            Picasso.get().load(news.images?.get(0)?.url).into(imageHeaderA)

                        } else {
                            Picasso.get().load(R.drawable.new_app_logo).into(imageHeaderA)

                        }
                        imageHeaderA.setOnClickListener {
                            val intent = Intent(activity, NewsWebViewActivity::class.java)
                            intent.putExtra("url", news.ampWebUrl)
                            intent.putExtra("url2", news.webUrl)
                            startActivity(intent)
                        }

                        trending_news_author.setOnClickListener {
                            val intent = Intent(activity, NewsWebViewActivity::class.java)
                            intent.putExtra("url", news.ampWebUrl)
                            intent.putExtra("url2", news.webUrl)
                            startActivity(intent)
                        }

                        text_title_news.setOnClickListener {
                            val intent = Intent(activity, NewsWebViewActivity::class.java)
                            intent.putExtra("url", news.ampWebUrl)
                            intent.putExtra("url2", news.webUrl)
                            startActivity(intent)
                        }
                        recyclerView.adapter = NewsActivityAdapter(News)
                        SwipeRefresh.setRefreshing(false)


//                        dialog.dismiss()

                    }
                } else {
                    activity?.runOnUiThread {

                        SwipeRefresh.setBackgroundColor(resources.getColor(R.color.colorWhite))
                        textView_trending.visibility = View.GONE
                        cardview_todays_news.visibility = View.GONE
                        linear_no_news.visibility = View.VISIBLE
                        linearLayoutNews_Imageheader.visibility = View.GONE
                        recycler_view_news_main.visibility = View.GONE
                        SwipeRefresh.setRefreshing(false)

                    }
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                activity?.runOnUiThread {

                    SwipeRefresh.setBackgroundColor(resources.getColor(R.color.colorWhite))
                    textView_trending.visibility = View.GONE
                    cardview_todays_news.visibility = View.GONE
                    linear_no_news.visibility = View.VISIBLE
                    linearLayoutNews_Imageheader.visibility = View.GONE
                    recycler_view_news_main.visibility = View.GONE
                    SwipeRefresh.setRefreshing(false)

                }

            }

        })

    }

}