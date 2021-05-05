package com.v1.Tammeni.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.Activities.NewsWebViewActivity
import com.v1.Tammeni.data.News.News
import com.v1.Tammeni.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_page_recycler_items.view.*
import java.io.IOException

class NewsActivityAdapter(val news: News) :
    RecyclerView.Adapter<NewsActivityAdapter.CustomViewHolder3>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder3 {
        val LayoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = LayoutInflater.inflate(R.layout.news_page_recycler_items, parent, false)
        return CustomViewHolder3(cellForRow)
    }

    override fun getItemCount(): Int {

        return news.news!!.count()
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: CustomViewHolder3, position: Int) {

        try {

            if (news.news?.get(position)?.title != null) {
                val data = news.news?.get(position)
                val date = news.news?.get(position)?.updatedDateTime

                val img = data?.images?.get(0)?.url
                val img2 = data?.provider?.images?.get(0)?.url

                val Image = holder.view.image_news
                if (data != null) {
                    if (data.title.length > 70) {
                        holder.view.txt_news_title.text = "${data.title.substring(0, 67)}..."
                    } else {
                        holder.view.txt_news_title.text = "${data.title}."
                    }

                    holder.view.txt_news_author.text = data.provider.name
                }
                if (img != null) {
                    Picasso.get().load(img).into(Image)
                } else if (img2 != null) {
                    Picasso.get().load(img2).into(Image)

                } else {
                    Picasso.get().load(R.drawable.app_logo).into(Image)
                }

            }
        } catch (e: IOException) {
            println(e)
        }


    }

    inner class CustomViewHolder3(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position = adapterPosition
                val data = news.news?.get(position)
                if (data?.ampWebUrl != null) {
                    val intent = Intent(view.context, NewsWebViewActivity::class.java)
                    intent.putExtra("url", data.ampWebUrl)
                    intent.putExtra("name", "main")
                    view.context.startActivity(intent)
                } else if (data?.webUrl != null) {
                    val intent = Intent(view.context, NewsWebViewActivity::class.java)
                    intent.putExtra("url2", data.webUrl)
                    intent.putExtra("name", "main")
                    view.context.startActivity(intent)
                }
            }
        }
    }
}