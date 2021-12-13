package com.example.panstwa_miasta.results

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.panstwa_miasta.Player
import com.example.panstwa_miasta.R
import com.example.panstwa_miasta.ViewProfileActivity
import com.example.panstwa_miasta.waiting_room.IRecyclerViewClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ResultsActivity : AppCompatActivity(), IRecyclerViewClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var playersList: ArrayList<Player>
    private lateinit var playerCounterView: TextView

    private lateinit var gameId: String
    private lateinit var db: FirebaseDatabase
    private lateinit var gameRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        db = Firebase.database("https://panstwa-miasta-a2611-default-rtdb.europe-west1.firebasedatabase.app/")
        gameId = intent.getStringExtra("gameId").toString()
        gameRef = db.reference.child("Games").child(gameId!!)

        playersList = ArrayList()
        viewsInit()
        getResultsFromDatabase()
    }

    private fun viewsInit() {
        recyclerView = findViewById(R.id.recyclerViewResult)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val customAdapter = ResultsAdapter(playersList, this)
        recyclerView.adapter = customAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        playerCounterView = findViewById(R.id.letterView)
        playerCounterView.text = "Podsumowanie"
    }

    private fun viewProfile(nick: String) {
        val i = Intent(this, ViewProfileActivity::class.java)
        i.putExtra("user", nick)
        startActivity(i)
    }

    private fun getResultsFromDatabase() {
        gameRef.child("Players")
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    var player = Player(it.key!!)
                    player.points = (it.child("Points").value as Long).toInt()
                    playersList.add(player)
                }
                recyclerView.adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onJoinedAvatarClicked(pos: Int) {
        viewProfile(playersList[pos].name)
    }

    override fun onInvitedAvatarClicked(adapterPosition: Int) {}
}