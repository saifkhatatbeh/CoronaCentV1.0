package com.v1.Tammeni.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.data.Leaderboard_data_items
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_leaderboard.view.leaderboard_country
import kotlinx.android.synthetic.main.leaderboard_item.view.*

class LeaderboardActivityAdapter(options: FirebaseRecyclerOptions<Leaderboard_data_items>) :
    FirebaseRecyclerAdapter<Leaderboard_data_items, LeaderboardActivityAdapter.myviewholder>(options) {
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myviewholder {
        val itemView3 = LayoutInflater.from(parent.context).inflate(
            com.v1.Tammeni.R.layout.leaderboard_item, parent, false
        )
        return myviewholder(itemView3)
    }


    override fun onBindViewHolder(
        holder: myviewholder,
        position: Int,
        model: Leaderboard_data_items


    ) {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        var user = mAuth.currentUser

        var UserRanking = (itemCount - position)


        holder.itemView.text_rank.text = " ${(itemCount - position)} "
        holder.itemView.text_username.text = " ${model.text_username} "
        holder.itemView.text_days_survived.text = " ${model.days_survived} "

        var img = holder.itemView.leaderboard_country
        Picasso.get().load(model.country_image).into(img)

        if (user!!.displayName == model.text_username) {
            var leaderboard = leaderBoard(
                UserRanking,
                model.text_username,
                model.days_survived,
                model.country_image
            )

            mDatabase.child("UserRanks").child(user.uid).setValue(leaderboard)

        }

    }

    private fun leaderBoard(
        rank: Int,
        userName: String,
        daysSurvived: Int,
        countryIMG: String
    ): Leaderboard_data_items {
        return Leaderboard_data_items(rank, userName, daysSurvived, countryIMG)
    }

    class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }
}