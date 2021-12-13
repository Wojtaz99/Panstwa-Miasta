package com.example.panstwa_miasta.main_game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.panstwa_miasta.Player
import com.example.panstwa_miasta.R

class PlayersPointsAdapter(var playersPointsList: ArrayList<Player>)
    : RecyclerView.Adapter<PlayersPointsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var playerNickLabel: TextView
        var playerPointsLabel: TextView

        init {
            playerNickLabel = view.findViewById(R.id.playerNickLabel)
            playerPointsLabel = view.findViewById(R.id.playerAnswerLabel)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_of_players_answers, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playerNickLabel.text = playersPointsList[position].name
        holder.playerPointsLabel.text = playersPointsList[position].points.toString()
    }

    override fun getItemCount() = playersPointsList.size

}
