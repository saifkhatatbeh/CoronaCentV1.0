package com.v1.Tammeni.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.Activities.NewsWebViewActivity
import com.v1.Tammeni.data.News.News
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.news_item.view.*
import java.util.*

class LiveNewsAdapter(val news: News) : RecyclerView.Adapter<LiveNewsAdapter.CustomViewHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder2 {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.news_item, parent, false)
        return CustomViewHolder2(cellForRow)
    }

    override fun getItemCount(): Int {
        return news.news!!.size
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: CustomViewHolder2, position: Int) {

        val data = news.news?.get(position)

        val str = data?.publishedDateTime
        val delim = "T"
        val delim2 = "-"

        val arr = str?.split(delim)?.toTypedArray()

        if (data != null) {
            data.ampWebUrl
        }

        if (data != null) {
            holder?.view?.txt_author.text = data.provider.name
        }


        if (data != null) {
            holder?.view?.txt_title.text = data.title + "..."

        }

        if (data != null) {
            if (data.tags == null) {
                holder?.view?.txt_country.text = "global"
            } else {
                var tags = data.tags.toString()
                val delim = "["
                val delim2 = "]"
                val delim3 = ","
                val delim4 = "-"

                val arr = tags?.split(delim)?.toTypedArray()
                val arr2 = arr[1]?.split(delim2)?.toTypedArray()
                val arr3 = arr2[0]?.split(delim3)?.toTypedArray()
                val arr4 = arr3[0]?.split(delim4)?.toTypedArray()


                if (arr3.size <= 1) {
                    val loc = Locale("", arr4[0])
                    holder?.view?.txt_country.text = loc.getDisplayCountry().toLowerCase()

                } else {

                    val loc = Locale("", arr4[0])
                    holder?.view?.txt_country.text = loc.getDisplayCountry().toLowerCase()
                }


            }
        }

    }

    inner class CustomViewHolder2(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position = adapterPosition
                var data = news.news?.get(position)
                if (data?.ampWebUrl != null) {
                    val intent = Intent(view.context, NewsWebViewActivity::class.java)
                    intent.putExtra("url", data.ampWebUrl.toString())
                    view.context.startActivity(intent)
                } else if (data?.webUrl != null) {
                    val intent = Intent(view.context, NewsWebViewActivity::class.java)
                    intent.putExtra("url2", data.webUrl.toString())
                    view.context.startActivity(intent)
                }
            }
        }
    }
}

