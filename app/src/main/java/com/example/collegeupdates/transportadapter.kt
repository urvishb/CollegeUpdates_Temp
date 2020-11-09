package com.example.collegeupdates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class transportadapter(var dataholder: ArrayList<Datamodel>) : RecyclerView.Adapter<transportadapter.Myviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design_transport, parent, false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.img.setImageResource(dataholder[position].image)
        holder.header.text = dataholder[position].header
        holder.desc.text = dataholder[position].desc
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.rvimage)
        var header: TextView = itemView.findViewById(R.id.myheader)
        var desc: TextView = itemView.findViewById(R.id.mydesc)

    }
}