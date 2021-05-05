package com.v1.Tammeni.Activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.v1.Tammeni.Adapters.LeaderboardActivityAdapter
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.v1.Tammeni.data.Leaderboard_data_items
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_leaderboard.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*


class LeaderboardActivity : AppCompatActivity() {
    lateinit var adapter: LeaderboardActivityAdapter

    private lateinit var mDatabase: DatabaseReference

    private lateinit var Ranking: DatabaseReference

    lateinit var leaderBoardList: MutableList<String>
    lateinit var SortedLeaderboard: MutableList<String>

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        leaderBoardList = mutableListOf()
        SortedLeaderboard = mutableListOf()
        mDatabase = FirebaseDatabase.getInstance().getReference("Score")
        Ranking = FirebaseDatabase.getInstance().getReference("UserRanks")

        mAuth = FirebaseAuth.getInstance()

        var user = mAuth.currentUser

        if (user!!.isAnonymous) {
            cardViewUserScore.visibility = View.GONE
        } else {
            cardViewUserScore.visibility = View.VISIBLE

        }
        var path = user.uid

        val options: FirebaseRecyclerOptions<Leaderboard_data_items> =
            FirebaseRecyclerOptions.Builder<Leaderboard_data_items>()
                .setQuery(
                    mDatabase.orderByChild("days_survived").limitToLast(100),
                    Leaderboard_data_items::class.java
                )
                .build()

        adapter = LeaderboardActivityAdapter(options)
        leaderboard_recycler.adapter = adapter
        leaderboard_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        leaderboard_recycler.setHasFixedSize(true)


        val nConnection = NetworkConnection(this)
        nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (isConnected) {
                val zone = Ranking.child(path)

                zone.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        finish()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot!!.exists()) {
                            leaderBoardList.clear()
                            for (h in snapshot.children) {
                                val leaderboard = h.getValue()
                                leaderBoardList.add(leaderboard.toString())
                            }
                            var country = leaderBoardList.get(0)
                            var result = leaderBoardList.get(1)
                            var rank = leaderBoardList.get(2)
                            var name = leaderBoardList.get(3)


                            var image = findViewById<ImageView>(R.id.leaderboard_country)

                            text_username_you.text = " $name "
                            text_days_survived_you.text = " $result "
                            text_rank_you.text = " $rank "

                            Picasso.get().load(country).into(image)

                        }
                    }

                })
            } else {
                val customToast_negative: View =
                    layoutInflater.inflate(
                        R.layout.custom_toast_negative,
                        fl_wrapper_fill_all_fields_toast
                    )

                Toast(this).apply {
                    customToast_negative.toast_text.text =
                        getString(R.string.No_Internet_Connection)
                    view = customToast_negative
                }.show()

                finish()
            }

        })


        backArrow.setOnClickListener {
            finish()
        }


    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    override fun onBackPressed() {

        finish()

        super.onBackPressed()

    }
}