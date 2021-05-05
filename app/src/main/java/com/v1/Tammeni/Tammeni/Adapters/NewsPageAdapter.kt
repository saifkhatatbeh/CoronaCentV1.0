package com.v1.Tammeni.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.R
import com.v1.Tammeni.data.News_items
import kotlinx.android.synthetic.main.news_item.view.*

class NewsPageAdapter(private val itemList: ArrayList<News_items>) :
    RecyclerView.Adapter<NewsPageAdapter.CountryViewHolder>() {

    var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.news_item
            , parent, false
        )

        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.text_author.text = currentItem.text_author
        holder.text_country.text = currentItem.text_country
        holder.text_title.text = currentItem.text_title

    }

    override fun getItemCount() = itemList.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val text_author: TextView = itemView.txt_author
        val text_country: TextView = itemView.txt_country
        val text_title: TextView = itemView.txt_title

    }

}